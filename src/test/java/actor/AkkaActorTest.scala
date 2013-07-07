package actor

import org.junit.Test
import akka.actor.{ReceiveTimeout, Props, ActorSystem, Actor}
import java.util.concurrent.{Executors, ExecutorService}
import scala.concurrent.duration.Duration

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

}

class TwoActor(var count: Int = 0) extends Actor {

//  val instance = context.actorOf(Props(new TwoActor(10)), name = "oneActor")
  // To set an initial delay
  context.setReceiveTimeout(Duration(2000, "milliseconds"))

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
