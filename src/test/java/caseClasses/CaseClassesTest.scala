package caseClasses

import org.junit.Test

/**
 * Created by Rogerio on 1/9/14 4:04 PM.
 */
class CaseClassesTest {

  @Test def patternMatch() {

    val x: Parent = SonOne("One")

    x match {
      case SonOne(n) =>
        println(s"One: $n")
      case SonTwo(i) =>
        println(s"Two: $i")
    }

  }

}

class Parent
case class SonOne(one: String) extends Parent
case class SonTwo(two: Int) extends Parent

