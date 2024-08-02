package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import ascii.filter.rasterFilter.bwFilter.specific.BrightnessBWFilter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class BrightnessHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument") {
    val brightnessArgumentHandler: ArgumentHandler =
      new BrightnessHandler()

    val argument = Argument("brightness", Seq("10"))
    val expectedConfigHolder = new ConfigHolder(
      None,
      filter = new MixedBWFilter(Seq(new BrightnessBWFilter(10))),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.addFilter(any[BWFilter])).thenReturn(expectedConfigHolder)

    val chainData = new ChainData(argument, configHolder)
    val resultConfig = brightnessArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
    verify(configHolder, times(1)).addFilter(any[BWFilter])
  }

  test("processArgument() - handles invalid argument") {
    val brightnessArgumentHandler: ArgumentHandler =
      new BrightnessHandler()

    val argument = Argument("brightness", Seq("invalid"))
    val configHolder = mock[ConfigHolder]

    val chainData = new ChainData(argument, configHolder)
    assertThrows[IllegalArgumentException] {
      brightnessArgumentHandler.handle(chainData)
    }
  }

}
