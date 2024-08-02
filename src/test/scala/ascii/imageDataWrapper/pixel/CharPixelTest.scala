package ascii.imageDataWrapper.pixel

import org.scalatest.FunSuite

class CharPixelTest extends FunSuite {
  test("CharPixelTest - check invalid charValue 256") {
    assertThrows[IllegalArgumentException] {
      CharPixel(256)
    }
  }

  test("CharPixelTest - check valid charValue 0") {
    val charPixel = CharPixel(0)
    assert(charPixel.charValue == 0)
  }

  test("CharPixelTest - check valid charValue 127") {
    val charPixel = CharPixel(127)
    assert(charPixel.charValue == 127)
  }

  test("CharPixelTest - check toString") {
    val charPixel = CharPixel(32)
    assert(charPixel.toString == " ")
  }
}
