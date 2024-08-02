package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.specific.RotationBWFilter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the rotate argument.
 */
class RotateHandler extends ArgumentHandler("rotate", 1) {

  /**
   * Adds a rotation filter to the configuration.
   * @param data the data to process
   * @throws IllegalArgumentException if the rotation angle is not a number
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val config = data.configHolder
    try {
      val angle =
        if (data.argument.parameters.head.startsWith("-"))
          data.argument.parameters.head.substring(1).toInt * (-1)
        else if (data.argument.parameters.head.startsWith("+"))
          data.argument.parameters.head.substring(1).toInt
        else
          data.argument.parameters.head.toInt

      val filter = new RotationBWFilter(angle)
      config.addFilter(filter)
    } catch {
      case e: NumberFormatException =>
        throw new IllegalArgumentException("Rotation angle must be an integer.")
    }
  }
}
