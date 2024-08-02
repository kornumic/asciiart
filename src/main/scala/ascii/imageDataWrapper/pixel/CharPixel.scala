package ascii.imageDataWrapper.pixel

/**
 * A data structure that represents a single Char pixel (an ASCII character).
 */
case class CharPixel(charValue: Char) extends Pixel {
  require(charValue.toInt <= 127, "Char value must be an ASCII character")
  override def toString: String = charValue.toString
}
