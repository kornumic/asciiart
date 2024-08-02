package ascii.ui.handlers.arguments

import ascii.importer.rasterImporter.RandomImporter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the image-random argument.
 */
class ImageRandomHandler extends ArgumentHandler("image-random", 0) {

  /**
   * Adds an random image generating importer to the configuration.
   * @param data the data to process
   * @throws IllegalStateException if the image source is already defined
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val config = data.configHolder
    if (config.getOptionImporter.isDefined)
      throw new IllegalStateException(
        "Image source defined twice, please provide only one image")

    config.setImporter(new RandomImporter())
  }
}
