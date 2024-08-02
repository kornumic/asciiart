package ascii.Importer.rasterImporter

import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.RGBPixel
import ascii.importer.rasterImporter.RandomImporter
import org.scalatest.FunSuite

class RandomImporterTest extends FunSuite {
  val seed = 0

  test("RandomImporterTest - importData returns a RasterImage[RGBPixel]") {
    val randomImporter = new RandomImporter(seed)
    val rasterImage: RasterImage[RGBPixel] = randomImporter.importData()

    val pixelMatrix = rasterImage.getPixels
    val height = pixelMatrix.length
    val width = if (height > 0) pixelMatrix(0).length else 0

    assert(height > 0 && height <= 90)
    assert(width > 0 && width <= 160)
  }

  test(
    "RandomImporterTest - importData returns a RasterImage with valid RGBPixel values") {
    val randomImporter = new RandomImporter(seed)
    val rasterImage: RasterImage[RGBPixel] = randomImporter.importData()

    val pixelMatrix = rasterImage.getPixels

    pixelMatrix.foreach(row =>
      row.foreach(pixel => {
        assert(pixel.r >= 0 && pixel.r <= 255)
        assert(pixel.g >= 0 && pixel.g <= 255)
        assert(pixel.b >= 0 && pixel.b <= 255)
      }))
  }
}
