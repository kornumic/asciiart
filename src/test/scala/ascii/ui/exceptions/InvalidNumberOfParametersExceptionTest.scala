package ascii.ui.exceptions

import org.scalatest.FunSuite

class InvalidNumberOfParametersExceptionTest extends FunSuite {

  test("testGetMessage") {
    val unknownParameterException = InvalidNumberOfParametersException("test")
    assert(unknownParameterException.getMessage == "test")
  }

  test("throw and catch") {
    try throw InvalidNumberOfParametersException("test")
    catch {
      case e: InvalidNumberOfParametersException =>
        assert(e.getMessage == "test")
    }
  }
}
