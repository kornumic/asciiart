package transformationTable.linear

import transformationTable.linear.LinearTransformationTable
import org.scalatest.FunSuite

class LinearTransformationTableTest extends FunSuite {
  test("LinearTransformationTableTest - empty table") {
    val providedTable = ""
    intercept[IllegalArgumentException](
      new LinearTransformationTable(providedTable)
    )
  }

  test("LinearTransformationTableTest - negative value") {
    val providedTable = "0"
    val linearTable = new LinearTransformationTable(providedTable)
    intercept[IllegalArgumentException](
      linearTable.transform(-1)
    )
  }

  test("LinearTransformationTableTest - too large value") {
    val providedTable = "0"
    val linearTable = new LinearTransformationTable(providedTable)
    intercept[IllegalArgumentException](
      linearTable.transform(256)
    )
  }

  test("LinearTransformationTableTest - too long table") {
    val providedTable = "00000000000000000000000000000000000000000000000000" + //50
      "00000000000000000000000000000000000000000000000000" + //100
      "00000000000000000000000000000000000000000000000000" + //150
      "00000000000000000000000000000000000000000000000000" + //200
      "00000000000000000000000000000000000000000000000000" + //250
      "00000" + // 255
      "0" //256

    intercept[IllegalArgumentException](
      new LinearTransformationTable(providedTable)
    )
  }

  test("LinearTransformationTableTest - check valid table") {
    val providedTable = "0"
    val linearTable = new LinearTransformationTable(providedTable)
    assert(linearTable.transform(0) == '0')
  }

  test(
    "LinearTransformationTableTest - check bounds of transformed values, 2 chars") {
    val providedTable = "01"
    val linearTable = new LinearTransformationTable(providedTable)
    assert(linearTable.transform(0) == '0')
    assert(linearTable.transform(127) == '0')
    assert(linearTable.transform(128) == '1')
    assert(linearTable.transform(255) == '1')
  }

  test(
    "LinearTransformationTableTest - check bounds of transformed values, 3 chars") {
    val providedTable = "012"
    val linearTable = new LinearTransformationTable(providedTable)
    assert(linearTable.transform(0) == '0')
    assert(linearTable.transform(85) == '0')
    assert(linearTable.transform(86) == '1')
    assert(linearTable.transform(170) == '1')
    assert(linearTable.transform(171) == '2')
    assert(linearTable.transform(255) == '2')
  }

}
