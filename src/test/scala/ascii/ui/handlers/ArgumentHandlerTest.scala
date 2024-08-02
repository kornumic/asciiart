package ascii.ui.handlers

import ascii.ui.ConfigHolder
import ascii.ui.exceptions.{
  InvalidNumberOfParametersException,
  UnknownParameterException
}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class ArgumentHandlerTest extends FunSuite with MockitoSugar {

  test("Test chain handling") {
    val configHolder = mock[ConfigHolder]
    val chainData = ChainData(Argument("test", Seq()), configHolder)

    val handler1 = new ArgumentHandler()
    val handler2 = new ArgumentHandler()
    val handler3 = new ArgumentHandler()
    val finalHandler = mock[ArgumentHandler]
    when(finalHandler.handle(any[ChainData])).thenReturn(Some(chainData))

    val initialHandler = handler1
    initialHandler
      .setNext(handler2)
      .setNext(handler3)
      .setNext(finalHandler)

    val optionResult = initialHandler.handle(chainData)
    assert(optionResult.isDefined)

    verify(finalHandler, times(1)).handle(any[ChainData])
  }

  test("Test chain handling - no handler could handle input") {
    val configHolder = mock[ConfigHolder]
    val chainData = ChainData(Argument("test", Seq()), configHolder)

    val handler1 = new ArgumentHandler()
    val handler2 = new ArgumentHandler()
    val handler3 = new ArgumentHandler()

    val initialHandler = handler1
    initialHandler
      .setNext(handler2)
      .setNext(handler3)

    intercept[UnknownParameterException](initialHandler.handle(chainData))
  }

  test("Checking argument") {
    val configHolder = mock[ConfigHolder]
    val chainData = ChainData(Argument("test", Seq()), configHolder)

    val handler = new ArgumentHandler("test", 0)
    val unusedHandler = mock[ArgumentHandler]
    handler.setNext(unusedHandler)

    val result = handler.handle(chainData)
    assert(result.isDefined)
    verify(unusedHandler, never).handle(any[ChainData])
  }

  test("Checking argument - too many parameters for argument") {
    val configHolder = mock[ConfigHolder]
    val chainData = ChainData(Argument("test", Seq("parameter")), configHolder)

    val handler = new ArgumentHandler("test", 0)
    val next = mock[ArgumentHandler]
    when(next.handle(any[ChainData])).thenReturn(Some(chainData))

    handler.setNext(next)

    intercept[InvalidNumberOfParametersException](
      handler.handle(chainData)
    )

    verify(next, never).handle(any[ChainData])
  }

  test("Checking argument - too few parameters for argument") {
    val configHolder = mock[ConfigHolder]
    val chainData =
      ChainData(Argument("test", Seq("testParameter")), configHolder)

    val handler = new ArgumentHandler("test", 2)
    val next = mock[ArgumentHandler]
    when(next.handle(any[ChainData])).thenReturn(Some(chainData))

    handler.setNext(next)

    intercept[InvalidNumberOfParametersException](
      handler.handle(chainData)
    )

    verify(next, never).handle(any[ChainData])
  }

}
