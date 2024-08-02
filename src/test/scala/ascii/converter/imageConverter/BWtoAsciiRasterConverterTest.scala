package ascii.converter.imageConverter

import ascii.converter.pixelConverter.PixelConverter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.{BWPixel, CharPixel, RGBPixel}
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class BWtoAsciiRasterConverterTest
    extends FunSuite
    with MockitoSugar
    with BeforeAndAfterAll {
  var pixelConverter: PixelConverter[BWPixel, CharPixel] = _
  override protected def beforeAll(): Unit = {
    super.beforeAll()

    pixelConverter = mock[PixelConverter[BWPixel, CharPixel]]

    when(pixelConverter.convert(BWPixel(1)))
      .thenReturn(CharPixel('a'))
    when(pixelConverter.convert(BWPixel(2)))
      .thenReturn(CharPixel('b'))
    when(pixelConverter.convert(BWPixel(3)))
      .thenReturn(CharPixel('c'))
    when(pixelConverter.convert(BWPixel(4)))
      .thenReturn(CharPixel('d'))
    when(pixelConverter.convert(BWPixel(5)))
      .thenReturn(CharPixel('e'))
    when(pixelConverter.convert(BWPixel(0)))
      .thenReturn(CharPixel(0))
    when(pixelConverter.convert(BWPixel(255)))
      .thenReturn(CharPixel(127))
  }

  test("BWtoAsciiRasterConverterTest - check dimensions 1x1") {
    val converter = new BWtoAsciiRasterConverter(pixelConverter)
    val pixels = Seq(Seq(BWPixel(1)))
    val image = new RasterImage[BWPixel](pixels)
    val convertedImage = converter.convert(image)
    assert(convertedImage.getWidth == 1)
    assert(convertedImage.getHeight == 1)
  }

  test("BWtoAsciiRasterConverterTest - check dimensions 2x2") {
    val converter = new BWtoAsciiRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(3), BWPixel(4))
    )
    val image = new RasterImage[BWPixel](pixels)
    val convertedImage = converter.convert(image)
    assert(convertedImage.getWidth == 2)
    assert(convertedImage.getHeight == 2)
  }

  test("BWtoAsciiRasterConverterTest - check dimensions 3x3") {
    val converter = new BWtoAsciiRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(BWPixel(1), BWPixel(2), BWPixel(3)),
      Seq(BWPixel(4), BWPixel(5), BWPixel(0)),
      Seq(BWPixel(255), BWPixel(255), BWPixel(255))
    )
    val image = new RasterImage[BWPixel](pixels)
    val convertedImage = converter.convert(image)
    assert(convertedImage.getWidth == 3)
    assert(convertedImage.getHeight == 3)
  }

  test("BWtoAsciiRasterConverterTest - check dimensions 3x1") {
    val converter = new BWtoAsciiRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(BWPixel(1), BWPixel(2), BWPixel(3)),
    )
    val image = new RasterImage[BWPixel](pixels)
    val convertedImage = converter.convert(image)
    assert(convertedImage.getWidth == 3)
    assert(convertedImage.getHeight == 1)
  }

  test("BWtoAsciiRasterConverterTest - 1x1 image") {
    val converter = new BWtoAsciiRasterConverter(pixelConverter)
    val pixels = Seq(Seq(BWPixel(1)))
    val image = new RasterImage[BWPixel](pixels)
    val convertedPixels = converter.convert(image).getPixels
    assert(convertedPixels == Seq(Seq(CharPixel('a'))))
  }

  test("BWtoAsciiRasterConverterTest - 2x2 image") {
    val converter = new BWtoAsciiRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(3), BWPixel(4))
    )
    val image = new RasterImage[BWPixel](pixels)
    val convertedPixels = converter.convert(image).getPixels
    assert(
      convertedPixels == Seq(
        Seq(CharPixel('a'), CharPixel('b')),
        Seq(CharPixel('c'), CharPixel('d'))
      ))
  }

  test("BWtoAsciiRasterConverterTest - 3x3 image") {
    val converter = new BWtoAsciiRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(BWPixel(1), BWPixel(2), BWPixel(3)),
      Seq(BWPixel(4), BWPixel(5), BWPixel(0)),
      Seq(BWPixel(255), BWPixel(255), BWPixel(255))
    )
    val image = new RasterImage[BWPixel](pixels)
    val convertedPixels = converter.convert(image).getPixels
    assert(
      convertedPixels == Seq(
        Seq(CharPixel('a'), CharPixel('b'), CharPixel('c')),
        Seq(CharPixel('d'), CharPixel('e'), CharPixel(0)),
        Seq(CharPixel(127), CharPixel(127), CharPixel(127))
      ))
  }

  test("BWtoAsciiRasterConverterTest - 1x3 image") {
    val converter = new BWtoAsciiRasterConverter(pixelConverter)
    val pixels = Seq(
      Seq(BWPixel(1), BWPixel(2), BWPixel(3)),
    )
    val image = new RasterImage[BWPixel](pixels)
    val convertedPixels = converter.convert(image).getPixels
    assert(
      convertedPixels == Seq(
        Seq(CharPixel('a'), CharPixel('b'), CharPixel('c')),
      ))
  }
}
