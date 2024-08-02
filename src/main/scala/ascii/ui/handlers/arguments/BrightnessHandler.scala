package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.specific.BrightnessBWFilter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the brightness argument.
 */
class BrightnessHandler extends ArgumentHandler("brightness", 1) {

  /**
   * Adds a brightness filter to the configuration.
   * @param data the data to process
   * @throws IllegalArgumentException if the brightness change is not a number
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder =
    try {
      val config = data.configHolder
      val brightnessChange = data.argument.parameters.head.toInt
      val brightnessBWFilter = new BrightnessBWFilter(brightnessChange)
      config.addFilter(brightnessBWFilter)
    } catch {
      case _: NumberFormatException =>
        throw new IllegalArgumentException(
          "Invalid value for brightness change.")
    }
}
