package ascii.filters.rasterFilter.bwFilter.specific

import ascii.filter.rasterFilter.bwFilter.specific.ScaleBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel
import org.scalatest.FunSuite

class ScaleBWFilterTest extends FunSuite {
  val imageBlack1x1 = new RasterImage(
    Seq(
      Seq(BWPixel(0))
    ))

  val image2x1 = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(1)),
    ))

  val image4x2 = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0), BWPixel(1), BWPixel(1)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(1), BWPixel(1)),
    ))

  val imageBlack2x2 = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0))
    ))

  val imageBlack3x3 = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0))
    ))

  val imageBlack2x3 = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0))
    ))

  val imageSequence2x2 = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(1)),
      Seq(BWPixel(2), BWPixel(3))
    ))

  val imageSequence4x4 = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0), BWPixel(1), BWPixel(1)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(1), BWPixel(1)),
      Seq(BWPixel(2), BWPixel(2), BWPixel(3), BWPixel(3)),
      Seq(BWPixel(2), BWPixel(2), BWPixel(3), BWPixel(3))
    ))

  // invalid scale
  test("ScaleBWFilterTest - check invalid scale 0.5") {
    val scale = 0.5
    intercept[IllegalArgumentException] {
      new ScaleBWFilter(scale)
    }
  }

  test("ScaleBWFilterTest - check invalid scale 2") {
    val scale = 2
    intercept[IllegalArgumentException] {
      new ScaleBWFilter(scale)
    }
  }

  test("ScaleBWFilterTest - check invalid scale 8") {
    val scale = 8
    intercept[IllegalArgumentException] {
      new ScaleBWFilter(scale)
    }
  }

  test("ScaleBWFilterTest - check negative scale -1") {
    val scale = -1
    intercept[IllegalArgumentException] {
      new ScaleBWFilter(scale)
    }
  }

  //valid scale
  test("ScaleBWFilterTest - check valid scale 0.25") {
    val scale = 0.25
    new ScaleBWFilter(scale).apply(imageBlack2x2)
  }

  test("ScaleBWFilterTest - check valid scale 1") {
    val scale = 1
    new ScaleBWFilter(scale).apply(imageBlack2x2)
  }

  test("ScaleBWFilterTest - check valid scale 4") {
    val scale = 4
    new ScaleBWFilter(scale).apply(imageBlack2x2)
  }

  // scaling up
  test(
    "ScaleBWFilterTest - check dimensions of scaled up image, even dimensions") {
    val scaledImage = new ScaleBWFilter(4)(imageBlack2x2)
    assert(scaledImage.getWidth == 4)
    assert(scaledImage.getHeight == 4)
  }

  test(
    "ScaleBWFilterTest - check dimensions of scaled up image, odd dimensions") {
    val scaledImage = new ScaleBWFilter(4)(imageBlack3x3)

    assert(scaledImage.getWidth == 6)
    assert(scaledImage.getHeight == 6)
  }

  test(
    "ScaleBWFilterTest - check dimensions of scaled up image, mixed dimensions") {

    val scaledImage = new ScaleBWFilter(4)(imageBlack2x3)

    assert(scaledImage.getWidth == 4)
    assert(scaledImage.getHeight == 6)
  }

  test("ScaleBWFilterTest - check scaled up image, 1x1") {
    val scaledImage = new ScaleBWFilter(4)(imageBlack1x1)
    val scaledPixels = scaledImage.getPixels
    val referencePixels = imageBlack2x2.getPixels

    for (i <- 0 until scaledImage.getHeight)
      for (j <- 0 until scaledImage.getWidth)
        assert(scaledPixels(i)(j).bwValue == referencePixels(i)(j).bwValue)
  }

  test("ScaleBWFilterTest - check scaled up image, 2x2") {
    val scaledImage = new ScaleBWFilter(4)(imageSequence2x2)
    val scaledPixels = scaledImage.getPixels
    val referencePixels = imageSequence4x4.getPixels

    for (i <- 0 until scaledImage.getHeight)
      for (j <- 0 until scaledImage.getWidth)
        assert(scaledPixels(i)(j).bwValue == referencePixels(i)(j).bwValue)
  }

  test("ScaleBWFilterTest - check scaled up image, 2x1") {
    val scaledImage = new ScaleBWFilter(4)(image2x1)

    val scaledPixels = scaledImage.getPixels
    val referencePixels = image4x2.getPixels

    for (i <- 0 until scaledImage.getHeight)
      for (j <- 0 until scaledImage.getWidth)
        assert(scaledPixels(i)(j).bwValue == referencePixels(i)(j).bwValue)
  }
  //unscaled
  test("ScaleBWFilterTest - check dimensions of unscaled") {
    val scaledImage = new ScaleBWFilter(1)(imageBlack3x3)

    assert(scaledImage.getWidth == 3)
    assert(scaledImage.getHeight == 3)
  }

  test("ScaleBWFilterTest - check contents of unscaled") {
    val scaledImage = new ScaleBWFilter(1)(imageBlack3x3)
    val scaledPixels = scaledImage.getPixels
    val referencePixels = imageBlack3x3.getPixels

    for (i <- 0 until scaledImage.getHeight)
      for (j <- 0 until scaledImage.getWidth)
        assert(scaledPixels(i)(j).bwValue == referencePixels(i)(j).bwValue)
  }

  //scaling down

  test(
    "ScaleBWFilterTest - check dimensions of scaled down image, even dimensions") {
    val scaledImage = new ScaleBWFilter(0.25)(imageBlack2x2)

    assert(scaledImage.getWidth == 1)
    assert(scaledImage.getHeight == 1)
  }

  test(
    "ScaleBWFilterTest - check dimensions of scaled down image, odd dimensions") {
    val scaledImage = new ScaleBWFilter(0.25)(imageBlack3x3)

    assert(scaledImage.getWidth == 1)
    assert(scaledImage.getHeight == 1)
  }

  test(
    "ScaleBWFilterTest - check dimensions of scaled down image, mixed dimensions") {
    val scaledImage = new ScaleBWFilter(0.25)(imageBlack2x3)

    assert(scaledImage.getWidth == 1)
    assert(scaledImage.getHeight == 1)
  }

  test("ScaleBWFilterTest - check scaled down image, 1x1") {
    intercept[IllegalArgumentException] {
      new ScaleBWFilter(0.25)(imageBlack1x1)
    }
  }

  test("ScaleBWFilterTest - check scaled down image, 2x1") {
    intercept[IllegalArgumentException] {
      new ScaleBWFilter(0.25)(image2x1)
    }
  }

}
