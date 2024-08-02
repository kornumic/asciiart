package ascii.importer.rasterImporter

import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.RGBPixel

import scala.util.Random

/**
 * A RasterImporter that imports a RasterImage by randomly generating a matrix
 * of RGB pixels.
 * @param seed seed for the random number generator
 */
class RandomImporter(seed: Long = Random.nextInt())
    extends RasterImporter[RGBPixel] {

  /**
   * A random number generator. The seed is set randomly by default or can be
   * set from the constructor (for testing purposes).
   */
  private val random = new Random(seed)

  /**
   * An internal data structure that represents an aspect ratio.
   * @param width width of the aspect ratio
   * @param height height of the aspect ratio
   */
  private case class AspectRatio(width: Int, height: Int) {}

  /**
   * A list of predefined aspect ratios.
   * @return
   */
  private def aspectRatios: Array[AspectRatio] = Array(
    AspectRatio(16, 9),
    AspectRatio(4, 3),
    AspectRatio(1, 1),
    AspectRatio(5, 3),
    AspectRatio(3, 2),
    AspectRatio(11, 8),
  )

  /**
   * Generates a random aspect ratio from a predefined list of aspect ratios.
   * @return
   */
  private def randomAspectRatio(): AspectRatio = {
    val index = random.nextInt(aspectRatios.length)
    val baseRatio = aspectRatios(index)
    val numerator = random.nextInt(10) + 1
    AspectRatio(baseRatio.width * numerator, baseRatio.height * numerator)
  }

  /**
   * Generates a random RGB pixel.
   * @return
   */
  private def randomRGBPixel(): RGBPixel = {
    val red = random.nextInt(256)
    val green = random.nextInt(256)
    val blue = random.nextInt(256)
    RGBPixel(red, green, blue)
  }

  /**
   * Generates a matrix of RGB pixels with random values.
   * @param height height of the matrix
   * @param width width of the matrix
   * @return
   */
  private def randomMatrix(height: Int, width: Int) =
    Seq.tabulate(height, width) {
      case (_, _) =>
        randomRGBPixel()
    }

  /**
   * Imports a RasterImage by randomly generating a matrix of RGB pixels.
   * @return
   */
  override def importData(): RasterImage[RGBPixel] = {
    val aspectRatio = randomAspectRatio()
    val pixelMatrix = randomMatrix(aspectRatio.height, aspectRatio.width)

    new RasterImage[RGBPixel](pixelMatrix)
  }
}
