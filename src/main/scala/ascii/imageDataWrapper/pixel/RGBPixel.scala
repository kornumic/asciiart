package ascii.imageDataWrapper.pixel

/**
 * A data structure that represents a single RGB pixel.
 */
case class RGBPixel(r: Int, g: Int, b: Int) extends Pixel {
  require(
    0 <= r && r <= 255,
    s"Invalid value for 'r': $r. Value must be between 0 and 255.")
  require(
    0 <= g && g <= 255,
    s"Invalid value for 'g': $g. Value must be between 0 and 255.")
  require(
    0 <= b && b <= 255,
    s"Invalid value for 'b': $b. Value must be between 0 and 255.")
  override def toString: String = {
    val rHex = f"$r%02X"
    val gHex = f"$g%02X"
    val bHex = f"$b%02X"
    f"#$rHex$gHex$bHex"
  }
}
