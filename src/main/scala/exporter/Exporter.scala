package exporter

/**
 * Represents an exporter of data.
 * @tparam S The type of the exported data
 */
trait Exporter[S] {

  /**
   * Exports data
   * @param source The source data
   */
  def `export`(source: S): Unit
}
