object PEGISteamLibrary {


  def parseInputFile(): List[VideoGameTitle] = {
    List()
  }


  def main(args: Array[String]): Unit = {

    if (checkArguments(args)) {
      println("\nPlease provide a plain text file as input, e.g.:\n    sbt \"run my-games.txt\"\n")
      sys.exit(-1)
    }

  }

  def checkArguments(args: Array[String]): Boolean = args.length == 1

  case class VideoGameTitle(title: String)

}

