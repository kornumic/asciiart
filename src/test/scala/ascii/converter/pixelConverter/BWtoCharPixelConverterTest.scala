package ascii.converter.pixelConverter

import transformationTable.TransformationTable
import ascii.imageDataWrapper.pixel.BWPixel
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class BWtoCharPixelConverterTest
    extends FunSuite
    with MockitoSugar
    with BeforeAndAfterAll {
  var transformationTable: TransformationTable = _

  override protected def beforeAll(): Unit = {
    super.beforeAll()

    transformationTable = mock[TransformationTable]
    when(transformationTable.transform(1)).thenReturn('a')
    when(transformationTable.transform(2)).thenReturn('b')
    when(transformationTable.transform(3)).thenReturn('c')
    when(transformationTable.transform(4)).thenReturn('d')
    when(transformationTable.transform(5)).thenReturn('e')
    when(transformationTable.transform(-1))
      .thenThrow(new IllegalArgumentException)
    when(transformationTable.transform(256))
      .thenThrow(new IllegalArgumentException)
  }

  test("BWtoCharPixelConverterTest - single pixel") {
    val pixelConverter = new BWtoCharPixelConverter(transformationTable)
    val pixel = BWPixel(1)
    assert(pixelConverter.convert(pixel).charValue == 'a')
  }

  test("BWtoCharPixelConverterTest - multiple pixels") {
    val pixelConverter = new BWtoCharPixelConverter(transformationTable)
    val pixels = Seq(BWPixel(1), BWPixel(2), BWPixel(3), BWPixel(4), BWPixel(5))
    val convertedPixels = pixels.map(pixelConverter.convert(_).charValue)
    assert(convertedPixels == Seq('a', 'b', 'c', 'd', 'e'))
  }

}
