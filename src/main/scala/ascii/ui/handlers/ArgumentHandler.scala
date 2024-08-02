package ascii.ui.handlers

import responsibilityChain.Handler
import ascii.ui.ConfigHolder
import ascii.ui.exceptions.{
  InvalidNumberOfParametersException,
  UnknownParameterException
}
import ascii.ui.parser.Argument

/**
 * The ArgumentHandler class is used to handle arguments. It is part of the chain of responsibility.
 * @param flagName the name of the argument
 * @param requiredParameters the number of parameters required for the argument
 */
class ArgumentHandler(
  private val flagName: String = "argument",
  private val requiredParameters: Int = 0)
    extends Handler[ChainData] {
  private var nextHandler: Option[Handler[ChainData]] = None

  /**
   * Handles the argument and returns the new configuration.
   * If the argument can be processed, stops the chain and returns the new configuration.
   * If the argument cannot be processed, passes it to the next handler.
   * If there is no next handler, throws an exception.
   * @param item the chain data containing the argument and the configuration
   * @throws UnknownParameterException if the argument is unknown
   * @throws InvalidNumberOfParametersException if the number of parameters is invalid
   * @return the new configuration
   */
  override def handle(item: ChainData): Option[ChainData] = {
    val argument = item.argument
    if (checkArgument(argument)) {
      val config = processArgument(item)
      Some(ChainData(argument, config))
    } else
      nextHandler match {
        case Some(handler) => handler.handle(item)
        case None =>
          throw UnknownParameterException(argument.flag)
      }
  }

  /**
   * Sets the next handler in the chain
   * @param nextHandler the next handler
   * @return the next handler
   */
  override def setNext(nextHandler: Handler[ChainData]): Handler[ChainData] = {
    this.nextHandler = Some(nextHandler)
    nextHandler
  }

  /**
   * Processes the argument and returns the new configuration
   * @param data the chain data containing the argument and the configuration
   * @return
   */
  protected def processArgument(data: ChainData): ConfigHolder =
    data.configHolder

  /**
   * Checks if the argument is the one handled by this handler
   * @param argument the argument to check
   * @return true if the argument is the one handled by this handler, false otherwise
   */
  private def checkArgument(argument: Argument): Boolean = {
    val flag = argument.flag
    val length = argument.parameters.length
    if (flag == flagName)
      if (length == requiredParameters)
        true
      else if (length < requiredParameters)
        throw InvalidNumberOfParametersException(
          s"Too few parameters provided for argument --$flagName, expected $requiredParameters, got $length")
      else
        throw InvalidNumberOfParametersException(
          s"Too many parameters provided for argument --$flagName, expected $requiredParameters, got $length")
    else
      false
  }

}
