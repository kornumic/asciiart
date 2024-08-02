package ascii.importer.rasterImporter

import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.{Pixel, RGBPixel}
import importer.Importer

import java.awt.image.BufferedImage
import java.io.IOException
import java.nio.file.Path
import javax.imageio.ImageIO

/**
 * An Importer that imports a RasterImage from a file.
 *
 * @tparam P the type of the pixels of the image
 */
trait RasterImporter[P <: Pixel] extends Importer[RasterImage[P]] {

  /**
   * Imports a RGB RasterImage from a file.
   *
   * @param path the path to the file
   * @return the imported RGB RasterImage
   */
  protected def importFromPath(path: Path): RasterImage[RGBPixel] =
    try {
      val data: BufferedImage = ImageIO.read(path.toFile)
      val pixels: Seq[Seq[RGBPixel]] =
        (0 until data.getHeight).map { y =>
          (0 until data.getWidth).map { x =>
            val rgb = data.getRGB(x, y)
            val red = (rgb >> 16) & 0xFF
            val green = (rgb >> 8) & 0xFF
            val blue = rgb & 0xFF
            RGBPixel(red, green, blue)
          }
        }
      new RasterImage[RGBPixel](pixels)
    } catch {
      case e: IllegalArgumentException =>
        throw new IllegalArgumentException("Path must point to a valid file")
      case e: IOException =>
        throw new IllegalArgumentException(
          "Could not read file, please check provided path")
    } finally {}

  /**
   * Imports a RasterImage using predefined importing method for the specific importer.
   * @return
   */
  def importData(): RasterImage[P]
}
