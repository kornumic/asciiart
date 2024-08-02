package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import exporter.stringExporter.FileStringExporter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

import java.io.File

class OutputFileHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument with path without directory") {
    val outputFilePath = "test/test.txt"

    val outputFileHandler: ArgumentHandler =
      new OutputFileHandler()

    val createdExporter = mock[FileStringExporter]

    val argument = Argument("output-file", Seq(outputFilePath))
    val expectedConfigHolder = new ConfigHolder(
      None,
      filter = new MixedBWFilter(Seq()),
      None,
      None,
      Some(createdExporter))

    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionFileExporter).thenReturn(None)
    when(configHolder.setFileExporter(any[FileStringExporter]))
      .thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = outputFileHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)

    val file = new File(outputFilePath)
    val dir = Option(file.getParentFile)
    file.delete()
    if (dir.isDefined) dir.get.delete()
  }

  test("processArgument() - handles valid argument with path with directory") {
    val outputFilePath = "test.txt"

    val outputFileHandler: ArgumentHandler =
      new OutputFileHandler()

    val createdExporter = mock[FileStringExporter]

    val argument = Argument("output-file", Seq(outputFilePath))
    val expectedConfigHolder = new ConfigHolder(
      None,
      filter = new MixedBWFilter(Seq()),
      None,
      None,
      Some(createdExporter))

    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionFileExporter).thenReturn(None)
    when(configHolder.setFileExporter(any[FileStringExporter]))
      .thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = outputFileHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)

    val file = new File(outputFilePath)
    val dir = Option(file.getParentFile)
    file.delete()
    if (dir.isDefined) dir.get.delete()
  }

  test("processArgument() - handles already defined exporter") {
    val outputFileHandler: ArgumentHandler =
      new OutputFileHandler()

    val argument = Argument("output-file", Seq("test.txt"))
    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionFileExporter)
      .thenReturn(Some(mock[FileStringExporter]))

    val chainData = ChainData(argument, configHolder)
    assertThrows[IllegalStateException] {
      outputFileHandler.handle(chainData)
    }
  }

  test("processArgument() - handles invalid output file parameter") {
    val outputFileHandler: ArgumentHandler =
      new OutputFileHandler()

    val argument = Argument("output-file", Seq("test.png"))
    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionFileExporter)
      .thenReturn(None)

    val chainData = ChainData(argument, configHolder)
    assertThrows[IllegalArgumentException] {
      outputFileHandler.handle(chainData)
    }
  }
}
