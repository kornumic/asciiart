package ascii

import exporter.stringExporter.ConsoleStringExporter
import ascii.ui.exceptions.{
  InvalidNumberOfParametersException,
  UnknownParameterException
}
import ascii.ui.facade.ConversionFacade
import ascii.ui.handlers.arguments.{
  BrightnessHandler,
  CustomTabletHandler,
  ImageHandler,
  ImageRandomHandler,
  InvertHandler,
  OutputConsoleHandler,
  OutputFileHandler,
  RotateHandler,
  ScaleHandler,
  TableHandler
}
import ascii.ui.parser.ArgsParser

/**
 * Main class of the application.
 */
object Main extends App {

  /**
   * Starts the application.
   * @param parser the parser to use
   * @param conversionFacade process of conversion to use
   * @param forUIExporter the exporter to use
   */
  private def start(
    parser: ArgsParser,
    conversionFacade: ConversionFacade,
    forUIExporter: ConsoleStringExporter
  ): Unit =
    try {
      val arguments = parser.parse()

      val initialHandler = new BrightnessHandler
      initialHandler
        .setNext(new CustomTabletHandler)
        .setNext(new ImageHandler)
        .setNext(new ImageRandomHandler)
        .setNext(new InvertHandler)
        .setNext(new OutputConsoleHandler)
        .setNext(new OutputFileHandler)
        .setNext(new RotateHandler)
        .setNext(new ScaleHandler)
        .setNext(new TableHandler)

      conversionFacade.convert(
        conversionFacade.loadConfiguration(arguments, initialHandler))
    } catch {
      case e: IllegalArgumentException =>
        forUIExporter.`export`(
          Option(e.getMessage).getOrElse("Unknown error occurred\n"))
      case e: IllegalStateException =>
        forUIExporter.`export`(
          Option(e.getMessage).getOrElse("Unknown error occurred\n"))
      case e: UnknownParameterException =>
        forUIExporter.`export`(
          Option(e.getMessage).getOrElse("Unknown error occurred\n"))
      case e: InvalidNumberOfParametersException =>
        forUIExporter.`export`(
          Option(e.getMessage).getOrElse("Unknown error occurred\n"))
    }

  start(
    new ArgsParser(args),
    new ConversionFacade(),
    new ConsoleStringExporter())
}
