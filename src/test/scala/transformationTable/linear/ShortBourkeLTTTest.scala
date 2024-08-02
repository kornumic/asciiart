package transformationTable.linear

import transformationTable.linear.ShortBourkeLTT
import org.scalatest.FunSuite

class ShortBourkeLTTTest extends FunSuite {
  test("ShortBourkeLTT - check valid table") {
    val shortBourkeLTT = new ShortBourkeLTT
    // check first value
    assert(shortBourkeLTT.transform(0) == '@')
    // check middle values that are ...+|=... to test that the table distributed evenly
    assert(shortBourkeLTT.transform(127) == '+')
    assert(shortBourkeLTT.transform(128) == '=')
    // check last value
    assert(shortBourkeLTT.transform(255) == ' ')
  }
}
