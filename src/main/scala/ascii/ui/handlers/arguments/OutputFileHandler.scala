package ascii.ui.handlers.arguments

import exporter.stringExporter.FileStringExporter
import ascii.ui.ConfigHolder
import ascii.ui.handlers.{ArgumentHandler, ChainData}

import java.io.File

/**
 * Handler for the output-file argument.
 */
class OutputFileHandler extends ArgumentHandler("output-file", 1) {

  /**
   * Adds a file output to the configuration.
   * @param data the data to process
   * @throws IllegalStateException if the file output is already defined
   * @throws IllegalArgumentException if the output file is not a .txt file
   * @return the modified configuration
   */
  override protected def processArgument(data: ChainData): ConfigHolder = {
    val config = data.configHolder
    if (config.getOptionFileExporter.isDefined)
      throw new IllegalStateException(
        "File output defined twice, please provide only one output")

    val path = data.argument.parameters.head
    if (path.isEmpty || !path.endsWith(".txt"))
      throw new IllegalArgumentException("Output file must be a .txt file")

    val file = new File(path)
    val optionDirectory = Option(file.getParentFile)
    optionDirectory match {
      case Some(directory) =>
        if (!directory.exists())
          directory.mkdirs()
      case _ =>
    }
    // check if file exists, if not create it
    if (!file.exists())
      file.createNewFile()

    val exporter = new FileStringExporter(file)
    config.setFileExporter(exporter)
  }
}
