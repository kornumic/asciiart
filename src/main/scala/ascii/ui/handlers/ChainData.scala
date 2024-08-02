package ascii.ui.handlers

import ascii.ui.ConfigHolder
import ascii.ui.parser.Argument

/**
 * The ChainData class is used to pass data through the chain of responsibility.
 * @param argument current argument to process
 * @param configHolder the configuration to use and modify
 */
case class ChainData(argument: Argument, configHolder: ConfigHolder) {}
