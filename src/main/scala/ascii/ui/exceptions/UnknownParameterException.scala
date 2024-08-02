package ascii.ui.exceptions

case class UnknownParameterException(flag: String) extends Exception {
  override def getMessage: String = s"Unknown argument: --$flag\n"
}
