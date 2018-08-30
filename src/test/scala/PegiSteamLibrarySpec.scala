import org.scalatest.{FlatSpec, Matchers}

class PegiSteamLibrarySpec extends FlatSpec with Matchers {

  it should "take one argument as CLI input" in {

    PEGISteamLibrary.checkArguments(Array()) should be (false)
    PEGISteamLibrary.checkArguments(Array("a","b")) should be (false)
    PEGISteamLibrary.checkArguments(Array("OK")) should be (true)
  }

  it should "read the input file as a string list" in {

    PEGISteamLibrary.parseInputFile()


    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}