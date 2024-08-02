package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import importer.Importer
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.RGBPixel
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.scalatest.FunSuite
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar

class ImageHandlerTest extends FunSuite with MockitoSugar {

  test("processArgument() - handles valid argument .png") {
    val imageArgumentHandler: ArgumentHandler =
      new ImageHandler()

    val createdImporter = mock[Importer[RasterImage[RGBPixel]]]

    val argument = Argument("image", Seq("test.png"))
    val expectedConfigHolder = new ConfigHolder(
      optionImporter = Some(createdImporter),
      filter = new MixedBWFilter(Seq()),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionImporter).thenReturn(None)
    when(configHolder.setImporter(any[Importer[RasterImage[RGBPixel]]]))
      .thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = imageArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
  }

  test("processArgument() - handles valid argument .jpg") {
    val imageArgumentHandler: ArgumentHandler =
      new ImageHandler()

    val createdImporter = mock[Importer[RasterImage[RGBPixel]]]

    val argument = Argument("image", Seq("test.jpg"))
    val expectedConfigHolder = new ConfigHolder(
      optionImporter = Some(createdImporter),
      filter = new MixedBWFilter(Seq()),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionImporter).thenReturn(None)
    when(configHolder.setImporter(any[Importer[RasterImage[RGBPixel]]]))
      .thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = imageArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
  }

  test("processArgument() - handles invalid argument") {
    val imageArgumentHandler: ArgumentHandler =
      new ImageHandler()

    val argument = Argument("image", Seq("invalid"))
    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionImporter).thenReturn(None)

    val chainData = ChainData(argument, configHolder)
    assertThrows[IllegalArgumentException] {
      imageArgumentHandler.handle(chainData)
    }
  }

  test("processArgument() - handles already defined importer") {
    val imageArgumentHandler: ArgumentHandler =
      new ImageHandler()

    val argument = Argument("image", Seq("test.png"))
    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionImporter)
      .thenReturn(Some(mock[Importer[RasterImage[RGBPixel]]]))

    val chainData = ChainData(argument, configHolder)
    assertThrows[IllegalStateException] {
      imageArgumentHandler.handle(chainData)
    }
  }
}
