package ascii.ui.exceptions

case class InvalidNumberOfParametersException(message: String)
    extends Exception {
  override def getMessage: String = message
}
