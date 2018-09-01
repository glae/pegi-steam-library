import java.io.{File, FileNotFoundException}
import com.typesafe.scalalogging.StrictLogging
import scala.io.Source

object PEGISteamLibrary extends StrictLogging {

  case class GameTitle(title: String)

  case class PEGIDetails(titleFOund: String, rating: String, todo: String)

  def main(args: Array[String]): Unit = {

    if (!checkCLIArguments(args)) {
      logger.error("Please provide a plain text file as input, e.g.: sbt \"run my-games.txt\"")
      sys.exit(-1)
    }

    parseGameListFile(new File(args.head))


  }

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

  def checkCLIArguments(args: Array[String]): Boolean = args.length == 1 && args.head.trim().nonEmpty

  def scrapPEGIWebpage(game: GameTitle, webpageContent: String): Option[PEGIDetails] = {


    None
  }

}

