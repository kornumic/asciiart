package ascii.ui.handlers.arguments

import transformationTable.linear.LinearTransformationTable
import ascii.converter.imageConverter.BWtoAsciiRasterConverter
import ascii.converter.pixelConverter.BWtoCharPixelConverter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the custom-table argument.
 */
class CustomTabletHandler extends ArgumentHandler("custom-table", 1) {

  /**
   * Adds a custom RasterImage converter with a provided transformation table to the configuration.
   * @param data the data to process
   * @throws IllegalArgumentException if the brightness change is not a number
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val argument = data.argument
    val config = data.configHolder
    val tableString = argument.parameters.head
    val table = new LinearTransformationTable(tableString)

    if (config.getOptionToCharImageConverter.isDefined)
      throw new IllegalStateException(
        "Transformation table defined twice, please provide only one transformation table")
    else
      config.setToCharImageConverter(
        new BWtoAsciiRasterConverter(new BWtoCharPixelConverter(table)))

  }
}
