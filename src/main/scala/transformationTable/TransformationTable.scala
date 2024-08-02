package transformationTable

/**
 * Represents a transformation table
 */
trait TransformationTable {
  /**
   * Transforms a value to another value
   * @param value The value to transform
   * @return The transformed value
   */
  def transform(value: Int): Char
}
