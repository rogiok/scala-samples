package typedactor

import akka.actor.TypedActor
import concurrent.{ExecutionContext, Promise, Future}
import util.{Failure, Success}
//import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.{Executors, ExecutorService}

/**
 * User: Rogerio
 * Date: 1/10/13 10:20 PM
 */
trait OperationManager {

  def getValue: Int
  def setOperationDontCare(id:Int, value: Int): Unit // fire and forget
  def setOperation(id: Int, value: Int): Future[Int] // Non-blocking send-request-reply
  def setOperationNowPlease(id:Int, value: Int): Option[Int] // Blocking send-request-reply
  def setOperationNow(id:Int, value: Int): Int // Blocking send-request-reply

}

class OperationManagerImpl(val monitor: OperationManagerMonitor) extends OperationManager {

  var value: Int = 0
  val service: ExecutorService = Executors.newFixedThreadPool(100)
  implicit val ec = ExecutionContext.fromExecutorService(service)

  def getValue = value

  def setOperationDontCare(id:Int, value: Int) {

    println(s"$id - Thread: ${Thread.currentThread().getName}")

    this.value += value
  }

  def setOperation(id: Int, value: Int): Future[Int] = {

    this.value += value

//    println(s"$id - [Message] Value: $value Total: ${this.value} - Thread: ${Thread.currentThread().getName}")

    if (this.value != value * id)
      throw new IllegalArgumentException(s"$id - Value: $value Total: ${this.value}")

    // val f0 = Promise.successful(this.value).future
    val f = Future[Int] {
      println(s"$id - [Execution] Value: $value Total: ${this.value} - Thread: ${Thread.currentThread().getName}")

      this.value
    }

    f.onSuccess({
      case msg => {
        monitor.setTotal(1)

//        println(s"$id - [Success] $msg - Thread: ${Thread.currentThread().getName}")
      }
    })

    f.onFailure({
      case e: IllegalArgumentException => {
        println(s"$id - Error: ${e.getMessage}")
      }
    })

    f
  }

  def setOperationNowPlease(id: Int, value: Int): Option[Int] = {

    println(s"$id - Thread: ${Thread.currentThread().getName}")

    this.value += value

    Some(this.value)
  }

  def setOperationNow(id: Int, value: Int): Int = {

    println(s"$id - Thread: ${Thread.currentThread().getName}")

    this.value += value

    this.value
  }

}


trait OperationManagerMonitor {
  def setTotal(value: Int): Unit
  def getTotal(): Int
}

class OperationManagerMonitorImpl extends OperationManagerMonitor {

  var total: Int = 0

  def setTotal(value: Int) {

    this.total += value

    println(s"Total: $total")

  }

  def getTotal(): Int = total

}


