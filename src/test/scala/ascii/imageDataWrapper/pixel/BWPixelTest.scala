package ascii.imageDataWrapper.pixel

import org.scalatest.FunSuite

class BWPixelTest extends FunSuite {
  test("BWPixelTest - check invalid bwValue -1") {
    assertThrows[IllegalArgumentException] {
      BWPixel(-1)
    }
  }

  test("BWPixelTest - check invalid bwValue 256") {
    assertThrows[IllegalArgumentException] {
      BWPixel(256)
    }
  }

  test("BWPixelTest - check valid bwValue 0") {
    val bwPixel = BWPixel(0)
    assert(bwPixel.bwValue == 0)
  }

  test("BWPixelTest - check valid bwValue 255") {
    val bwPixel = BWPixel(255)
    assert(bwPixel.bwValue == 255)
  }

  test("BWPixelTest - check toString") {
    val bwPixel = BWPixel(255)
    assert(bwPixel.toString == " 255 ")
  }
}
