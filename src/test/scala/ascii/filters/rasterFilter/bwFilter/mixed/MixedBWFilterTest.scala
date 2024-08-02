package ascii.filters.rasterFilter.bwFilter.mixed

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import ascii.imageDataWrapper.image.RasterImage
import ascii.imageDataWrapper.pixel.BWPixel
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class MixedBWFilterTest
    extends FunSuite
    with MockitoSugar
    with BeforeAndAfterAll {
  val image1: RasterImage[BWPixel] = new RasterImage[BWPixel](
    Seq(
      Seq(BWPixel(0), BWPixel(1)),
      Seq(BWPixel(1), BWPixel(0)),
    ),
  )

  val imageBlack: RasterImage[BWPixel] = new RasterImage[BWPixel](
    Seq(
      Seq(BWPixel(0), BWPixel(0)),
      Seq(BWPixel(0), BWPixel(0)),
    ),
  )

  val imageWhite: RasterImage[BWPixel] = new RasterImage[BWPixel](
    Seq(
      Seq(BWPixel(255), BWPixel(255)),
      Seq(BWPixel(255), BWPixel(255)),
    ),
  )
  val imageGray: RasterImage[BWPixel] = new RasterImage[BWPixel](
    Seq(
      Seq(BWPixel(127), BWPixel(127)),
      Seq(BWPixel(127), BWPixel(127)),
    ),
  )

  var identityBWFilter: BWFilter = _
  var brightnessDownBWFilter: BWFilter = _
  var brightnessUpBWFilter: BWFilter = _

  override def beforeAll(): Unit = {
    super.beforeAll()

    identityBWFilter = mock[BWFilter]
    brightnessDownBWFilter = mock[BWFilter]
    brightnessUpBWFilter = mock[BWFilter]

    // identity filter
    when(identityBWFilter.apply(image1)).thenReturn(image1)
    when(identityBWFilter.apply(imageBlack)).thenReturn(imageBlack)
    when(identityBWFilter.apply(imageWhite)).thenReturn(imageWhite)
    when(identityBWFilter.apply(imageGray)).thenReturn(imageGray)
    // brightness -255
    when(brightnessDownBWFilter.apply(image1)).thenReturn(imageBlack)
    when(brightnessDownBWFilter.apply(imageBlack)).thenReturn(imageBlack)
    when(brightnessDownBWFilter.apply(imageWhite)).thenReturn(imageBlack)
    when(brightnessDownBWFilter.apply(imageGray)).thenReturn(imageBlack)
    // brightness +255
    when(brightnessUpBWFilter.apply(image1)).thenReturn(imageWhite)
    when(brightnessUpBWFilter.apply(imageBlack)).thenReturn(imageWhite)
    when(brightnessUpBWFilter.apply(imageWhite)).thenReturn(imageWhite)
    when(brightnessUpBWFilter.apply(imageGray)).thenReturn(imageWhite)

  }

  test("MixedBWFilterTest - add new filter") {
    val filter1 = new MixedBWFilter(Seq[BWFilter](identityBWFilter))
    val filter2 = filter1.appended(brightnessUpBWFilter)
    val filter3 = filter2.appended(identityBWFilter)

    val filteredImage1 = filter1(imageGray)
    val filteredImage2 = filter2(imageGray)
    val filteredImage3 = filter3(imageGray)

    assert(filteredImage1.getPixels != filteredImage2.getPixels)
    assert(filteredImage2.getPixels == filteredImage3.getPixels)
  }

  test("MixedBWFilterTest - add new mixed filter") {
    val filter1 = new MixedBWFilter(Seq[BWFilter](identityBWFilter))
    val filter2 = new MixedBWFilter(
      Seq[BWFilter](brightnessUpBWFilter, brightnessDownBWFilter))
    val finalMixFilter = filter1.appended(filter2)

    val filteredImage = finalMixFilter(imageGray)

    assert(filteredImage.getPixels == imageBlack.getPixels)
  }

  test("MixedBWFilterTest - empty filter list") {
    val filters = Seq[BWFilter]()
    val filter = new MixedBWFilter(filters)

    val filteredImage = filter(image1)

    assert(filteredImage.getWidth == image1.getWidth)
    assert(filteredImage.getHeight == image1.getHeight)
  }

  test("MixedBWFilterTest - nested mix filter") {
    val filter = new MixedBWFilter(
      Seq[BWFilter](
        new MixedBWFilter(Seq[BWFilter]()),
      ))

    val image: RasterImage[BWPixel] = new RasterImage[BWPixel](
      image1.getPixels
    )
    val filteredImage = filter(image)

    assert(filteredImage.getWidth == image.getWidth)
    assert(filteredImage.getHeight == image.getHeight)
  }

  test("MixedBWFilterTest - check pixel matrix content") {
    val filter = new MixedBWFilter(
      Seq[BWFilter](
        new MixedBWFilter(Seq[BWFilter]()),
      ))

    val filteredImage = filter(image1)

    val imagePixels = image1.getPixels
    val filteredImagePixels = filteredImage.getPixels

    for (i <- 0 until image1.getHeight)
      for (j <- 0 until image1.getWidth)
        assert(filteredImagePixels(i)(j) == imagePixels(i)(j))
  }

  test("MixedBWFilterTest - check pixel matrix content different filters") {
    val filter = new MixedBWFilter(
      Seq[BWFilter](
        new MixedBWFilter(
          Seq[BWFilter](identityBWFilter, brightnessUpBWFilter)),
        new MixedBWFilter(Seq[BWFilter](brightnessDownBWFilter)),
      ))

    val filteredImage = filter(image1)

    val filteredImagePixels = filteredImage.getPixels
    for (i <- 0 until filteredImage.getHeight)
      for (j <- 0 until filteredImage.getWidth)
        assert(filteredImagePixels(i)(j) == imageBlack.getPixels(i)(j))
  }

  test("MixedBWFilterTest - 2 mix filters that should do the same thing") {
    val filter1 = new MixedBWFilter(
      Seq[BWFilter](
        new MixedBWFilter(
          Seq[BWFilter](identityBWFilter, brightnessUpBWFilter)),
        new MixedBWFilter(Seq[BWFilter](brightnessDownBWFilter)),
      ))

    val filter2 = new MixedBWFilter(
      Seq[BWFilter](
        identityBWFilter,
        brightnessUpBWFilter,
        brightnessDownBWFilter))

    val filteredImage1 = filter1(image1)

    val filteredImage2 = filter2(image1)

    val filteredImagePixels1 = filteredImage1.getPixels
    val filteredImagePixels2 = filteredImage2.getPixels

    for (i <- 0 until filteredImage1.getHeight)
      for (j <- 0 until filteredImage1.getWidth)
        assert(filteredImagePixels1(i)(j) == filteredImagePixels2(i)(j))
  }
}
