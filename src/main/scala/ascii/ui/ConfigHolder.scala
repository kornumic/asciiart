package ascii.ui

import exporter.stringExporter.{ConsoleStringExporter, FileStringExporter}
import importer.Importer
import transformationTable.linear.BourkeLTT
import ascii.converter.imageConverter.{
  BWtoAsciiRasterConverter,
  RasterConverter
}
import ascii.converter.pixelConverter.BWtoCharPixelConverter
import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.{BWPixel, CharPixel, RGBPixel}

/**
 * Class that holds the configuration of the program.
 * @param optionImporter the importer to use
 * @param filter the filter to use
 * @param optionToCharImageConverter the converter to use
 * @param optionConsoleExporter the console exporter to use
 * @param optionFileExporter the file exporter to use
 */
class ConfigHolder(
  optionImporter: Option[Importer[RasterImage[RGBPixel]]], // imports RGB Image
  filter: MixedBWFilter = new MixedBWFilter(Seq()), // filters BW Image
  optionToCharImageConverter: Option[RasterConverter[BWPixel, CharPixel]], // converts to Char Image
  optionConsoleExporter: Option[ConsoleStringExporter] = None, // exports Ascii (String) Image
  optionFileExporter: Option[FileStringExporter] = None // exports Ascii (String) Image
) {

  /**
   * Validates the configuration.
   * @throws IllegalArgumentException if the configuration is invalid, message contains the reason
   */
  def validate(): Unit = {
    if (optionImporter.isEmpty)
      throw new IllegalArgumentException(
        "Image source not defined, please provide an image source.\n")
    if (optionToCharImageConverter.isEmpty)
      throw new IllegalArgumentException(
        "Transformation table not defined, please provide a transformation table.\n")
    if (optionConsoleExporter.isEmpty && optionFileExporter.isEmpty)
      throw new IllegalArgumentException(
        "No output defined, please provide an output.\n")
  }

  /**
   * Initializes the image converter if it is not already initialized.
   * @return the modified configuration with the initialized image converter
   */
  def initializeImageConverter(): ConfigHolder =
    if (optionToCharImageConverter.isEmpty)
      new ConfigHolder(
        optionImporter,
        filter,
        Some(
          new BWtoAsciiRasterConverter(
            new BWtoCharPixelConverter(new BourkeLTT()))),
        optionConsoleExporter,
        optionFileExporter)
    else this

  /**
   * Returns the importer.
   * @return the importer
   */
  def getOptionImporter: Option[Importer[RasterImage[RGBPixel]]] =
    optionImporter

  /**
   * Sets the importer. If the importer is already defined, throws an exception.
   * @param newImporter the importer to set
   * @throws IllegalStateException if the importer is already defined
   * @return new modified configuration
   */
  def setImporter(
    newImporter: Importer[RasterImage[RGBPixel]]): ConfigHolder = {
    if (optionImporter.isDefined)
      throw new IllegalStateException(
        "Image source defined twice, please provide only one image source")
    new ConfigHolder(
      Some(newImporter),
      filter,
      optionToCharImageConverter,
      optionConsoleExporter,
      optionFileExporter)
  }

  /**
   * Returns the filter.
   * @return the filter
   */
  def getOptionFilter: Option[MixedBWFilter] = Some(filter)

  /**
   * Adds a filter to the configuration.
   * @param filterToAdd the filter to add
   * @throws IllegalStateException if the filter is already defined
   * @return new modified configuration
   */
  def addFilter(filterToAdd: BWFilter): ConfigHolder = {
    val newFilter = filter.appended(filterToAdd)

    new ConfigHolder(
      optionImporter,
      newFilter,
      optionToCharImageConverter,
      optionConsoleExporter,
      optionFileExporter)
  }

  /**
   * Returns the image converter.
   * @return the image converter
   */
  def getOptionToCharImageConverter
    : Option[RasterConverter[BWPixel, CharPixel]] =
    optionToCharImageConverter

  /**
   * Sets the image converter. If the image converter is already defined, throws an exception.
   * @param rasterConverter the image converter to set
   * @throws IllegalStateException if the image converter is already defined
   * @return new modified configuration
   */
  def setToCharImageConverter(
    rasterConverter: RasterConverter[BWPixel, CharPixel]): ConfigHolder = {
    if (optionToCharImageConverter.isDefined)
      throw new IllegalStateException(
        "Transformation table defined twice, please provide only one transformation table")
    new ConfigHolder(
      optionImporter,
      filter,
      Some(rasterConverter),
      optionConsoleExporter,
      optionFileExporter)
  }

  /**
   * Returns the console exporter.
   * @return the console exporter
   */
  def getOptionConsoleExporter: Option[ConsoleStringExporter] =
    optionConsoleExporter

  /**
   * Sets the console exporter. If the console exporter is already defined, throws an exception.
   * @param newExporter the console exporter to set
   * @return new modified configuration
   */
  def setConsoleExporter(newExporter: ConsoleStringExporter): ConfigHolder = {
    if (optionConsoleExporter.isDefined)
      throw new IllegalStateException(
        "Console exporter defined twice, please provide only one console exporter")
    new ConfigHolder(
      optionImporter,
      filter,
      optionToCharImageConverter,
      Some(newExporter),
      optionFileExporter)
  }

  /**
   * Returns the file exporter.
   * @return the file exporter
   */
  def getOptionFileExporter: Option[FileStringExporter] = optionFileExporter

  /**
   * Sets the file exporter. If the file exporter is already defined, throws an exception.
   * @param newExporter the file exporter to set
   * @return new modified configuration
   */
  def setFileExporter(newExporter: FileStringExporter): ConfigHolder = {
    if (optionFileExporter.isDefined)
      throw new IllegalStateException(
        "File exporter defined twice, please provide only one console exporter")
    new ConfigHolder(
      optionImporter,
      filter,
      optionToCharImageConverter,
      optionConsoleExporter,
      Some(newExporter))
  }

}
