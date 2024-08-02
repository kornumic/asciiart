package ascii.converter.pixelConverter

import transformationTable.TransformationTable
import ascii.imageDataWrapper.pixel.{BWPixel, CharPixel}

/**
 * A PixelConverter is a Converter that converts a Pixel of type A to a Pixel of type B.
 *
 * @param transformationTable the transformation table used to convert a BW pixel to a Char pixel
 */
class BWtoCharPixelConverter(transformationTable: TransformationTable)
    extends PixelConverter[BWPixel, CharPixel] {

  /**
   * Converts a BW pixel to a Char pixel using a transformation table.
   * @param pixel the BW pixel to be converted
   * @return
   */
  override def convert(pixel: BWPixel): CharPixel =
    CharPixel(transformationTable.transform(pixel.bwValue))
}
