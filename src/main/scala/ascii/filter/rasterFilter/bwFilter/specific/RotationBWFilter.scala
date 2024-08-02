package ascii.filter.rasterFilter.bwFilter.specific

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel

/**
 * A filter that applies to a RasterImage of type BWPixel.
 * This filter rotates the image.
 * @param angle the angle of rotation, must be a multiple of 90
 */
class RotationBWFilter(val angle: Int) extends BWFilter {
  require(angle % 90 == 0, "Angle must be a multiple of 90")

  /**
   * Makes sure the angle is in the range [0, 360).
   */
  private def normalizedAngle: Int = {
    val a = angle % 360
    if (a < 0)
      a + 360
    else a
  }

  /**
   * Calculates the number of 90 degree turns to apply to the image.
   **/
  private def numberOfTurns: Int = normalizedAngle / 90

  /**
   * Rotates the image 90 degrees clockwise.
   */
  private def rotate90(image: RasterImage[BWPixel]): RasterImage[BWPixel] = {
    val pixels: Seq[Seq[BWPixel]] = image.getPixels
    val height = image.getHeight
    val width = image.getWidth

    val rotated: Seq[Seq[BWPixel]] =
      Seq.tabulate(width, height)((x, y) => pixels(height - 1 - y)(x))

    new RasterImage(rotated)
  }

  /**
   * Applies the filter to the given RasterImage.
   * @param source the BW RasterImage to which the filter is applied
   * @return new BW RasterImage resulting from the application of the filter
   */
  override def apply(source: RasterImage[BWPixel]): RasterImage[BWPixel] = {
    val turns = numberOfTurns
    if (turns == 0)
      new RasterImage(source.getPixels)
    else if (turns == 1)
      rotate90(source)
    else if (turns == 2)
      rotate90(rotate90(source))
    else
      rotate90(rotate90(rotate90(source)))
  }
}
