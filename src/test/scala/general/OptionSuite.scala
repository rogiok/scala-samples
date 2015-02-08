package general

import org.scalatest.FunSuite

import scala.util.{Success, Failure, Try}

/**
 * Created by Rogerio on 2/4/15 11:22 PM.
 */
class OptionSuite extends FunSuite {

  test("Avoiding NullPointerException") {

    val x: String = null

    val x1: Option[String] = None

    Try(println(x1.getOrElse("D").substring(0, 1))) match {
      case Success(_) =>
        assert(true)
      case Failure(e) =>
        e.printStackTrace()

        assert(false)
    }

//    Try(println(x.substring(0, 1) + "10")) match {
//      case Success(_) =>
//        assert(true)
//      case Failure(e) =>
//        e.printStackTrace()
//
//        assert(false)
//    }

  }

  test("Comparing Options") {

    val x = Some(10)
    val y = Some(10)

    assertResult(true)(x == y)

  }

}
