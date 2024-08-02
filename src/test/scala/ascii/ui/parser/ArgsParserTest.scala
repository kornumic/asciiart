package ascii.ui.parser

import org.scalatest.FunSuite
import ascii.ui.parser.ArgsParser

class ArgsParserTest extends FunSuite {
  test("ArgsParserTest - test empty args") {
    val args = Array[String]()
    intercept[IllegalArgumentException](new ArgsParser(args).parse())
  }

  test("ArgsParserTest - first argument is not a flag") {
    val args = Array[String]("i-am-not-a-flag")
    intercept[IllegalArgumentException](new ArgsParser(args).parse())
  }

  test("ArgsParserTest - test one flag") {
    val args = Array[String]("--flag")
    val expected = Seq[Argument](Argument("flag", Seq.empty))
    assert(new ArgsParser(args).parse() == expected)
  }

  test("ArgsParserTest - test one flag with one parameter") {
    val args = Array[String]("--flag", "parameter")
    val expected = Seq[Argument](Argument("flag", Seq("parameter")))
    assert(new ArgsParser(args).parse() == expected)
  }

  test("ArgsParserTest - test one flag with two parameters") {
    val args = Array[String]("--flag", "parameter1", "parameter2")
    val expected =
      Seq[Argument](Argument("flag", Seq("parameter1", "parameter2")))
    assert(new ArgsParser(args).parse() == expected)
  }

  test("ArgsParserTest - two flags without parameters") {
    val args = Array[String]("--flag1", "--flag2")
    val expected =
      Seq[Argument](Argument("flag1", Seq.empty), Argument("flag2", Seq.empty))
    assert(new ArgsParser(args).parse() == expected)
  }

  test("ArgsParserTest - flag mix no.1") {
    val args = Array[String]("--flag1", "parameter1", "--flag2", "parameter2")
    val expected = Seq[Argument](
      Argument("flag1", Seq("parameter1")),
      Argument("flag2", Seq("parameter2")))
    assert(new ArgsParser(args).parse() == expected)
  }

  test("ArgsParserTest - flag mix no.2") {
    val args =
      Array[String]("--flag1", "parameter1", "--flag2", "parameter2", "--flag3")
    val expected = Seq[Argument](
      Argument("flag1", Seq("parameter1")),
      Argument("flag2", Seq("parameter2")),
      Argument("flag3", Seq.empty))
    assert(new ArgsParser(args).parse() == expected)
  }

  test("ArgsParserTest - flag mix no.3") {
    val args =
      Array[String](
        "--flag1",
        "parameter1",
        "--flag2",
        "parameter2",
        "--flag3",
        "parameter3")
    val expected = Seq[Argument](
      Argument("flag1", Seq("parameter1")),
      Argument("flag2", Seq("parameter2")),
      Argument("flag3", Seq("parameter3")))
    assert(new ArgsParser(args).parse() == expected)
  }

  test("ArgsParserTest - only flags") {
    val args = Array[String]("--flag1", "--flag2", "--flag3")
    val expected = Seq[Argument](
      Argument("flag1", Seq.empty),
      Argument("flag2", Seq.empty),
      Argument("flag3", Seq.empty))
    assert(new ArgsParser(args).parse() == expected)
  }
}
