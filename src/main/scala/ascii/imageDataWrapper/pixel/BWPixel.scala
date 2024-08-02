package ascii.imageDataWrapper.pixel

/**
 * A data structure that represents a single BW pixel.
 */
case class BWPixel(bwValue: Int) extends Pixel {
  require(bwValue >= 0 && bwValue <= 255, "BWPixel value must be between 0 and 255")
  override def toString: String = f" $bwValue%03d "
}
