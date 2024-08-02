package ascii.converter.pixelConverter

import converter.Converter
import ascii.imageDataWrapper.pixel.Pixel

/**
 * A converter that converts a Pixel of type From to a Pixel of type To.
 *
 * @tparam From the type of the source Pixel
 * @tparam To   the type of the target Pixel
 */
trait PixelConverter[From <: Pixel, To <: Pixel] extends Converter[From, To] {}
