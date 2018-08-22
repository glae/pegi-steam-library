object PEGISteamLibrary {

  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("\nPlease provide a plain text file as input, e.g.:\n    sbt \"run my-games.txt\"\n")
      sys.exit(-1)
    }

    println(args)
    println("bonjour")
  }
}
