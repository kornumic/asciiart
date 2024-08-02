package ascii.importer.rasterImporter

import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.RGBPixel

import java.nio.file.{Path, Paths}

/**
 * An Importer that imports a RGB, *.png, RasterImage from a file.
 *
 * @param pathString relative or absolute path to the *.png file
 */
class PNGImporter(val pathString: String) extends RasterImporter[RGBPixel] {
  require(pathString.nonEmpty, "Path must not be empty")
  require(pathString.endsWith(".png"), "Path must point to a .png file")

  /**
   * Imports a RGB RasterImage from a file.
   *
   * @return the imported RGB RasterImage
   */
  override def importData(): RasterImage[RGBPixel] = {
    val path: Path = Paths.get(pathString.trim())
    importFromPath(path)
  }
}
