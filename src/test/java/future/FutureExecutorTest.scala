package future

import org.junit.Test
import concurrent.{Promise, Await, ExecutionContext, Future}
import concurrent.duration.Duration
import collection.mutable.ArrayBuffer
import ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit
import scala.util.Success

/**
 * User: Rogerio
 * Date: 1/12/13 10:38 AM
 */
class FutureExecutorTest {

  @Test def runOne() {

//    import ExecutionContext.Implicits.global

    val f1 = Future[Int] {
      println("OK")

      10
    }

    println("Defining Success")

    Thread.sleep(2000)

    f1 onSuccess {
      case msg => println(s"Success: $msg")
//      case e: Exception => println(s"Error: $e")
//      case _ => println("Error")
    }

    f1 onComplete( x => x match { case Success(y) => println(s"Complete: $y") } )

    println("Begin")

    Thread.sleep(2000)

    Await.ready(f1, Duration(10, TimeUnit.SECONDS))
    //    println(Await.result(f1, Duration(10, TimeUnit.SECONDS)).asInstanceOf[Int])
//    println(Await.result(f1, Duration(10, TimeUnit.SECONDS)))
//    println(Await.result(f1, 10 second))
    println("End")

  }

  @Test def runList() {

//    import ExecutionContext.Implicits.global

    println(s"Thread: ${Thread.currentThread.getName}")

    val list = new ArrayBuffer[Future[Int]]

    for (i <- 1 to 10) {
      val f = Future[Int] {
        // println(s"$i")

        i * 10
      }

      f onSuccess {
        case msg => println(s"Result: $msg from ${Thread.currentThread.getName}")
      }

      list += f
    }

//    val func = (result: Future[Int]) => {
//      println(result.value)
//      true
//    }

//    list.forall(func)

//    for (o <- list) {
//      println(o.value)
//    }

//    list foreach println

    Thread.sleep(2000)

  }

  @Test def runSuccessful() {

    val f = Promise.successful(10).future

    f onSuccess({
      case msg => println(msg)
    })

    println(Await.result(f, Duration(1, TimeUnit.SECONDS)))

  }

  @Test def runInAClosure() {

    var count = 10

    val control = Future {
      while (count > 0) {
        println(s"Count: $count")
        Thread.sleep(100)
      }
    }

    for (i <- 1 to 10) {
      val f1 = Future[Int] {
        println(s"OK: $i")

        Thread.sleep(500)

        if (i == 7)
          throw new Exception()

        10
      }

//      f1 onSuccess {
//        case msg =>
//          println(s"Complete: $i")
//          count = count - 1
//      }

      val pf = new PartialFunction[Int, Any] {
        override def isDefinedAt(x: Int): Boolean = {
          true
        }

        override def apply(v1: Int): Any = {
          println(v1)

          count = count - 1
        }
      }

      f1.onSuccess(pf)

      f1 onFailure {
        case msg =>
          count = count - 1
      }
    }

    val c = new TestOne()

    c.hello2 {
      case x: String =>
        println("Hello: " + x)

        1
    }

//    Thread.sleep(2000)

    Await.ready(control, Duration(10, TimeUnit.SECONDS))

    println("End")

  }

}

class TestOne {

  def hello(x: String) {

    println("Hello " + x)

  }

  def hello2(x: PartialFunction[String, Int]) {
    println("Result: " + x("30"))
  }
}
