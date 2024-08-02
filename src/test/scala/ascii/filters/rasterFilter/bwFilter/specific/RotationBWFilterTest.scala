package ascii.filters.rasterFilter.bwFilter.specific

import ascii.filter.rasterFilter.bwFilter.specific.RotationBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel
import org.scalatest.FunSuite

class RotationBWFilterTest extends FunSuite {

  val imageBlack4x3 = new RasterImage(
    Seq(
      Seq(BWPixel(0), BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0), BWPixel(0))
    ))

  val imageCorners4x3 = new RasterImage(
    Seq(
      Seq(BWPixel(1), BWPixel(0), BWPixel(0), BWPixel(2)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(3), BWPixel(0), BWPixel(0), BWPixel(4))
    ))

  val imageCorners3x4 = new RasterImage(
    Seq(
      Seq(BWPixel(3), BWPixel(0), BWPixel(1)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(4), BWPixel(0), BWPixel(2))
    ))

  val imageCorners4x3upsideDown = new RasterImage(
    Seq(
      Seq(BWPixel(4), BWPixel(0), BWPixel(0), BWPixel(3)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(2), BWPixel(0), BWPixel(0), BWPixel(1))
    ))

  val imageCorners3x4upsideDown = new RasterImage(
    Seq(
      Seq(BWPixel(2), BWPixel(0), BWPixel(4)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0), BWPixel(0)),
      Seq(BWPixel(1), BWPixel(0), BWPixel(3))
    ))

  test("Rotation - check dimension +90") {
    val rotatedImage =
      new RotationBWFilter(90).apply(imageBlack4x3)

    assert(rotatedImage.getWidth == 3)
    assert(rotatedImage.getHeight == 4)
  }

  test("Rotation - check dimension +180") {
    val rotatedImage =
      new RotationBWFilter(180).apply(imageBlack4x3)

    assert(rotatedImage.getWidth == 4)
    assert(rotatedImage.getHeight == 3)
  }

  test("Rotation - check dimension -90") {
    val rotatedImage =
      new RotationBWFilter(-90).apply(imageBlack4x3)

    assert(rotatedImage.getWidth == 3)
    assert(rotatedImage.getHeight == 4)
  }

  test("Rotation - check dimension -450") {
    val rotatedImage =
      new RotationBWFilter(-450).apply(imageBlack4x3)

    assert(rotatedImage.getWidth == 3)
    assert(rotatedImage.getHeight == 4)
  }

  test("Rotation - not mod 90 = 0") {

    intercept[IllegalArgumentException] {
      new RotationBWFilter(78).apply(imageBlack4x3)
    }
  }

  test("Rotation - negative not mod 90 = 0") {

    intercept[IllegalArgumentException] {
      new RotationBWFilter(-69).apply(imageBlack4x3)
    }
  }

  test("Rotation - rotate pixels +90") {

    val rotatedImage =
      new RotationBWFilter(90).apply(imageCorners4x3)

    val referencePixels = imageCorners3x4.getPixels
    assert(rotatedImage.getPixels == referencePixels)
  }

  test("Rotation - rotate pixels +180") {
    val rotatedImage =
      new RotationBWFilter(180).apply(imageCorners4x3)
    val rotatedPixels = imageCorners4x3upsideDown.getPixels
    assert(rotatedImage.getPixels == rotatedPixels)
  }

  test("Rotation - rotate pixels +270") {
    val rotatedImage =
      new RotationBWFilter(270).apply(imageCorners4x3)

    val rotatedPixels = imageCorners3x4upsideDown.getPixels

    assert(rotatedImage.getPixels == rotatedPixels)
  }

  test("Rotation - rotate pixels +360") {
    val rotatedImage =
      new RotationBWFilter(360).apply(imageCorners4x3)

    val rotatedPixels = imageCorners4x3.getPixels

    assert(rotatedImage.getPixels == rotatedPixels)
  }

  test("Rotation - rotate pixels +450") {
    val rotatedImage =
      new RotationBWFilter(450).apply(imageCorners4x3)

    val rotatedPixels = imageCorners3x4.getPixels

    assert(rotatedImage.getPixels == rotatedPixels)
  }

  test("Rotation - compare +270 to -90") {
    val rotatedImage270 =
      new RotationBWFilter(270).apply(imageCorners4x3)
    val rotatedImageMinus90 =
      new RotationBWFilter(270).apply(imageCorners4x3)

    assert(rotatedImage270.getPixels == rotatedImageMinus90.getPixels)
  }
}
