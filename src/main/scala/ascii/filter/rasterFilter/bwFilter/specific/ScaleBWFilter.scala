package ascii.filter.rasterFilter.bwFilter.specific

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel

/**
 * A filter that applies to a RasterImage of type BWPixel.
 * This filter scales the image (0.25, 1, 4)
 * @param scale the amount of scaling to apply to the image
 */
class ScaleBWFilter(val scale: Double) extends BWFilter {
  require(
    scale == 0.25 || scale == 1 || scale == 4,
    "Scale must be 0.25, 1 or 4")

  /**
   * Scales up the image by a factor of 4. The new pixels are duplicated from the original pixels.
   * @param source the BW RasterImage to which the filter is applied
   * @return
   */
  private def scaleUp(source: RasterImage[BWPixel]): RasterImage[BWPixel] =
    new RasterImage(source.getPixels.flatMap(row => {
      val scaledRow = row.flatMap(p => Seq(p, p))
      Seq(scaledRow, scaledRow)
    }))

  /**
   * Calculates the average value of the 4 pixels in the square.
   * @param pixels the pixels of the image
   * @param i the row of the top left pixel of the averaged square 2x2 pixels
   * @param j the column of the top left pixel of the averaged square 2x2 pixels
   * @return the average value of the 4 pixels in the square
   */
  private def calculateAverageValue(
    pixels: Seq[Seq[BWPixel]],
    i: Int,
    j: Int): Int = {

    val leftUp = pixels(i)(j).bwValue
    val rightUp = pixels(i)(j + 1).bwValue
    val leftDown = pixels(i + 1)(j).bwValue
    val rightDown = pixels(i + 1)(j + 1).bwValue

    (leftUp + leftDown + rightUp + rightDown) / 4
  }

  /**
   * Scales down the image by a factor of 0.25. The new pixels are the average of the original pixels.
   * @param source the BW RasterImage to which the filter is applied
   * @return
   */
  private def scaleDown(source: RasterImage[BWPixel]): RasterImage[BWPixel] = {
    val pixels = source.getPixels
    val scaledWidth = source.getWidth / 2
    val scaledHeight = source.getHeight / 2
    new RasterImage(Seq.tabulate(scaledHeight, scaledWidth) {
      case (i, j) =>
        BWPixel(calculateAverageValue(pixels, i * 2, j * 2))
    })
  }

  /**
   * Applies the filter to the given RasterImage.
   * @param source the BW RasterImage to which the filter is applied
   * @return new BW RasterImage resulting from the application of the filter
   */
  override def apply(source: RasterImage[BWPixel]): RasterImage[BWPixel] =
    scale match {
      case 0.25 => scaleDown(source)
      case 1    => source
      case 4    => scaleUp(source)
    }
}
