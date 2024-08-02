package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import exporter.Exporter
import exporter.stringExporter.ConsoleStringExporter
import importer.Importer
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.RGBPixel
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class OutputConsoleHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument") {
    val outputConsoleArgumentHandler: ArgumentHandler =
      new OutputConsoleHandler()

    val createdExporter = mock[ConsoleStringExporter]

    val argument = Argument("output-console", Seq())
    val expectedConfigHolder = new ConfigHolder(
      optionImporter = None,
      filter = new MixedBWFilter(Seq()),
      None,
      optionConsoleExporter = Some(createdExporter))

    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionConsoleExporter).thenReturn(None)
    when(configHolder.setConsoleExporter(any[ConsoleStringExporter]))
      .thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = outputConsoleArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
  }

  test("processArgument() - handles already defined exporter") {
    val outputConsoleArgumentHandler: ArgumentHandler =
      new OutputConsoleHandler()

    val argument = Argument("output-console", Seq())
    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionConsoleExporter)
      .thenReturn(Some(mock[ConsoleStringExporter]))

    val chainData = ChainData(argument, configHolder)
    intercept[IllegalStateException] {
      outputConsoleArgumentHandler.handle(chainData)
    }
  }
}
