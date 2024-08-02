package ascii.converter.imageConverter

import ascii.converter.pixelConverter.{BWtoCharPixelConverter, PixelConverter}
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.{BWPixel, CharPixel}

/**
 * A RasterConverter is an ImageConverter that converts a BW RasterImage to a Ascii RasterImage.
 *
 * @param pixelConverter the PixelConverter that converts a BW pixel to a Char pixel using a transformation table
 */
class BWtoAsciiRasterConverter(
  pixelConverter: PixelConverter[BWPixel, CharPixel])
    extends RasterConverter[BWPixel, CharPixel] {
  /**
   * Converts a BW RasterImage to a Ascii RasterImage.
   * @param source the BW RasterImage to be converted
   * @return the Ascii RasterImage resulting from the conversion
   */
  override def convert(source: RasterImage[BWPixel]): RasterImage[CharPixel] =
    new RasterImage[CharPixel](
      source.getPixels.map(_.map(pixel => pixelConverter.convert(pixel))))
}
