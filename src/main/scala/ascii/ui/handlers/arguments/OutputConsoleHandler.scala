package ascii.ui.handlers.arguments

import exporter.stringExporter.ConsoleStringExporter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

/**
 * Handler for the output-console argument.
 */
class OutputConsoleHandler extends ArgumentHandler("output-console", 0) {

  /**
   * Adds a console output to the configuration.
   * @param data the data to process
   * @throws IllegalStateException if the console output is already defined
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val config = data.configHolder
    if (config.getOptionConsoleExporter.isDefined)
      throw new IllegalStateException(
        "Console output defined twice, please provide only one output")
    config.setConsoleExporter(new ConsoleStringExporter())
  }
}
