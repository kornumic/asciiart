package importer

/**
 * Represents an importer of data.
 * @tparam I The type of the imported data
 */
trait Importer[I] {

  /**
   * Imports data
   * @return Imported data
   */
  def importData(): I
}
