import java.io.File

import scala.io.Source

object PEGISteamLibrary {

  case class VideoGameTitle(title: String)

  def main(args: Array[String]): Unit = {

    if (!checkCLIArguments(args)) {
      println("\nPlease provide a plain text file as input, e.g.:\n    sbt \"run my-games.txt\"\n")
      sys.exit(-1)
    }

  }

  def parseGameListFile(gameList: File): List[VideoGameTitle] = Source.fromFile(gameList).getLines().toList.map(VideoGameTitle)

  def checkCLIArguments(args: Array[String]): Boolean = args.length == 1 && args.head.trim().nonEmpty


}

