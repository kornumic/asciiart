package ascii.filters.rasterFilter.bwFilter.specific

import ascii.filter.rasterFilter.bwFilter.specific.InvertBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel
import org.scalatest.FunSuite

class InvertBWFilterTest extends FunSuite {
  val imageBlack = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0))
    ))

  val imageWhite = new RasterImage(
    Seq(
      Seq(BWPixel(255), BWPixel(255), BWPixel(255)),
      Seq(BWPixel(255), BWPixel(255), BWPixel(255)),
      Seq(BWPixel(255), BWPixel(255), BWPixel(255))
    ))
  val imageSinglePixel = new RasterImage(
    Seq(
      Seq(BWPixel(0))
    ))

  test("InvertBWFilterTest - invert black picture") {
    val filter = new InvertBWFilter()
    val invertedImage = filter(imageBlack)
    for {
      i <- 0 until 3
      j <- 0 until 3
    } assert(invertedImage.getPixels(i)(j).bwValue == 255)
  }

  test("InvertBWFilterTest - invert white picture") {
    val filter = new InvertBWFilter()
    val invertedImage = filter(imageWhite)
    for {
      i <- 0 until 3
      j <- 0 until 3
    } assert(invertedImage.getPixels(i)(j).bwValue == 0)
  }

  test("Single pixel image") {
    val filter = new InvertBWFilter()
    val invertedImage = filter(imageSinglePixel)
    for {
      i <- 0 until 1
      j <- 0 until 1
    } assert(invertedImage.getPixels(i)(j).bwValue == 255)
  }
}
