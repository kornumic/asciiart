package ascii.ui.facade

import exporter.stringExporter.{ConsoleStringExporter, FileStringExporter}
import importer.Importer
import ascii.converter.imageConverter.{RGBtoBWRasterConverter, RasterConverter, ToStringImageConverter}
import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.{BWPixel, CharPixel, RGBPixel}
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.mockito.internal.matchers.Any
import org.mockito.stubbing.Answer
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class ConversionFacadeTest
    extends FunSuite
    with MockitoSugar
    with BeforeAndAfterAll {

  var importer: Importer[RasterImage[RGBPixel]] = _
  var toBWConverter: RGBtoBWRasterConverter = _
  var filter: MixedBWFilter = _
  var toCharImageConverter: RasterConverter[BWPixel, CharPixel] = _
  var toAsciiImageConverter: ToStringImageConverter = _
  var consoleExporter: ConsoleStringExporter = _
  var fileExporter: FileStringExporter = _
  var configHolder: ConfigHolder = _
  var facade: ConversionFacade = _

  val testString = "aa\naa"

  val rgbImage = new RasterImage[RGBPixel](
    Seq(
      Seq(RGBPixel(0, 0, 0), RGBPixel(0, 0, 0)),
      Seq(RGBPixel(0, 0, 0), RGBPixel(0, 0, 0))
    ))

  val bwImage = new RasterImage[BWPixel](
    Seq(
      Seq(BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0))
    ))

  val charImage = new RasterImage[CharPixel](
    Seq(
      Seq(CharPixel('a'), CharPixel('a')),
      Seq(CharPixel('a'), CharPixel('a'))
    ))

  override def beforeAll(): Unit = {
    super.beforeAll()

    toBWConverter = mock[RGBtoBWRasterConverter]
    when(toBWConverter.convert(rgbImage)).thenReturn(bwImage)

    toAsciiImageConverter = mock[ToStringImageConverter]
    when(toAsciiImageConverter.convert(charImage)).thenReturn(testString)

    importer = mock[Importer[RasterImage[RGBPixel]]]
    when(importer.importData()).thenReturn(rgbImage)

    filter = mock[MixedBWFilter]
    when(filter.apply(bwImage)).thenReturn(bwImage)

    toCharImageConverter = mock[RasterConverter[BWPixel, CharPixel]]
    when(toCharImageConverter.convert(bwImage)).thenReturn(charImage)

    consoleExporter = mock[ConsoleStringExporter]
    doNothing.when(consoleExporter).`export`(testString)

    fileExporter = mock[FileStringExporter]
    doNothing.when(fileExporter).`export`(testString)
  }

  test("loadConfiguration - no arguments") {
    facade = new ConversionFacade(toBWConverter, toAsciiImageConverter)
    val arguments: Seq[Argument] = Seq()

    val initialHandler = mock[ArgumentHandler]

    intercept[IllegalArgumentException](
      facade.loadConfiguration(arguments, initialHandler))

  }

  test("loadConfiguration - one argument") {
    facade = new ConversionFacade(toBWConverter, toAsciiImageConverter)
    val argument = Argument("--test", Seq())
    val arguments: Seq[Argument] = Seq(argument)

    val handlerChain = mock[ArgumentHandler]

    configHolder = mock[ConfigHolder]
    doNothing.when(configHolder).validate()
    when(configHolder.initializeImageConverter()).thenReturn(configHolder)

    val returnedChainData = ChainData(argument, configHolder)

    when(handlerChain.handle(any[ChainData]))
      .thenReturn(Some(returnedChainData))

    facade.loadConfiguration(arguments, handlerChain)

    verify(handlerChain, times(1)).handle(any[ChainData])
    verify(configHolder, times(1)).initializeImageConverter()
  }

  test("loadConfiguration - two arguments") {
    facade = new ConversionFacade(toBWConverter, toAsciiImageConverter)
    val argument1 = Argument("--test", Seq())
    val argument2 = Argument("--test2", Seq())
    val arguments: Seq[Argument] = Seq(argument1, argument2)

    val handlerChain = mock[ArgumentHandler]

    configHolder = mock[ConfigHolder]
    when(configHolder.initializeImageConverter()).thenReturn(configHolder)

    val returnedChainData = ChainData(argument1, configHolder)

    when(handlerChain.handle(any[ChainData]))
      .thenReturn(Some(returnedChainData))

    facade.loadConfiguration(arguments, handlerChain)

    verify(handlerChain, times(2)).handle(any[ChainData])
    verify(configHolder, times(1)).initializeImageConverter()
  }

  test("loadConfiguration - handler returns None") {
    facade = new ConversionFacade(toBWConverter, toAsciiImageConverter)
    val argument1 = Argument("--test", Seq())
    val arguments: Seq[Argument] = Seq(argument1)

    val handlerChain = mock[ArgumentHandler]

    configHolder = mock[ConfigHolder]
    when(configHolder.initializeImageConverter()).thenReturn(configHolder)

    when(handlerChain.handle(any[ChainData]))
      .thenReturn(None)

    intercept[IllegalStateException](facade.loadConfiguration(arguments, handlerChain))

    verify(handlerChain, times(1)).handle(any[ChainData])
    verify(configHolder, times(0)).initializeImageConverter()
  }

  test(
    "Basic conversion should import data once, convert to BW, apply filter, convert to Char and export to console and file") {

    facade = new ConversionFacade(toBWConverter, toAsciiImageConverter)

    configHolder = mock[ConfigHolder]
    doNothing.when(configHolder).validate()

    when(configHolder.getOptionImporter).thenReturn(Some(importer))
    when(configHolder.getOptionFilter).thenReturn(Some(filter))
    when(configHolder.getOptionToCharImageConverter)
      .thenReturn(Some(toCharImageConverter))
    when(configHolder.getOptionConsoleExporter)
      .thenReturn(Some(consoleExporter))
    when(configHolder.getOptionFileExporter)
      .thenReturn(Some(fileExporter))

    facade.convert(configHolder)

    verify(importer, times(1)).importData()
    verify(toBWConverter, times(1)).convert(rgbImage)
    verify(filter, times(1)).apply(bwImage)
    verify(toCharImageConverter, times(1)).convert(bwImage)
    verify(consoleExporter, times(1)).`export`(testString)
    verify(fileExporter, times(1)).`export`(testString)
  }

  test("One exporter is provided - console") {
    facade = new ConversionFacade(toBWConverter, toAsciiImageConverter)

    configHolder = mock[ConfigHolder]
    doNothing.when(configHolder).validate()

    when(configHolder.getOptionImporter).thenReturn(Some(importer))
    when(configHolder.getOptionFilter).thenReturn(Some(filter))
    when(configHolder.getOptionToCharImageConverter)
      .thenReturn(Some(toCharImageConverter))

    when(configHolder.getOptionConsoleExporter)
      .thenReturn(Some(consoleExporter))
    when(configHolder.getOptionFileExporter)
      .thenReturn(None)

    facade.convert(configHolder)

    verify(fileExporter, times(0)).`export`(_)
  }

  test("One exporter is provided - file") {
    facade = new ConversionFacade(toBWConverter, toAsciiImageConverter)

    configHolder = mock[ConfigHolder]
    doNothing.when(configHolder).validate()

    when(configHolder.getOptionImporter).thenReturn(Some(importer))
    when(configHolder.getOptionFilter).thenReturn(Some(filter))
    when(configHolder.getOptionToCharImageConverter)
      .thenReturn(Some(toCharImageConverter))

    when(configHolder.getOptionConsoleExporter)
      .thenReturn(None)
    when(configHolder.getOptionFileExporter)
      .thenReturn(Some(fileExporter))

    facade.convert(configHolder)

    verify(consoleExporter, times(0)).`export`(_)
  }

}
