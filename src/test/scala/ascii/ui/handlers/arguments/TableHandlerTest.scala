package ascii.ui.handlers.arguments

import ascii.converter.imageConverter.RasterConverter
import ascii.imageDataWrapper.pixel.{BWPixel, CharPixel}
import ascii.ui.ConfigHolder
import ascii.ui.exceptions.UnknownParameterException
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class TableHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument - bourke") {
    val tableArgumentHandler: ArgumentHandler =
      new TableHandler()

    val argument = Argument("table", Seq("bourke"))

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

    val result = tableArgumentHandler.handle(chainData)
    assert(result.isDefined)
    assert(result.get.configHolder.getOptionToCharImageConverter.isDefined)
  }

  test("processArgument() - handles valid argument - bourke-short") {
    val tableArgumentHandler: ArgumentHandler =
      new TableHandler()

    val argument = Argument("table", Seq("bourke-short"))

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

    val result = tableArgumentHandler.handle(chainData)
    assert(result.isDefined)
    assert(result.get.configHolder.getOptionToCharImageConverter.isDefined)
  }

  test("processArgument() - handles valid argument - non-linear") {
    val tableArgumentHandler: ArgumentHandler =
      new TableHandler()

    val argument = Argument("table", Seq("non-linear"))

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

    val result = tableArgumentHandler.handle(chainData)
    assert(result.isDefined)
    assert(result.get.configHolder.getOptionToCharImageConverter.isDefined)
  }

  test("processArgument() - process already defined table") {
    val tableArgumentHandler: ArgumentHandler =
      new TableHandler()

    val argument = Argument("table", Seq("bourke"))

    val definedConverter = mock[RasterConverter[BWPixel, CharPixel]]
    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionToCharImageConverter)
      .thenReturn(Some(definedConverter))

    val chainData = ChainData(argument, configHolder)

    intercept[IllegalStateException](tableArgumentHandler.handle(chainData))
  }

  test("processArgument() - handles invalid argument") {
    val tableArgumentHandler: ArgumentHandler =
      new TableHandler()

    val argument = Argument("table", Seq("invalid"))

    val configHolder = mock[ConfigHolder]
    val chainData = ChainData(argument, configHolder)

    assertThrows[IllegalArgumentException] {
      tableArgumentHandler.handle(chainData)
    }
  }

}
