package actor

import org.junit.Test
import akka.actor._
import akka.pattern.ask
import java.util.concurrent.{TimeUnit, Executors, ExecutorService}
import scala.concurrent.duration.Duration
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Promise, ExecutionContext, Future, Await}
import akka.util.Timeout
import scala.util.Random

/**
 * User: Rogerio
 * Date: 7/7/13
 * Time: 12:38 PM
 */
class AkkaActorTest {

  @Test def sendTest() {

    val service: ExecutorService = Executors.newFixedThreadPool(10)
    val system = ActorSystem("MySystem")

    val twoActor = system.actorOf(Props(new TwoActor(0)), name = "oneActor")

    for (i <- 1 to 10) {
      service.execute(new Runnable() {
        def run() {
          println("Sent to " + i)

          twoActor ! "Hello " + i
        }
      })
    }

    Thread.sleep(12000)

    system.shutdown

    println("OK")

  }

  @Test def sendMasterTest() {

    val system = ActorSystem("MySystem")
    var min = Long.MaxValue

    implicit val timeout = Timeout(120, TimeUnit.SECONDS)

    val master = system.actorOf(Props(new MasterActor(10000000, 100)), name = "masterActor")

    for (i <- 1 to 100) {

      val start = System.currentTimeMillis

      val future = master ? "start"

      val total = Await.result(future, timeout.duration).asInstanceOf[Int]

      val end = System.currentTimeMillis() - start

      min = Math.min(min, end)

      println(s"Average: $total - time: ${end} ms - Min: $min")

    }

    system.stop(master)

    Thread.sleep(2000)

    system.shutdown

    println("OK")

  }

  @Test def averageTest() {

    var min = Long.MaxValue

    for (i <- 1 to 100) {
      val start = System.currentTimeMillis
      var total = 0
//    var result = 0

      (1 to 10000000).foreach(i => {
        total += new Random().nextInt(1000)

//      result = total / i
      })

      println(total)

      val end = System.currentTimeMillis() - start
      min = Math.min(min, end)

      println(s"Average: ${total / 10000000} - time: ${end} ms - Min: $min")
    }
  }

}

class MasterActor(var elements: Int = 1, var numWorkers: Int = 10) extends Actor {

  val workers = ArrayBuffer[ActorRef]()
  var count = 0
  var total = 0
  var origin: ActorRef = _

  {
    for (i <- 1 to numWorkers)
//      workers += context.actorOf(Props[WorkerActor].withDispatcher("my-thread-pool-dispatcher"), name = "workerActor" + i)
      workers += context.actorOf(Props[WorkerActor], name = "workerActor" + i)
  }

  def receive: Receive = {
    case "start" =>
      origin = context.sender
      count = 0
      total = 0

//      val service: ExecutorService = Executors.newFixedThreadPool(500)
//      val random = new Random()
//      var index = 0
      val range = elements / numWorkers

      for (i <- 0 until numWorkers) {
//        val worker = context.actorOf(Props[WorkerActor], name = "workerActor" + i)

//        workers += worker

//        println(s"${(i * range) + 1} ${((i + 1) * range)}")

        workers(i) ! ((i * range) + 1, ((i + 1) * range))
      }

//      for (i <- 1 to elements) {


//        workers(index) ! i

//        index += 1
//      }

    case x: Int =>

      count += 1
      total += x

      if (count == workers.length) {
//        println("Sending Future")

        println(total)

        origin ! total / elements
//        sender ! Future.successful[Int](total)
//        sender ! Promise.successful(total)
      }

  }

  override def postStop() = {
    workers.foreach(context.stop(_))

//    println(total)
  }
}

class WorkerActor extends Actor {
  def receive: Receive = {
    case (start: Int, end: Int) =>

      try {
//        Thread.sleep(1000)

        var total = 0

        for (i <- start to end)
          total += new Random().nextInt(1000)

        sender ! total

//        println("Sent to master: " + num)
      } catch {
        case e: Exception =>
          sender ! akka.actor.Status.Failure(e)
      }

  }
}

class TwoActor(var count: Int = 0) extends Actor {

//  val instance = context.actorOf(Props(new TwoActor(10)), name = "oneActor")
  // To set an initial delay
  context.setReceiveTimeout(Duration(5000, "milliseconds"))

  override def preStart() = {
    println("PreStart")
  }

  def receive = {
    case msg: String =>
      count += 1
      println(msg + " - " + count)

      Thread.sleep(1000)
    case ReceiveTimeout =>
      throw new RuntimeException("Time out")
  }
}
