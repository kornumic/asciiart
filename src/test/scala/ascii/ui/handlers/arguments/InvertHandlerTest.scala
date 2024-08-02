package ascii.ui.handlers.arguments

import ascii.filter.rasterFilter.bwFilter.BWFilter
import ascii.filter.rasterFilter.bwFilter.mixed.MixedBWFilter
import ascii.filter.rasterFilter.bwFilter.specific.InvertBWFilter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}
import ascii.ui.parser.Argument
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.FunSuite

class InvertHandlerTest extends FunSuite with MockitoSugar {
  test("processArgument() - handles valid argument") {
    val invertArgumentHandler: ArgumentHandler =
      new InvertHandler()

    val argument = Argument("invert", Seq())
    val expectedConfigHolder = new ConfigHolder(
      None,
      filter = new MixedBWFilter(Seq(new InvertBWFilter())),
      None)

    val configHolder = mock[ConfigHolder]
    when(configHolder.getOptionImporter).thenReturn(None)
    when(configHolder.addFilter(any[InvertBWFilter]))
      .thenReturn(expectedConfigHolder)

    val chainData = ChainData(argument, configHolder)
    val resultConfig = invertArgumentHandler.handle(chainData)

    assert(resultConfig.isDefined)
    assert(resultConfig.get.configHolder == expectedConfigHolder)
    verify(configHolder, times(1)).addFilter(any[BWFilter])
  }
}
