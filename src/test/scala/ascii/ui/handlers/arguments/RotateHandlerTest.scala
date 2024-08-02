package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import ascii.filter.rasterFilter.bwFilter.specific.RotationBWFilter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class RotateHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument positive") {
    val rotateArgumentHandler: ArgumentHandler =
      new RotateHandler()

    val argument = Argument("rotate", Seq("+90"))
    val expectedConfigHolder = new ConfigHolder(
      None,
      filter = new MixedBWFilter(Seq(new RotationBWFilter(90))),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.addFilter(any[BWFilter])).thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = rotateArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
    verify(configHolder, times(1)).addFilter(any[BWFilter])
  }

  test("processArgument() - handles valid argument negative") {
    val rotateArgumentHandler: ArgumentHandler =
      new RotateHandler()

    val argument = Argument("rotate", Seq("-90"))
    val expectedConfigHolder = new ConfigHolder(
      None,
      filter = new MixedBWFilter(Seq(new RotationBWFilter(-90))),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.addFilter(any[BWFilter])).thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = rotateArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
    verify(configHolder, times(1)).addFilter(any[BWFilter])
  }

  test("processArgument() - handles non-integer argument") {
    val rotateArgumentHandler: ArgumentHandler =
      new RotateHandler()

    val argument = Argument("rotate", Seq("invalid"))
    val configHolder = mock[ConfigHolder]

    val chainData = ChainData(argument, configHolder)
    assertThrows[IllegalArgumentException] {
      rotateArgumentHandler.handle(chainData)
    }
  }
}
