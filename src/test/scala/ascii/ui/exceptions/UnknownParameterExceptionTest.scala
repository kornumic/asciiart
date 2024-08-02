package ascii.ui.exceptions

import org.scalatest.FunSuite

class UnknownParameterExceptionTest extends FunSuite {
  test("testGetMessage") {
    val unknownParameterException = UnknownParameterException("flag")
    assert(unknownParameterException.getMessage == "Unknown argument: --flag\n")
  }

  test("throw and catch") {
    try {
      throw UnknownParameterException("flag")
    } catch {
      case e: UnknownParameterException => assert(e.getMessage == "Unknown argument: --flag\n")
    }
  }
}
