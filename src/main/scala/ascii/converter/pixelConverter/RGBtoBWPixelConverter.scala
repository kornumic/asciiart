package ascii.converter.pixelConverter

import ascii.imageDataWrapper.pixel.{BWPixel, RGBPixel}

/**
 * A PixelConverter is a Converter that converts a Pixel of type A to a Pixel of type B.
 *
 */
class RGBtoBWPixelConverter extends PixelConverter[RGBPixel, BWPixel] {

  /**
   * Converts a RGB pixel to a BW pixel using a luminosity method.
   * @param pixel the RGB pixel to be converted
   * @return the BW pixel resulting from the conversion
   */
  override def convert(pixel: RGBPixel): BWPixel =
    BWPixel(
      ((0.3 * pixel.r.toDouble) + (0.59 * pixel.g.toDouble) + (0.11 * pixel.b.toDouble)).round.toInt)
}
