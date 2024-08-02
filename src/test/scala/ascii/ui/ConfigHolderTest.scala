package ascii.ui

import exporter.stringExporter.{ConsoleStringExporter, FileStringExporter}
import importer.Importer
import ascii.converter.imageConverter.RasterConverter
import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.{BWPixel, CharPixel, RGBPixel}
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class ConfigHolderTest
    extends FunSuite
    with MockitoSugar
    with BeforeAndAfterAll {
  var definedImporter: Importer[RasterImage[RGBPixel]] = _
  var definedFilter: MixedBWFilter = _
  var definedConverter: RasterConverter[BWPixel, CharPixel] = _
  var definedConsoleExporter: ConsoleStringExporter = _
  var definedFileExporter: FileStringExporter = _

  override def beforeAll(): Unit = {
    super.beforeAll()

    definedImporter = mock[Importer[RasterImage[RGBPixel]]]
    definedFilter = mock[MixedBWFilter]
    definedConverter = mock[RasterConverter[BWPixel, CharPixel]]
    definedConsoleExporter = mock[ConsoleStringExporter]
    definedFileExporter = mock[FileStringExporter]
  }

  test(
    "validate() should throw an IllegalArgumentException if no importer is defined") {
    val configHolder = new ConfigHolder(
      None,
      definedFilter,
      Some(definedConverter),
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    assertThrows[IllegalArgumentException](configHolder.validate())
  }

  test(
    "validate() should throw an IllegalArgumentException if no converter is defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    assertThrows[IllegalArgumentException](configHolder.validate())
  }

  test(
    "validate() should throw an IllegalArgumentException if no exporter is defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      Some(definedConverter),
      None,
      None
    )

    assertThrows[IllegalArgumentException](configHolder.validate())
  }

  test(
    "validate() should not throw an IllegalArgumentException if all required fields are defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      Some(definedConverter),
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    configHolder.validate()
  }

  test(
    "initializeImageConverter() should return a new ConfigHolder with a default converter if no converter is defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    val newConfigHolder = configHolder.initializeImageConverter()

    assert(newConfigHolder.getOptionToCharImageConverter.isDefined)
  }

  test(
    "initializeImageConverter() should return the same ConfigHolder if a converter is defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      Some(definedConverter),
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    val newConfigHolder = configHolder.initializeImageConverter()
    assert(newConfigHolder.getOptionToCharImageConverter.isDefined)
  }

  test(
    "setImporter() should throw an IllegalStateException if an importer is already defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    assertThrows[IllegalStateException](
      configHolder.setImporter(definedImporter))
  }

  test("setImporter() should return a new ConfigHolder with the new importer") {
    val configHolder = new ConfigHolder(
      None,
      definedFilter,
      None,
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    val newConfigHolder = configHolder.setImporter(definedImporter)
    assert(newConfigHolder.getOptionImporter.isDefined)
  }

  test("addFilter() should return a new ConfigHolder with the new filter") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    val newConfigHolder = configHolder.addFilter(definedFilter)
    assert(newConfigHolder.getOptionFilter.isDefined)
  }

  test(
    "setOptionToCharImageConverter() should throw an IllegalStateException if a converter is already defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      Some(definedConverter),
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    assertThrows[IllegalStateException](
      configHolder.setToCharImageConverter(definedConverter))
  }

  test(
    "setOptionToCharImageConverter() should return a new ConfigHolder with the new converter") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    val newConfigHolder =
      configHolder.setToCharImageConverter(definedConverter)
    assert(newConfigHolder.getOptionToCharImageConverter.isDefined)
  }

  test(
    "setConsoleExporter() should throw an IllegalStateException if a console exporter is already defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    assertThrows[IllegalStateException](
      configHolder.setConsoleExporter(definedConsoleExporter))
  }

  test(
    "setConsoleExporter() should return a new ConfigHolder with the new console exporter") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      None,
      Some(definedFileExporter)
    )

    val newConfigHolder =
      configHolder.setConsoleExporter(definedConsoleExporter)
    assert(newConfigHolder.getOptionConsoleExporter.isDefined)
  }

  test(
    "setFileExporter() should throw an IllegalStateException if a file exporter is already defined") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      Some(definedConsoleExporter),
      Some(definedFileExporter)
    )

    assertThrows[IllegalStateException](
      configHolder.setFileExporter(definedFileExporter))
  }

  test(
    "setFileExporter() should return a new ConfigHolder with the new file exporter") {
    val configHolder = new ConfigHolder(
      Some(definedImporter),
      definedFilter,
      None,
      Some(definedConsoleExporter),
      None
    )

    val newConfigHolder =
      configHolder.setFileExporter(definedFileExporter)
    assert(newConfigHolder.getOptionFileExporter.isDefined)
  }
}
