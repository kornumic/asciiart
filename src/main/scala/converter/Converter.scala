package converter

/**
 * Generic converter from one type to another
 */
trait Converter[I, J] {

  /**
   * Converts from one type to another
   * @param source The source item
   * @return Converted item
   */
  def convert(source: I): J
}
