package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import ascii.filter.rasterFilter.bwFilter.specific.ScaleBWFilter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class ScaleHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument 4") {
    val scaleArgumentHandler: ArgumentHandler =
      new ScaleHandler()

    val argument = Argument("scale", Seq("4"))
    val expectedConfigHolder = new ConfigHolder(
      None,
      filter = new MixedBWFilter(Seq(new ScaleBWFilter(4))),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.addFilter(any[BWFilter])).thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = scaleArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
    verify(configHolder, times(1)).addFilter(any[BWFilter])
  }

  test("processArgument() - handles valid argument 0.25") {
    val scaleArgumentHandler: ArgumentHandler =
      new ScaleHandler()

    val argument = Argument("scale", Seq("0.25"))
    val expectedConfigHolder = new ConfigHolder(
      None,
      filter = new MixedBWFilter(Seq(new ScaleBWFilter(0.25))),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.addFilter(any[BWFilter])).thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = scaleArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
    verify(configHolder, times(1)).addFilter(any[BWFilter])
  }

  test("processArgument() - handles non-integer argument") {
    val scaleArgumentHandler: ArgumentHandler =
      new ScaleHandler()

    val argument = Argument("scale", Seq("invalid"))
    val configHolder = mock[ConfigHolder]

    val chainData = ChainData(argument, configHolder)
    assertThrows[IllegalArgumentException] {
      scaleArgumentHandler.handle(chainData)
    }
  }
}
