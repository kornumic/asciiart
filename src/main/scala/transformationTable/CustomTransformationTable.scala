package transformationTable

/**
 * Non-Linear custom Transformation Table
 * Defines non-linear way to transform one Int to Char
 */
class CustomTransformationTable extends TransformationTable {
  require(customTable.forall(value => value.to >= 0))
  require(customTable.length > 1)

  /**
   * Internal class representing one value in table
   */
  private case class CustomTableValue(ascii: Char, to: Int) {
    require(to >= 0 && to <= 255)
  }

  /**
   * Table representing transformation.
   * @return table
   */
  private def customTable = Seq(
    CustomTableValue(' ', 5),
    CustomTableValue('A', 95),
    CustomTableValue('S', 96),
    CustomTableValue('C', 97),
    CustomTableValue('I', 98),
    CustomTableValue('T', 99),
    CustomTableValue('B', 100),
    CustomTableValue('L', 200),
    CustomTableValue('E', 250),
    CustomTableValue('.', 255),
  )

  /**
   * Transformation using internal non-linear table
   * Finds smallest CustomTableValue.to that is bigger than value. Returns CustomTableValue.ascii
   * @param value The value to transform
   * @throws IllegalArgumentException if could not transform
   * @return The transformed value
   */
  override def transform(value: Int): Char = {
    if (value < 0)
      throw new IllegalArgumentException("Value cannot be negative")

    customTable.find(character => character.to >= value) match {
      case Some(character) => character.ascii
      case None            => throw new IllegalArgumentException("No character found")
    }
  }
}
