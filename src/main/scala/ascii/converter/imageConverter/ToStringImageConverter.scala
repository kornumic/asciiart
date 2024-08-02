package ascii.converter.imageConverter

import converter.Converter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.CharPixel

/**
 * A RasterConverter is an ImageConverter that converts an Ascii RasterImage to a String.
 */
class ToStringImageConverter extends Converter[RasterImage[CharPixel], String] {

  /**
   * Converts an Ascii RasterImage to a String.
   * @param source the Ascii RasterImage to be converted
   * @return the String resulting from the conversion
   */
  override def convert(source: RasterImage[CharPixel]): String =
    source.getPixels
      .map(row => row.map(pixel => pixel.charValue).mkString(""))
      .mkString("\n")
}
