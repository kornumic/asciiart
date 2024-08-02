package ascii.filters.rasterFilter.bwFilter.specific

import ascii.filter.rasterFilter.bwFilter.specific.BrightnessBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel
import org.scalatest.FunSuite

class BrightnessBWFilterTest extends FunSuite {
  val image1 = new RasterImage(
    Seq(
      Seq(BWPixel(2), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(255), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(1))
    ))

  val imageBlack = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0))
    ))

  val imageOneBright = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(250), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0))
    ))

  test("BrightnessBWFilter - maximum brightness") {
    val filter = new BrightnessBWFilter(255)
    val newImage = filter.apply(image1)
    for {
      i <- 0 until 3
      j <- 0 until 3
    } assert(newImage.getPixels(i)(j).bwValue == 255)
  }

  test("BrightnessBWFilter - minimum brightness") {
    val filter = new BrightnessBWFilter(-255)
    val newImage = filter.apply(image1)
    for {
      i <- 0 until 3
      j <- 0 until 3
    } assert(newImage.getPixels(i)(j).bwValue == 0)
  }

  test("BrightnessBWFilter - slightly increase brightness") {
    val filter = new BrightnessBWFilter(5)
    val newImage = filter.apply(imageBlack)
    for {
      i <- 0 until 3
      j <- 0 until 3
    } assert(newImage.getPixels(i)(j).bwValue == 5)
  }

  test("BrightnessBWFilter - one bright pixel") {
    val filter = new BrightnessBWFilter(10)
    val newImage = filter.apply(imageOneBright)
    assert(newImage.getPixels(0)(0).bwValue == 10)
    assert(newImage.getPixels(1)(1).bwValue == 255)
  }
}
