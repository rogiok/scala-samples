package future

import org.junit.Test
import concurrent.{Promise, Await, ExecutionContext, Future}
import concurrent.duration.Duration
import collection.mutable.ArrayBuffer
import ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

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

    f1 onSuccess {
      case msg => println(s"Success: $msg")
//      case e: Exception => println(s"Error: $e")
//      case _ => println("Error")
    }

    println("Begin")

    //    println(Await.result(f1, Duration(10, TimeUnit.SECONDS)).asInstanceOf[Int])
    println(Await.result(f1, Duration(10, TimeUnit.SECONDS)))
//    println(Await.result(f1, 10 second))
    println("End")

  }

  @Test def runList() {

//    import ExecutionContext.Implicits.global

    val list = new ArrayBuffer[Future[Int]]

    for (i <- 1 to 10) {
      val f = Future[Int] {
        // println(s"$i")

        i * 10
      }

      f onSuccess({
        case msg => println(s"Result: $msg from ${Thread.currentThread.getName}")
      })

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

}
