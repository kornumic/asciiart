package transformationTable.linear

import transformationTable.linear.BourkeLTT
import org.scalatest.FunSuite

class BourkeLTTTest extends FunSuite {
  test("BourkeLTT - check valid table") {
    val bourkeLTT = new BourkeLTT
    // check first value
    assert(bourkeLTT.transform(0) == '$')
    // check middle values that are ...n|x... to test that the table distributed evenly
    assert(bourkeLTT.transform(127) == 'n')
    assert(bourkeLTT.transform(128) == 'x')
    // check last value
    assert(bourkeLTT.transform(255) == ' ')
  }
}
