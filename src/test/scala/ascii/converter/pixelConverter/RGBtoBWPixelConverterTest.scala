package ascii.converter.pixelConverter

import ascii.imageDataWrapper.pixel.{BWPixel, RGBPixel}
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class RGBtoBWPixelConverterTest
    extends FunSuite
    with MockitoSugar
    with BeforeAndAfterAll {

  test("RGBtoBWPixelConverterTest - single pixel") {
    val pixelConverter = new RGBtoBWPixelConverter()
    val pixel = RGBPixel(1, 2, 3)
    assert(pixelConverter.convert(pixel).bwValue == BWPixel(2).bwValue)
  }
}
