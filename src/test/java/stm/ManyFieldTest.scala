package stm

import scala.concurrent.stm._
import org.junit.Test
import java.util.concurrent.{TimeUnit, Executors}

/**
 * User: Rogerio
 * Date: 6/8/13
 * Time: 4:09 PM
 */
class ManyFieldTest {

  @Test def writeSkewTest {

    val executor = Executors.newScheduledThreadPool(10)

    val manyField = new ManyField

    for (i <- 0 until 10)
      executor.schedule(new Runnable() {
        override def run {
          manyField.sum(1, 1, true)

          println("OK")
        }
      }, 1, TimeUnit.SECONDS)

    Thread.sleep(20000)

    executor.shutdown()

    println(s"final one: ${manyField.getOne} - two: ${manyField.getTwo}")

  }
}

class ManyField {

  val one = Ref(0)
  val two = Ref(0)

  def getOne: Int = {
    atomic {
      implicit txn => {
        one()
      }
    }
  }

  def getTwo: Int = {
    atomic {
      implicit txn => {
        two()
      }
    }
  }

  def sum(pOne: Int, pTwo: Int, phase: Boolean) {
    atomic {
      implicit txn => {
        println(s"one: $pOne - two: $pTwo")

//        if (phase)
          one.swap(one() + pOne)
//        else
          two.swap(two() + pTwo)

        Thread.sleep(1000)
      }
    }
  }

}
