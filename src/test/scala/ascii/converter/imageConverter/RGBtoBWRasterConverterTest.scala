package ascii.converter.imageConverter

import ascii.converter.pixelConverter.PixelConverter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.{BWPixel, RGBPixel}
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class RGBtoBWRasterConverterTest
    extends FunSuite
    with MockitoSugar
    with BeforeAndAfterAll {
  var pixelConverter: PixelConverter[RGBPixel, BWPixel] = _

  override protected def beforeAll(): Unit = {
    super.beforeAll()

    pixelConverter = mock[PixelConverter[RGBPixel, BWPixel]]

    when(pixelConverter.convert(RGBPixel(1, 2, 3)))
      .thenReturn(BWPixel(2))
    when(pixelConverter.convert(RGBPixel(0, 0, 0)))
      .thenReturn(BWPixel(0))
    when(pixelConverter.convert(RGBPixel(255, 255, 255)))
      .thenReturn(BWPixel(255))
  }

  test("RGBtoBWRasterConverterTest - check dimensions 1x1") {
    val converter = new RGBtoBWRasterConverter(pixelConverter)
    val pixels = Seq(Seq(RGBPixel(1, 2, 3)))
    val image = new RasterImage[RGBPixel](pixels)
    val convertedImage = converter.convert(image)
    assert(convertedImage.getWidth == 1)
    assert(convertedImage.getHeight == 1)
  }

  test("RGBtoBWRasterConverterTest - check dimensions 2x2") {
    val converter = new RGBtoBWRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0)),
      Seq(RGBPixel(255, 255, 255), RGBPixel(1, 2, 3))
    )
    val image = new RasterImage[RGBPixel](pixels)
    val convertedImage = converter.convert(image)
    assert(convertedImage.getWidth == 2)
    assert(convertedImage.getHeight == 2)
  }

  test("RGBtoBWRasterConverterTest - check dimensions 3x3") {
    val converter = new RGBtoBWRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0), RGBPixel(255, 255, 255)),
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0), RGBPixel(255, 255, 255)),
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0), RGBPixel(255, 255, 255))
    )
    val image = new RasterImage[RGBPixel](pixels)
    val convertedImage = converter.convert(image)
    assert(convertedImage.getWidth == 3)
    assert(convertedImage.getHeight == 3)
  }

  test("RGBtoBWRasterConverterTest - check dimensions 3x1") {
    val converter = new RGBtoBWRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0), RGBPixel(255, 255, 255)),
    )
    val image = new RasterImage[RGBPixel](pixels)
    val convertedImage = converter.convert(image)
    assert(convertedImage.getWidth == 3)
    assert(convertedImage.getHeight == 1)
  }

  test("RGBtoBWRasterConverterTest - 1x1 image") {
    val converter = new RGBtoBWRasterConverter(pixelConverter)
    val pixels = Seq(Seq(RGBPixel(1, 2, 3)))
    val image = new RasterImage[RGBPixel](pixels)
    val convertedPixels = converter.convert(image).getPixels
    assert(convertedPixels == Seq(Seq(BWPixel(2))))
  }

  test("RGBtoBWRasterConverterTest - 2x2 image") {
    val converter = new RGBtoBWRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0)),
      Seq(RGBPixel(255, 255, 255), RGBPixel(1, 2, 3))
    )
    val image = new RasterImage[RGBPixel](pixels)
    val convertedPixels = converter.convert(image).getPixels
    assert(
      convertedPixels == Seq(
        Seq(BWPixel(2), BWPixel(0)),
        Seq(BWPixel(255), BWPixel(2))
      ))
  }

  test("RGBtoBWRasterConverterTest - 3x3 image") {
    val converter = new RGBtoBWRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0), RGBPixel(255, 255, 255)),
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0), RGBPixel(255, 255, 255)),
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0), RGBPixel(255, 255, 255))
    )
    val image = new RasterImage[RGBPixel](pixels)
    val convertedPixels = converter.convert(image).getPixels
    assert(
      convertedPixels == Seq(
        Seq(BWPixel(2), BWPixel(0), BWPixel(255)),
        Seq(BWPixel(2), BWPixel(0), BWPixel(255)),
        Seq(BWPixel(2), BWPixel(0), BWPixel(255))
      ))
  }

  test("RGBtoBWRasterConverterTest - 1x3 image") {
    val converter = new RGBtoBWRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(RGBPixel(1, 2, 3), RGBPixel(0, 0, 0), RGBPixel(255, 255, 255)),
    )
    val image = new RasterImage[RGBPixel](pixels)
    val convertedPixels = converter.convert(image).getPixels
    assert(
      convertedPixels == Seq(
        Seq(BWPixel(2), BWPixel(0), BWPixel(255)),
      ))
  }
}
