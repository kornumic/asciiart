package transformationTable.linear

import transformationTable.TransformationTable

/**
 * Represents a linear transformation table, where the values are sorted by their brightness.
 * @param providedTable The table to use for transformation
 */
class LinearTransformationTable(providedTable: String)
    extends TransformationTable {
  require(table.length < 256, "Too many characters provided for linear table")
  require(table.nonEmpty, "Provided linear table cannot be empty")

  /**
   * The table to use for transformation
   */
  private def table: String = providedTable

  /**
   * The length of the table
   */
  private def tableLength: Int = table.length

  /**
   * Transforms a value to another value using chosen transformation table
   * @param value The value to transform
   * @throws IllegalArgumentException If the value is not between 0 and 255
   * @return The transformed value
   */
  override def transform(value: Int): Char = {
    if (value < 0 || value > 255)
      throw new IllegalArgumentException("Value must be between 0 and 255")

    val index =
      (value.toDouble * ((tableLength).toDouble / 256.0)).toInt
    table(index)
  }
}
