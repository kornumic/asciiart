package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.specific.ScaleBWFilter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the scale argument.
 */
class ScaleHandler extends ArgumentHandler("scale", 1) {

  /**
   * Adds a scale filter to the configuration.
   * @param data the data to process
   * @throws IllegalArgumentException if the scale is not a number
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val config = data.configHolder
    try {
      val scale = data.argument.parameters.head.toDouble

      val filter = new ScaleBWFilter(scale)
      config.addFilter(filter)
    } catch {
      case e: NumberFormatException =>
        throw new IllegalArgumentException("Scale must be 0.25, 1 or 4.")
    }
  }
}
