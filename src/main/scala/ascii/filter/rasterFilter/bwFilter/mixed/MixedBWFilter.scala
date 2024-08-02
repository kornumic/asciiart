package ascii.filter.rasterFilter.bwFilter.mixed

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel

/**
 * A filter that applies to a RasterImage of type BWPixel. This filter is a composition of other BWFilters.
 * Uses a Composite pattern to work with multiple filters as if they were one.
 * @param filters the sequence of BWFilters to apply
 */
class MixedBWFilter(filters: Seq[BWFilter]) extends BWFilter {

  /**
   * Applies the filter to the given RasterImage.
   * Internally stored provided filters are applies in the order they were given.
   * @param source the BW RasterImage to which the filter is applied
   * @return new BW RasterImage resulting from the application of the filter
   */
  override def apply(source: RasterImage[BWPixel]): RasterImage[BWPixel] =
    filters.foldLeft(source)((img, filter) => filter.apply(img))

  def appended(filter: BWFilter): MixedBWFilter =
    new MixedBWFilter(filters :+ filter)
}
