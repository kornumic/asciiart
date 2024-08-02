package ascii.imageDataWrapper.pixel

import org.scalatest.FunSuite

class RGBPixelTest extends FunSuite {
  test("RGBPixelTest - check invalid redValue -1") {
    assertThrows[IllegalArgumentException] {
      RGBPixel(-1, 0, 0)
    }
  }

  test("RGBPixelTest - check invalid redValue 256") {
    assertThrows[IllegalArgumentException] {
      RGBPixel(256, 0, 0)
    }
  }

  test("RGBPixelTest - check invalid greenValue -1") {
    assertThrows[IllegalArgumentException] {
      RGBPixel(0, -1, 0)
    }
  }

  test("RGBPixelTest - check invalid greenValue 256") {
    assertThrows[IllegalArgumentException] {
      RGBPixel(0, 256, 0)
    }
  }

  test("RGBPixelTest - check invalid blueValue -1") {
    assertThrows[IllegalArgumentException] {
      RGBPixel(0, 0, -1)
    }
  }

  test("RGBPixelTest - check invalid blueValue 256") {
    assertThrows[IllegalArgumentException] {
      RGBPixel(0, 0, 256)
    }
  }

  test("RGBPixelTest - check valid redValue 0") {
    val rgbPixel = RGBPixel(0, 0, 0)
    assert(rgbPixel.r == 0)
  }

  test("RGBPixelTest - check valid redValue 255") {
    val rgbPixel = RGBPixel(255, 0, 0)
    assert(rgbPixel.r == 255)
  }

  test("RGBPixelTest - check valid greenValue 0") {
    val rgbPixel = RGBPixel(0, 0, 0)
    assert(rgbPixel.g == 0)
  }

  test("RGBPixelTest - check valid greenValue 255") {
    val rgbPixel = RGBPixel(0, 255, 0)
    assert(rgbPixel.g == 255)
  }

  test("RGBPixelTest - check valid blueValue 0") {
    val rgbPixel = RGBPixel(0, 0, 0)
    assert(rgbPixel.b == 0)
  }

  test("RGBPixelTest - check valid blueValue 255") {
    val rgbPixel = RGBPixel(0, 0, 255)
    assert(rgbPixel.b == 255)
  }

  test("RGBPixelTest - check toString") {
    val rgbPixel = RGBPixel(0, 0, 0)
    assert(rgbPixel.toString == "#000000")
  }
}
