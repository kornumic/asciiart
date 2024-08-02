package ascii.ui.facade

import ascii.converter.imageConverter.{
  RGBtoBWRasterConverter,
  ToStringImageConverter
}
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument

/**
 * The ConversionFacade is a facade for the conversion process.
 * It is responsible for loading the configuration and converting the image.
 * @param toBWConverter the converter used to convert the image to black and white
 * @param toAsciiImageConverter the converter used to convert the image to a string
 */
class ConversionFacade(
  toBWConverter: RGBtoBWRasterConverter = new RGBtoBWRasterConverter(),
  toAsciiImageConverter: ToStringImageConverter = new ToStringImageConverter()) {

  /**
   * Loads the configuration from the arguments and returns a ConfigHolder
   * If no transformation table is provided, the default one (BourkeLTT) is used
   * @param arguments the arguments to load the configuration from
   * @param handlerChain the chain of handlers to handle the arguments
   * @throws IllegalArgumentException if no arguments are provided
   * @throws IllegalStateException if the chain data is None
   * @return the ConfigHolder containing the configuration
   */
  def loadConfiguration(
    arguments: Seq[Argument],
    handlerChain: ArgumentHandler): ConfigHolder = {
    if (arguments.isEmpty)
      throw new IllegalArgumentException("No arguments provided")
    val config = arguments.foldLeft(
      new ConfigHolder(
        optionImporter = None,
        optionToCharImageConverter = None
      ))((configHolder, argument) => {
      val chainData = handlerChain.handle(ChainData(argument, configHolder))
      chainData match {
        case Some(data) => data.configHolder
        case None       => throw new IllegalStateException("Chain data is None")
      }
    })
    val initializedConverterConfig = config.initializeImageConverter()
    initializedConverterConfig
  }

  /**
   * Converts the image using the provided configuration
   * Throws an exception if the configuration is invalid
   * @param configHolder the configuration to use
   * @throws IllegalArgumentException if the configuration is invalid
   */
  def convert(configHolder: ConfigHolder): Unit = {
    configHolder.validate()
    val image = configHolder.getOptionImporter.get.importData()
    val bwImage = toBWConverter.convert(image)
    val filteredImage = configHolder.getOptionFilter.get.apply(bwImage)

    val charImage =
      configHolder.getOptionToCharImageConverter.get.convert(filteredImage)
    val asciiImage = toAsciiImageConverter.convert(charImage)

    if (configHolder.getOptionConsoleExporter.isDefined)
      configHolder.getOptionConsoleExporter.get.`export`(asciiImage)

    if (configHolder.getOptionFileExporter.isDefined)
      configHolder.getOptionFileExporter.get.`export`(asciiImage)

  }
}
