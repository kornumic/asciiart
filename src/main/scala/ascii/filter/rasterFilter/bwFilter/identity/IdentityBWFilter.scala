package ascii.filter.rasterFilter.bwFilter.identity

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel

/**
 * A filter that applies to a RasterImage of type BWPixel.
 * This filter does nothing.
 */
class IdentityBWFilter extends BWFilter {

  /**
   * Applies the filter to the given RasterImage.
   * @param source the BW RasterImage to which the filter is applied
   * @return new BW RasterImage resulting from the application of the filter
   */
  override def apply(source: RasterImage[BWPixel]): RasterImage[BWPixel] =
    source
}
