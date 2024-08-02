package exporter.stringExporter

import exporter.stringExporter.StreamStringExporter
import org.mockito.ArgumentMatchers.any
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import java.io.{ByteArrayOutputStream, IOException, OutputStream}

class StreamStringExporterTest
    extends FunSuite
    with MockitoSugar
    with BeforeAndAfterAll {

  test("StreamStringExporterTest - export string to stream") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamStringExporter(stream)

    exporter.export("Hello, world!")
    val text = stream.toString("UTF-8")
    assert(text == "Hello, world!")
  }

  test(
    "StreamStringExporterTest - throw exception when exporting to closed stream") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamStringExporter(stream)
    exporter.close()

    val testString = "This should throw an exception"
    assertThrows[Exception] {
      exporter.export(testString)
    }
  }

  test("StreamStringExporterTest - close stream") {
    val stream = mock[OutputStream]
    val exporter = new StreamStringExporter(stream)
    exporter.close()

    verify(stream).close()
  }

  test("StreamStringExporterTest - close stream only once") {
    val stream = mock[OutputStream]
    val exporter = new StreamStringExporter(stream)
    exporter.close()
    exporter.close()

    verify(stream, times(1)).close()
  }

  test("Test message overwriting when OutputStream throw error") {
    val stream = mock[OutputStream]
    when(stream.write(any[Array[Byte]]())).thenThrow(new IOException("test\n"))
    val exporter = new StreamStringExporter(stream)
    try exporter.export("Hello, world!")
    catch {
      case e: IOException =>
        assert(e.getMessage == "Error writing to the stream.\n")
      case e: Exception => fail()
    }
  }
}
