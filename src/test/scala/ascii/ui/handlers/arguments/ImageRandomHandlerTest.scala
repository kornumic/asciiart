package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import importer.Importer
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.RGBPixel
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class ImageRandomHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument") {
    val imageRandomArgumentHandler: ArgumentHandler =
      new ImageRandomHandler()

    val createdImporter = mock[Importer[RasterImage[RGBPixel]]]

    val argument = Argument("image-random", Seq())
    val expectedConfigHolder = new ConfigHolder(
      optionImporter = Some(createdImporter),
      filter = new MixedBWFilter(Seq()),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionImporter).thenReturn(None)
    when(configHolder.setImporter(any[Importer[RasterImage[RGBPixel]]]))
      .thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = imageRandomArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
  }

  test("processArgument() - handles already defined importer") {
    val imageRandomArgumentHandler: ArgumentHandler =
      new ImageRandomHandler()

    val argument = Argument("image-random", Seq())
    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionImporter)
      .thenReturn(Some(mock[Importer[RasterImage[RGBPixel]]]))

    val chainData = ChainData(argument, configHolder)
    assertThrows[IllegalStateException] {
      imageRandomArgumentHandler.handle(chainData)
    }
  }
}
