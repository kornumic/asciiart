package ascii.Importer.rasterImporter

import ascii.importer.rasterImporter.PNGImporter
import org.scalatest.FunSuite

import java.nio.file.Path

class PNGImporterTest extends FunSuite {
  test("PNGImporterTest - check imported dimensions") {
    val image =
      new PNGImporter("src/test/assets/globe.png").importData()
    assert(image.getWidth == 600)
    assert(image.getHeight == 600)
  }

  test("PNGImporterTest - Throw exception for empty path") {
    assertThrows[IllegalArgumentException] {
      new PNGImporter("")
    }
  }

  test("PNGImporterTest - Throw exception for path with only whitespace") {
    assertThrows[IllegalArgumentException] {
      new PNGImporter(" ")
    }
  }

  test("PNGImporterTest - path not pointing to a .png file") {
    assertThrows[IllegalArgumentException] {
      new PNGImporter("src/test/assets/globe.jpg")
    }
  }
}
