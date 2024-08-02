package ascii.filter.rasterFilter.bwFilter.specific

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel

/**
 * A filter that applies to a RasterImage of type BWPixel.
 * This filter changes the brightness of the image.
 * @param change the amount of brightness to add to / subtract from the image
 */
class BrightnessBWFilter(val change: Int) extends BWFilter {

  /**
   * Applies the filter to the given RasterImage.
   * Every value stays in the range [0, 255].
   * @param source the BW RasterImage to which the filter is applied
   * @return new BW RasterImage resulting from the application of the filter
   */
  override def apply(source: RasterImage[BWPixel]): RasterImage[BWPixel] = {
    val pixels = source.getPixels.map(_.map(p => {
      var newBrightness = p.bwValue + change

      if (newBrightness < 0)
        newBrightness = 0
      else if (newBrightness > 255)
        newBrightness = 255

      BWPixel(newBrightness)
    }))

    new RasterImage(pixels)
  }

}
