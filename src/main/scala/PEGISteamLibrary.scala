import java.io.{File, FileNotFoundException}
import java.nio.file.{Files, Paths}
import com.softwaremill.sttp._
import com.softwaremill.sttp.HttpURLConnectionBackend
import com.typesafe.scalalogging.StrictLogging
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import scala.io.Source

object PEGISteamLibrary extends StrictLogging {

  case class GameTitle(title: String)

  case class GameResponse(title: GameTitle, response: String)

  case class PEGIDetails(searchTitle: String, foundTitle: String, ratingURL: String, consumerAdvice: String, descriptorsURL: List[String])

  def main(args: Array[String]): Unit = {

    if (!checkCLIArguments(args)) {
      logger.error("Please provide a plain text file as input, e.g.: sbt \"run my-games.txt\"")
      sys.exit(-1)
    }

    val gameFile = args.head

    val content =
      parseGameListFile(new File(gameFile))
        .map(callPEGIWebsite)
        .flatMap(scrapPEGIWebpage)
        .map(writeHTMLTableLigne)

    if (content.nonEmpty) writeOutput(gameFile, content.mkString)
  }


  private def writeOutput(gameFile: String, content: String) = {
    val header =
      """
                <!DOCTYPE html><html>
                <head>
                <meta name="viewport" content="width=device-width, user-scalable=no">
                <meta charset="utf-8"/>
                <title>PEGI report</title>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@ajusa/lit@latest/src/lit.css" />
                <style>* {font-family:sans-serif}</style>
                </head>
      <body><h1>PEGI report</h1>
      <p>Please not that games that have not been found do not appear on this page.</p>
      <table>
      <tr><th>Searched game</th><th>Found game</th><th>Customer advice</th><th>Rating</th><th>Tags</th></tr>"""
    val footer = "</table></body></html>"

    val path = Paths.get(gameFile + ".html")
    logger.info("writing result to: " + path)
    Files.write(path, (header + content + footer).getBytes("UTF-8"))
  }

  def checkCLIArguments(args: Array[String]): Boolean = args.length == 1 && args.head.trim().nonEmpty

  def parseGameListFile(gameList: File): List[GameTitle] = {
    try {
      val titles = Source.fromFile(gameList).getLines().toList.map(GameTitle)
      logger.info(s"games found: ${titles.map(_.title)}")
      titles
    } catch {
      case _: FileNotFoundException =>
        logger.error(s"game file not found!")
        List()
    }
  }

  def callPEGIWebsite(game: GameTitle): GameResponse = {
    implicit val backend = HttpURLConnectionBackend()
    Thread.sleep(400)
    val response = sttp.get(uri"https://pegi.info/search-pegi?q=${game.title}").send()
    logger.info(s"call PEGI website, result: ${response.code}")
    GameResponse(game, response.unsafeBody)
  }


  def scrapPEGIWebpage(gameResponse: GameResponse): Option[PEGIDetails] = {
    val doc = JsoupBrowser().parseString(gameResponse.response)

    val resultCount = (doc >> text(".results-count strong")).split(" ")(0).toInt
    logger.info(s"Found $resultCount result(s) on PEGI website")

    resultCount match {
      case 0 => None
      case more =>
        val details = PEGIDetails(
          gameResponse.title.title,
          doc >> text(".body.text-with-summary .info h3"),
          (doc >> element(".body.text-with-summary .age-rating img")).attr("src"),
          doc >> text(".body.text-with-summary .content-main"),
          (doc >> elements(".body.text-with-summary .game:first-child .descriptors img")).map(_.attr("src")).toList)

        logger.info("found: " + details)
        Some(details)
    }
  }

  def writeHTMLTableLigne(details: PEGIDetails): String = {
    s"""
        <tr>
        <td>${details.searchTitle}</td>
        <td>${details.foundTitle}</td>
        <td>${details.consumerAdvice}</td>
        <td><img height="84" width="69" src="${details.ratingURL}"></td>
        <td>${details.descriptorsURL.map(d => s"""<img height="69" width="69" src="$d">""").mkString}</td>
        </tr>
    """
  }

}

