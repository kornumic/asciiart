package ascii.filter.rasterFilter.bwFilter

import ascii.filters.rasterFilter.RasterFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel

/**
 * A filter that applies to a RasterImage of type BWPixel.
 */
trait BWFilter extends RasterFilter[BWPixel, RasterImage[BWPixel]] {
    /**
     * Applies the filter to the given RasterImage.
     * @param source the BW RasterImage to which the filter is applied
     * @return new BW RasterImage resulting from the application of the filter
     */
  override def apply(source: RasterImage[BWPixel]): RasterImage[BWPixel]
}
