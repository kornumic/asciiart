package ascii.filter.rasterFilter.bwFilter.specific

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel

/**
 * A filter that applies to a RasterImage of type BWPixel.
 * This filter inverts the image.
 */
class InvertBWFilter extends BWFilter {

  /**
   * Applies the filter to the given RasterImage.
   * @param source the BW RasterImage to which the filter is applied
   * @return new BW RasterImage resulting from the application of the filter
   */
  override def apply(source: RasterImage[BWPixel]): RasterImage[BWPixel] = {
    val pixels = source.getPixels
    val invertedPixels =
      Seq.tabulate(source.getWidth, source.getHeight)((x, y) =>
        BWPixel(255 - pixels(x)(y).bwValue))
    new RasterImage(invertedPixels)
  }
}
