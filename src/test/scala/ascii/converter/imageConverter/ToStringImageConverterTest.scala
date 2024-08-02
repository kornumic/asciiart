package ascii.converter.imageConverter

import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.CharPixel
import org.scalatest.FunSuite

class ToStringImageConverterTest extends FunSuite {

  test("ToStringImageConverter - conversion to string") {
    val converter = new ToStringImageConverter
    val pixels = Seq(
      Seq(CharPixel('t'), CharPixel('e')),
      Seq(CharPixel('s'), CharPixel('t'))
    )
    val image = new RasterImage[CharPixel](pixels)
    assert(converter.convert(image) == "te\nst")
  }

  test("ToStringImageConverter - image 1x1") {
    val converter = new ToStringImageConverter
    val pixels = Seq(Seq(CharPixel(' ')))
    val image = new RasterImage[CharPixel](pixels)
    assert(converter.convert(image) == " ")
  }

}
