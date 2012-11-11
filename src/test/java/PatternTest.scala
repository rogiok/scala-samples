import org.junit.Test
import collection.mutable.Map
import collection.JavaConversions.propertiesAsScalaMap

class PatternTest {

  @Test
  def first {

    var r = ""
    val ch = "x"

    ch match {
      case "a" => r = "Letter A"
      case "b" => r = "Letter B"
      case _ => r = "Letter ?"
    }

    println(r)

    r = ch match {
      case "a" => "Letter A"
      case "b" => "Letter B"
      case _ => "Letter ?"
    }

    println(r)
  }

  @Test
  def second {

    var r: String = null
    val ch = "x"

    r = ch match {
      case "a" => "Letter A"
      case "b" => "Letter B"
      case _ if (ch == "x") => "Letter XX"
      case _ => "Letter ?"
    }

    println(r)

  }

  @Test
  def typePattern {

    var ch: Any = "10"
    var r: Any = null

    r = ch match {
      case i: Int => i * 2
      case s: String => s + "10"
      case _ => 100.0
    }

    println(r)

  }

}
