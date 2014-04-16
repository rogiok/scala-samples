package future

import org.junit.Test
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}
import scala.concurrent.duration._

/**
 * User: Rogerio
 * Date: 1/12/13 10:38 AM
 */
class FutureAkkaTest {

  import ExecutionContext.Implicits.global
//  implicit val ec = ExecutionContext.fromExecutorService(yourExecutorServiceGoesHere)

  @Test def runOne() {

    val f1 = Future[Int] {
      10
    }

    val r = for {
      r1 <- f1.mapTo[Int]
    } yield r1

    r.value.get match {
      case Success(v) =>
        println(v)
      case Failure(e: Throwable) =>
        println(e)
    }

  }

  @Test def runTwo() {

    val f1 = Future[Int] {
      Thread.sleep(5000)

      10
    }

    f1 onComplete {
      case Success(x) =>
        println(x)
      case Failure(e: Throwable) =>
        println(e)
    }

    Thread.sleep(10000)
  }

  @Test def runThree() {

    var r = 0

    val f1 = Future[Int] {
      Thread.sleep(5000)

      10
    }

    try {
      val x = Await.result(f1, 3 seconds).asInstanceOf[Int]

      println(x)
    } catch {
      case e: Throwable =>
        println(e.getMessage)
        r = 100
    }

    println(r)

  }

  @Test def runFour() {

    var r = 0

    val f1 = Future[Int] {
      Thread.sleep(2000)

      throw new RuntimeException
//      10
    }

    val f2 = Future[Int] {
      Thread.sleep(2000)

      10
    }

    try {
      val x = Await.result(f1, 3 seconds).asInstanceOf[Int]
      val y = Await.result(f2, 3 seconds).asInstanceOf[Int]

      println(x + y)
    } catch {
      case e: Throwable =>
        e.printStackTrace()

        r = 100
    }

    println(r)

  }
}
