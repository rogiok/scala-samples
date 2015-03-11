package general

import java.util.Calendar

import org.scalatest.FunSuite

import scala.collection.mutable

/**
 * Created by Rogerio on 2/24/15 8:48 PM.
 */
class PerformanceListSuite extends FunSuite {

  test("Immutable List") {

    var l = List[String]()

    val start = Calendar.getInstance().getTimeInMillis

    for (i <- 1 to 100) {
//      l = l :+ s"Hey$i"
      l = l ::: List(s"Hey$i")
    }

    val end = Calendar.getInstance().getTimeInMillis

    println(s"${end - start} ms")

  }

  test("Mutable List") {

    val l = mutable.MutableList[String]()

    val start = Calendar.getInstance().getTimeInMillis

    for (i <- 1 to 100) {
      l += s"Hey$i"
    }

    l.toList

    val end = Calendar.getInstance().getTimeInMillis

    println(s"${end - start} ms")

  }

}
