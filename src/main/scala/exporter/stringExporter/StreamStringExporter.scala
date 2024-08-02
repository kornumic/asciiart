package exporter.stringExporter

import java.io.{IOException, OutputStream}

/**
 * Represents an exporter of strings to a stream.
 * @param outStream The stream to export to
 */
class StreamStringExporter(outStream: OutputStream) extends StringExporter {
  private var closed = false

  /**
   * Exports data to the stream
   * @param source The source data
   */
  private def exportToStream(source: String): Unit = {
    if (closed)
      throw new IllegalStateException("The stream is already closed")
    try {
      outStream.write(source.getBytes("UTF-8"))
      outStream.flush()
    } catch {
      case _: IOException =>
        throw new IOException("Error writing to the stream.\n")
    }
  }

  /**
   * Closes the stream
   */
  def close(): Unit = {
    if (closed)
      return

    outStream.close()
    closed = true
  }
  /**
   * Exports data to the stream
   * @param source The source data
   */
  override def `export`(source: String): Unit = exportToStream(source)
}
