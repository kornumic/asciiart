package ascii.ui.parser

/**
 * Class that parses the arguments provided by the user into usable objects.
 * @param args arguments provided by the user
 */
class ArgsParser(args: Array[String]) {

  /**
   * Parses the arguments provided by the user into usable objects.
   * Checks if the first argument is a flag and if there are any arguments provided.
   * @throws IllegalArgumentException if no arguments are provided
   *                                  or if the first argument is not a flag
   * @return a sequence of Argument objects with set flags and parameters
   */
  def parse(): Seq[Argument] = {
    if (args.isEmpty)
      throw new IllegalArgumentException("No arguments provided")
    else if (!args.head.startsWith("--"))
      throw new IllegalArgumentException("No flag provided")

    var arguments: Seq[Argument] = Seq[Argument]()
    var argument = args.head
    var parameters: Seq[String] = Seq.empty
    var rest = args.tail

    while (argument.startsWith("--")) {
      while (rest.nonEmpty && !rest.head.startsWith("--")) {
        val parameter = rest.head
        rest = rest.tail
        parameters = parameters.appended(parameter)
      }
      arguments = arguments.appended(
        Argument(argument.slice(2, argument.length), parameters))
      parameters = Seq.empty

      if (rest.nonEmpty) {
        argument = rest.head
        rest = rest.tail
      } else
        argument = "end"
    }
    arguments
  }
}
