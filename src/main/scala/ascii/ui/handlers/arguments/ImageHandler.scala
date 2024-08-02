package ascii.ui.handlers.arguments

import ascii.importer.rasterImporter.{JPGImporter, PNGImporter}
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the image argument.
 */
class ImageHandler extends ArgumentHandler("image", 1) {

  /**
   * Adds an image importer to the configuration. The image format must be .png or .jpg.
   * Provided path can be absolute or relative.
   * @param data the data to process
   * @throws IllegalArgumentException if the image format is not supported
   * @throws IllegalStateException if the image source is already defined
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val config = data.configHolder
    if (config.getOptionImporter.isDefined)
      throw new IllegalStateException(
        "Image source defined twice, please provide only one image")

    val path = data.argument.parameters.head

    if (path.endsWith(".png"))
      config.setImporter(new PNGImporter(path))
    else if (path.endsWith(".jpg"))
      config.setImporter(new JPGImporter(path))
    else
      throw new IllegalArgumentException("Image format is not supported")
  }
}
