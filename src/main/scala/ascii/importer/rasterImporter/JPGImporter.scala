package ascii.importer.rasterImporter

import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.RGBPixel

import java.nio.file.{Path, Paths}

/**
 * An Importer that imports a RGB, *.jpg, RasterImage from a file.
 *
 * @param pathString relative or absolute path to the *.jpg file
 */
class JPGImporter(val pathString: String) extends RasterImporter[RGBPixel] {
  require(pathString.nonEmpty, "Path must not be empty")
  require(pathString.endsWith(".jpg"), "Path must point to a .jpg file")
  override def importData(): RasterImage[RGBPixel] = {
    val path: Path = Paths.get(pathString.trim())
    importFromPath(path)
  }
}
