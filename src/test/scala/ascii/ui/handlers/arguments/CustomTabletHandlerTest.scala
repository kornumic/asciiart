package ascii.ui.handlers.arguments

import converter.Converter
import ascii.converter.imageConverter.{
  BWtoAsciiRasterConverter,
  RasterConverter
}
import ascii.imageDataWrapper.pixel.{BWPixel, CharPixel}
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class CustomTabletHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument") {
    val customTableArgumentHandler: ArgumentHandler =
      new CustomTabletHandler()

    val argument = Argument("custom-table", Seq("0123456"))

    val definedConverter = mock[RasterConverter[BWPixel, CharPixel]]
    val expectedConfigHolder = mock[ConfigHolder]
    when(expectedConfigHolder.getOptionToCharImageConverter)
      .thenReturn(Some(definedConverter))

    val providedConfigHolder = mock[ConfigHolder]
    when(providedConfigHolder.getOptionToCharImageConverter)
      .thenReturn(None)
    when(
      providedConfigHolder.setToCharImageConverter(
        any[RasterConverter[BWPixel, CharPixel]]))
      .thenReturn(expectedConfigHolder)
    val chainData = ChainData(argument, providedConfigHolder)

    val result = customTableArgumentHandler.handle(chainData)
    assert(result.isDefined)
    assert(result.get.configHolder.getOptionToCharImageConverter.isDefined)
  }

  test("processArgument() - process already defined table") {
    val customTableArgumentHandler: ArgumentHandler =
      new CustomTabletHandler()

    val argument = Argument("custom-table", Seq("0123456"))

    val definedConverter = mock[RasterConverter[BWPixel, CharPixel]]
    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionToCharImageConverter)
      .thenReturn(Some(definedConverter))

    val chainData = ChainData(argument, configHolder)

    intercept[IllegalStateException](
      customTableArgumentHandler.handle(chainData))
  }
}
