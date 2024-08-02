package ascii.ui.handlers.arguments

import transformationTable.CustomTransformationTable
import transformationTable.linear.{BourkeLTT, ShortBourkeLTT}
import ascii.converter.imageConverter.BWtoAsciiRasterConverter
import ascii.converter.pixelConverter.BWtoCharPixelConverter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the table argument.
 */
class TableHandler extends ArgumentHandler("table", 1) {

  /**
   * Adds a RasterImage converter with a chosen transformation table to the configuration.
   * @param data the data to process
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val config = data.configHolder
    val table = data.argument.parameters.head.trim
    val transformationTable = table match {
      case "bourke"       => new BourkeLTT
      case "bourke-short" => new ShortBourkeLTT
      case "non-linear"   => new CustomTransformationTable
      case name =>
        throw new IllegalArgumentException(
          s"Unknown transformation table name: $name")
    }
    if (config.getOptionToCharImageConverter.isDefined)
      throw new IllegalStateException(
        "Transformation table defined twice, please provide only one transformation table")
    val pixelConverter = new BWtoCharPixelConverter(transformationTable)
    val toCharImageConverter = new BWtoAsciiRasterConverter(pixelConverter)
    config.setToCharImageConverter(toCharImageConverter)
  }
}
