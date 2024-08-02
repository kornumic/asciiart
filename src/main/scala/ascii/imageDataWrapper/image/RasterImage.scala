package ascii.imageDataWrapper.image

import ascii.imageDataWrapper.pixel.Pixel
import ascii.imageDataWrapper.pixelMatrix.PixelMatrix

/**
 * A RasterImage is an Image that is represented as a matrix of pixels.
 *
 * @param sourcePixelMatrix the matrix of pixels that represents the image
 * @tparam P the type of the pixels of the image
 */
class RasterImage[P <: Pixel](sourcePixelMatrix: PixelMatrix[P]) extends Image {

  /**
   * The matrix of pixels that represents the image.
   */
  private val pixelMatrix = sourcePixelMatrix

  /**
   * Constructs a RasterImage from a 2d sequence of pixels.
   * @param pixelSequence 2d sequence of pixels
   */
  def this(pixelSequence: Seq[Seq[P]]) = this(new PixelMatrix[P](pixelSequence))

  /**
   * Returns the width of the image.
   * @return width of the image
   */
  def getWidth: Int = pixelMatrix.getWidth

  /**
   * Returns the height of the image.
   * @return height of the image
   */
  def getHeight: Int = pixelMatrix.getHeight

  /**
   * Returns a copy of the pixels of the images pixel matrix.
   * @return 2d sequence copy of image pixels
   */
  def getPixels: Seq[Seq[P]] =
    Seq.tabulate(getHeight, getWidth)((y, x) => pixelMatrix.getPixel(x, y))

}
