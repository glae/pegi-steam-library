import java.io.File

import com.typesafe.scalalogging.StrictLogging

import scala.io.Source

object PEGISteamLibrary extends StrictLogging {

  case class GameTitle(title: String)

  case class PEGIDetails(titleFOund: String, rating:String, todo:String)

  def main(args: Array[String]): Unit = {

    if (!checkCLIArguments(args)) {
      println("\nPlease provide a plain text file as input, e.g.:\n    sbt \"run my-games.txt\"\n")
      sys.exit(-1)
    }

  }

  def parseGameListFile(gameList: File): List[GameTitle] = Source.fromFile(gameList).getLines().toList.map(GameTitle)

  def checkCLIArguments(args: Array[String]): Boolean = args.length == 1 && args.head.trim().nonEmpty

  def scrapPEGIWebpage(game: GameTitle, webpageContent:String): Option[PEGIDetails] = {
    None
  }

}

