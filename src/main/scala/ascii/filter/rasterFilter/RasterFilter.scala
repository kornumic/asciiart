package ascii.filters.rasterFilter

import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.Pixel
import filter.Filter

/**
 * A filter that applies to a RasterImage of type T.
 *
 * @tparam P the type of the pixels of the RasterImage
 * @tparam I the type of the RasterImage
 */
trait RasterFilter[P <: Pixel, I <: RasterImage[P]] extends Filter[I] {
  /**
   * Applies the filter to the given RasterImage.
   * @param source the RasterImage to which the filter is applied
   * @return new RasterImage resulting from the application of the filter
   */
  override def apply(source: I): I
}
