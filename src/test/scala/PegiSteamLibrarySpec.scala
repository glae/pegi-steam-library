import java.io.File

import PEGISteamLibrary.VideoGameTitle
import org.scalatest.{FlatSpec, Matchers}

class PegiSteamLibrarySpec extends FlatSpec with Matchers {


  it should "take one argument as CLI input" in {

    PEGISteamLibrary.checkCLIArguments(Array()) should be(false)
    PEGISteamLibrary.checkCLIArguments(Array("a", "b")) should be(false)
    PEGISteamLibrary.checkCLIArguments(Array("    ")) should be(false)
    PEGISteamLibrary.checkCLIArguments(Array("OK")) should be(true)
  }


  it should "read the input file as a string list" in {

    PEGISteamLibrary.parseGameListFile(new File("my-games.txt")) should be(
      List(
        VideoGameTitle("Hyper Light Drifter"),
        VideoGameTitle("World of Warcraft: Battle for Azeroth"),
        VideoGameTitle("Yoshi for Nintendo Switch"),
        VideoGameTitle("Mass Effect"),
        VideoGameTitle("DOOM GTS"),
        VideoGameTitle("WWE Survivor Series"),
        VideoGameTitle("Chasm"),
        VideoGameTitle("Crusader Kings II"),
        VideoGameTitle("Gold Rush: The Game"),
        VideoGameTitle("Tanzia"),
        VideoGameTitle("The Jackbox Party Pack 3"),
        VideoGameTitle("Mario Tennis Aces"),
        VideoGameTitle("Journey"),
        VideoGameTitle("Mafia III"),
        VideoGameTitle("Call of Duty: Black Ops III"))
    )
  }

}