package ascii.imageDataWrapper.pixelMatrix

import ascii.imageDataWrapper.pixel.{BWPixel, Pixel}
import org.scalatest.FunSuite

class PixelMatrixTest extends FunSuite {
  test("PixelMatrixTest - check invalid matrix, 0x0") {
    val pixels: Seq[Seq[Pixel]] = Seq()
    intercept[IllegalArgumentException] {
      new PixelMatrix[Pixel](pixels)
    }
  }

  test("PixelMatrixTest - check invalid matrix, 1x0") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq()
    )
    intercept[IllegalArgumentException] {
      new PixelMatrix[Pixel](pixels)
    }
  }

  test("PixelMatrixTest - check invalid matrix, 3x0") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(),
      Seq(),
      Seq()
    )
    intercept[IllegalArgumentException] {
      new PixelMatrix[Pixel](pixels)
    }
  }

  test("PixelMatrixTest - different row lengths") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(BWPixel(1), BWPixel(2), BWPixel(3)),
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(1), BWPixel(2), BWPixel(3)))

    intercept[IllegalArgumentException] {
      new PixelMatrix[Pixel](pixels)
    }
  }

  test("PixelMatrixTest - check valid matrix, 1x1") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(BWPixel(1))
    )
    new PixelMatrix[Pixel](pixels)
  }

  test("PixelMatrixTest - check valid matrix, 2x2") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(3), BWPixel(4))
    )
    new PixelMatrix[Pixel](pixels)
  }

  test("PixelMatrixTest - getPixel at pos") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(3), BWPixel(4))
    )
    val pixelMatrix = new PixelMatrix[Pixel](pixels)
    assert(pixelMatrix.getPixel(0, 0) == BWPixel(1))
    assert(pixelMatrix.getPixel(1, 0) == BWPixel(2))
    assert(pixelMatrix.getPixel(0, 1) == BWPixel(3))
    assert(pixelMatrix.getPixel(1, 1) == BWPixel(4))
  }

  test("PixelMatrixTest - getPixel at pos out of bounds") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(3), BWPixel(4))
    )
    val pixelMatrix = new PixelMatrix[Pixel](pixels)
    intercept[IllegalArgumentException] {
      pixelMatrix.getPixel(0, 2)
    }
    intercept[IllegalArgumentException] {
      pixelMatrix.getPixel(2, 0)
    }
    intercept[IllegalArgumentException] {
      pixelMatrix.getPixel(2, 2)
    }
  }

  test("PixelMatrixTest - getPixel at pos negative") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(3), BWPixel(4))
    )
    val pixelMatrix = new PixelMatrix[Pixel](pixels)
    intercept[IllegalArgumentException] {
      pixelMatrix.getPixel(-1, 0)
    }
    intercept[IllegalArgumentException] {
      pixelMatrix.getPixel(0, -1)
    }
    intercept[IllegalArgumentException] {
      pixelMatrix.getPixel(-1, -1)
    }
  }

  test("PixelMatrixTest - get dimensions 2x2") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(3), BWPixel(4))
    )
    val pixelMatrix = new PixelMatrix[Pixel](pixels)
    assert(pixelMatrix.getWidth == 2)
    assert(pixelMatrix.getHeight == 2)
  }

  test("PixelMatrixTest - iterate over pixels") {
    val pixels: Seq[Seq[Pixel]] = Seq(
      Seq(BWPixel(1), BWPixel(2)),
      Seq(BWPixel(3), BWPixel(4))
    )
    val pixelMatrix = new PixelMatrix[Pixel](pixels)
    val iterator = pixelMatrix.iterator
    assert(iterator.next() == BWPixel(1))
    assert(iterator.next() == BWPixel(2))
    assert(iterator.next() == BWPixel(3))
    assert(iterator.next() == BWPixel(4))
    assert(!iterator.hasNext)
  }

}
