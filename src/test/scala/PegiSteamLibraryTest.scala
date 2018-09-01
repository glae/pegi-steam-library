import java.io.File
import PEGISteamLibrary.{GameTitle, PEGIDetails}
import org.scalatest.{FlatSpec, Matchers}
import scala.io.Source

class PEGISteamLibraryTest extends FlatSpec with Matchers {


  it should "take one argument as CLI input" in {

    PEGISteamLibrary.checkCLIArguments(Array()) should be(false)
    PEGISteamLibrary.checkCLIArguments(Array("a", "b")) should be(false)
    PEGISteamLibrary.checkCLIArguments(Array("    ")) should be(false)
    PEGISteamLibrary.checkCLIArguments(Array("OK")) should be(true)
  }


  it should "read the input file as a string list" in {

    PEGISteamLibrary.parseGameListFile(new File("my-games.txt")) should be(
      List(
        GameTitle("Hyper Light Drifter"),
        GameTitle("World of Warcraft: Battle for Azeroth"),
        GameTitle("Yoshi for Nintendo Switch"),
        GameTitle("Mass Effect"),
        GameTitle("DOOM GTS"),
        GameTitle("WWE Survivor Series"),
        GameTitle("Chasm"),
        GameTitle("Crusader Kings II"),
        GameTitle("Gold Rush: The Game"),
        GameTitle("Tanzia"),
        GameTitle("The Jackbox Party Pack 3"),
        GameTitle("Mario Tennis Aces"),
        GameTitle("Journey"),
        GameTitle("Mafia III"),
        GameTitle("Call of Duty: Black Ops III"),
        GameTitle("Phobia 2"),
      )
    )
  }


  it should "return *zero* game detail when there is no result from game query in PEGI webpage" in {

    PEGISteamLibrary.scrapPEGIWebpage(GameTitle("Phobia 2"), HTMLTestFile("PEGI-Phobia 2-no-result.html")) should be(None)
  }


  it should "return *one* game detail when there is one result from game query in PEGI webpage" in {

    PEGISteamLibrary.scrapPEGIWebpage(
      GameTitle("World of Warcraft: Battle for Azeroth"), HTMLTestFile("PEGI-World of Warcraft-unique-result.html"))
      .shouldBe(Some(PEGIDetails("", "", "")))
  }


  it should "return *one* game detail result when there are many results (arbitrary: the first one) from game query in PEGI webpage" in {

    PEGISteamLibrary.scrapPEGIWebpage(GameTitle("Hyper Light Drifter"), HTMLTestFile("PEGI-Hyper Light Drifter-multiple-result.html"))
      .shouldBe(Some(PEGIDetails("", "", "")))
  }


  private def HTMLTestFile(filename: String) = Source.fromResource(filename).mkString

}

