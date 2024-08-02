package ascii.converter.imageConverter

import converter.Converter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.Pixel

/**
 * A converter that converts a RasterImage of type From to a RasterImage of type To.
 *
 * @tparam From the type of the pixels of the source RasterImage
 * @tparam To   the type of the pixels of the target RasterImage
 */
trait RasterConverter[From <: Pixel, To <: Pixel]
    extends Converter[RasterImage[From], RasterImage[To]] {

  /**
   * Converts a RasterImage of type From to a RasterImage of type To.
   * @param source the source RasterImage
   * @return
   */
  override def convert(source: RasterImage[From]): RasterImage[To]
}
