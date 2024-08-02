package ascii.filters.rasterFilter.bwFilter.identity

import ascii.filter.rasterFilter.bwFilter.identity.IdentityBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel
import org.scalatest.FunSuite

class IdentityBWFilterTest extends FunSuite {
  test("IdentityBWFilterTest - check dimensions 4x1") {
    val filter = new IdentityBWFilter

    val image: RasterImage[BWPixel] = new RasterImage[BWPixel](
      Seq(
        Seq(BWPixel(0), BWPixel(1)),
        Seq(BWPixel(1), BWPixel(0)),
      ),
    )
    val filteredImage = filter(image)

    assert(filteredImage.getWidth == image.getWidth)
    assert(filteredImage.getHeight == image.getHeight)
  }

  test("IdentityBWFilterTest - check pixel matrix content") {
    val filter = new IdentityBWFilter

    val image: RasterImage[BWPixel] = new RasterImage[BWPixel](
      Seq(
        Seq(BWPixel(0), BWPixel(1)),
        Seq(BWPixel(1), BWPixel(0)),
      ),
    )
    val filteredImage = filter(image)

    val imagePixels = image.getPixels
    val filteredImagePixels = filteredImage.getPixels

    for (i <- 0 until image.getHeight)
      for (j <- 0 until image.getWidth)
        assert(filteredImagePixels(i)(j) == imagePixels(i)(j))
  }
}
