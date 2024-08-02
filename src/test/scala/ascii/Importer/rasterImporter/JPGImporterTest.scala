package ascii.Importer.rasterImporter

import ascii.importer.rasterImporter.JPGImporter
import org.scalatest.FunSuite

import java.nio.file.Path

class JPGImporterTest extends FunSuite {
  test("JPGImporterTest - check imported dimensions") {
    val image =
      new JPGImporter("src/test/assets/globe.jpg").importData()
    assert(image.getWidth == 600)
    assert(image.getHeight == 600)
  }

  test("JPGImporterTest - Throw exception for empty path") {
    assertThrows[IllegalArgumentException] {
      new JPGImporter("")
    }
  }

  test("JPGImporterTest - Throw exception for path with only whitespace") {
    assertThrows[IllegalArgumentException] {
      new JPGImporter(" ")
    }
  }

  test("JPGImporterTest - path not pointing to a .jpg file") {
    assertThrows[IllegalArgumentException] {
      new JPGImporter("src/test/assets/globe.png")
    }
  }

}
