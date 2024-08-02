package transformationTable

import transformationTable.CustomTransformationTable
import org.scalatest.FunSuite

class CustomTransformationTableTest extends FunSuite {

  test("CustomTransformationTable - check invalid value -1") {
    assertThrows[IllegalArgumentException] {
      new CustomTransformationTable().transform(-1)
    }
  }

  test("CustomTransformationTable - check invalid value 256") {
    assertThrows[IllegalArgumentException] {
      new CustomTransformationTable().transform(256)
    }
  }

  test("CustomTransformationTable - check all values") {
    val customTransformationTable = new CustomTransformationTable

    assert(customTransformationTable.transform(0) == ' ')
    assert(customTransformationTable.transform(5) == ' ')
    assert(customTransformationTable.transform(6) == 'A')
    assert(customTransformationTable.transform(95) == 'A')
    assert(customTransformationTable.transform(96) == 'S')
    assert(customTransformationTable.transform(97) == 'C')
    assert(customTransformationTable.transform(98) == 'I')
    assert(customTransformationTable.transform(99) == 'T')
    assert(customTransformationTable.transform(100) == 'B')
    assert(customTransformationTable.transform(101) == 'L')
    assert(customTransformationTable.transform(200) == 'L')
    assert(customTransformationTable.transform(201) == 'E')
    assert(customTransformationTable.transform(250) == 'E')
    assert(customTransformationTable.transform(251) == '.')
    assert(customTransformationTable.transform(255) == '.')
  }
}
