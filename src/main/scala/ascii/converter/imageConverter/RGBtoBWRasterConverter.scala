package ascii.converter.imageConverter

import ascii.converter.pixelConverter.{PixelConverter, RGBtoBWPixelConverter}
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.{BWPixel, RGBPixel}

/**
 * A RasterConverter is an ImageConverter that converts a RGB RasterImage to a BW RasterImage.
 *
 * @param pixelConverter the PixelConverter that converts a RGB pixel to a BW pixel using a transformation table
 */
class RGBtoBWRasterConverter(
  pixelConverter: PixelConverter[RGBPixel, BWPixel] = new RGBtoBWPixelConverter)
    extends RasterConverter[RGBPixel, BWPixel] {

  /**
   * Converts a RGB RasterImage to a BW RasterImage.
   * @param source the source RasterImage
   *  @return
   */
  override def convert(
    source: RasterImage[RGBPixel]
  ): RasterImage[BWPixel] =
    new RasterImage[BWPixel](
      source.getPixels.map(_.map(pixel => pixelConverter.convert(pixel))))
}
