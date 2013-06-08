package stm

import scala.concurrent.stm._
import org.junit.Test
import java.util.concurrent.{TimeUnit, Executors}

/**
 * User: Rogerio
 * Date: 6/8/13
 * Time: 12:02 PM
 */
class SingleFieldTest {

  val replenishTimer = Executors.newScheduledThreadPool(10)

  @Test def threadWithoutRetryTest {

    val singleField = new SingleField(0)

    replenishTimer.schedule(new Runnable() {
      def run {
        singleField.add(10)
      }
    }, 1, TimeUnit.SECONDS)

    replenishTimer.schedule(new Runnable() {
      def run {
        singleField.add(20)
      }
    }, 1, TimeUnit.SECONDS)

    replenishTimer.schedule(new Runnable() {
      def run {
        singleField.addWithAlternative(20)
      }
    }, 1, TimeUnit.SECONDS)

    //    new Thread() {
//      override def run() {
//        singleField.add(10)
//      }
//    }.start()

    Thread.sleep(10000)

    replenishTimer.shutdown()

    println(s"final one = ${singleField.getValue}")

  }

  @Test def threadWithRetryTest {

    val singleField = new SingleField(0)

    replenishTimer.schedule(new Runnable() {
      def run {
        singleField.addWithAlternative(5)
      }
    }, 1, TimeUnit.SECONDS)

    Thread.sleep(2000)

    println(s"final one = ${singleField.getValue}")

  }

}

class SingleField(val initialValue: Int) {
  val one = Ref(initialValue)

  def getValue: Int = {
    atomic {
      implicit txn => {
        one()
      }
    }
  }

  def add(value: Int) {
    atomic.withRetryTimeout(1000) {
      implicit txn => {
        println(s"one = $value - ${Thread.currentThread.getName}")

        one.swap(one() + value)

        Thread.sleep(1000)
      }
    }
  }

  def addWithAlternative(value: Int) {
    atomic.withRetryTimeout(1000) {
      implicit txn => {
        println(s"one.before = ${one()} - ${Thread.currentThread.getName}")

        one.swap(one() + value)

        println(s"one.after = ${one()} - ${Thread.currentThread.getName}")

        if (one() == 5)
          retry // Search for another way to process, doing rollback and going to orAtomic

        Thread.sleep(1000)
      }
    } orAtomic {
      implicit txn => {
        println("pseudo rollback")
      }
    }
  }

}