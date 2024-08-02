package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.specific.InvertBWFilter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the invert argument.
 */
class InvertHandler extends ArgumentHandler("invert", 0) {

  /**
   * Adds an invert filter to the configuration.
   * @param data the data to process
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val filter = new InvertBWFilter()
    val config = data.configHolder
    config.addFilter(filter)
  }
}
