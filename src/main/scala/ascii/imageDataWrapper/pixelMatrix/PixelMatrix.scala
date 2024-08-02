package ascii.imageDataWrapper.pixelMatrix

import ascii.imageDataWrapper.pixel.Pixel

/**
 * A data wrapper structure representing a 2D matrix of pixels.
 */
class PixelMatrix[P <: Pixel](pixels: Seq[Seq[P]]) {
  require(pixels.nonEmpty, "Pixel matrix must not be empty")
  require(pixels.head.nonEmpty, "Pixel matrix must not be empty")
  require(
    pixels.forall(_.length == pixels.head.length),
    "Width must match number of columns")

  /**
   * Returns the pixel at the given coordinates.
   * @param x x coordinate
   * @param y y coordinate
   * @return pixel at the given coordinates
   */
  def getPixel(x: Int, y: Int): P = {
    if (x < 0 || x >= getWidth)
      throw new IllegalArgumentException(s"Invalid x coordinate: $x")
    if (y < 0 || y >= getHeight)
      throw new IllegalArgumentException(s"Invalid y coordinate: $y")

    pixels(y)(x)
  }

  /**
   * Returns the width of the pixel matrix.
   * @return width of the pixel matrix
   */
  def getWidth: Int = pixels.head.length

  /**
   * Returns the height of the pixel matrix.
   * @return height of the pixel matrix
   */
  def getHeight: Int = pixels.length

  /**
   * Returns an iterator over the pixels of the pixel matrix.
   * @return iterator over the pixels of the pixel matrix
   */
  def iterator: Iterator[P] = pixels.iterator.flatten
}
