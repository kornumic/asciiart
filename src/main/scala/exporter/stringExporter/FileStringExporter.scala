package exporter.stringExporter

import java.io.{File, FileOutputStream}

/**
 * Represents an exporter of strings to a file.
 * @param file The file to export to
 */
class FileStringExporter(file: File)
    extends StreamStringExporter(new FileOutputStream(file)) {}
