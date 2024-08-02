package exporter.stringExporter

/**
 * Represents an exporter of strings to a console
 */
class ConsoleStringExporter extends StreamStringExporter(System.out) {}
