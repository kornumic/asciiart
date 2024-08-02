package filter

/**
 * Represents a filter of data.
 * @tparam I The type of the filtered data
 */
trait Filter[I] {

  /**
   * Filters data
   * @param source The source data
   * @return Filtered data
   */
  def apply(source: I): I
}
