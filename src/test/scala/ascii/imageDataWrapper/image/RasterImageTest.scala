package ascii.imageDataWrapper.image

import ascii.imageDataWrapper.pixel.BWPixel
import ascii.imageDataWrapper.pixelMatrix.PixelMatrix
import org.scalatest.FunSuite

class RasterImageTest extends FunSuite {
  val sequence2x2: Seq[Seq[BWPixel]] = Seq(
    Seq(BWPixel(0), BWPixel(1)),
    Seq(BWPixel(2), BWPixel(3))
  )

  test("Check image values") {
    val pixelMatrix = new PixelMatrix[BWPixel](sequence2x2)

    val image: RasterImage[BWPixel] = new RasterImage(pixelMatrix)
    val returnedPixels: Seq[Seq[BWPixel]] = image.getPixels
    assert(returnedPixels(0)(0) == BWPixel(0))
    assert(returnedPixels(0)(1) == BWPixel(1))
    assert(returnedPixels(1)(0) == BWPixel(2))
    assert(returnedPixels(1)(1) == BWPixel(3))
  }

  test("RasterImage - check invalid image, 0x0") {
    val pixels: Seq[Seq[BWPixel]] = Seq()
    intercept[IllegalArgumentException] {
      new RasterImage(pixels)
    }
  }

  test("RasterImage - check invalid image, 1x0") {
    val pixels: Seq[Seq[BWPixel]] = Seq(
      Seq()
    )
    intercept[IllegalArgumentException] {
      new RasterImage(pixels)
    }
  }

  test("RasterImage - check invalid image, 3x0") {
    val pixels: Seq[Seq[BWPixel]] = Seq(
      Seq(),
      Seq(),
      Seq()
    )
    intercept[IllegalArgumentException] {
      new RasterImage(pixels)
    }
  }

  test("RasterImage - different row lengths") {
    val pixels: Seq[Seq[BWPixel]] = Seq(
      Seq(BWPixel(1), BWPixel(2), BWPixel(3)),
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(1), BWPixel(2), BWPixel(3))
    )
    intercept[IllegalArgumentException] {
      new RasterImage(pixels)
    }

  }
}
