package actor

import scala.actors.Actor
import org.junit.Test
import java.util.concurrent.{Executors, ExecutorService}

/**
 * User: Rogerio
 * Date: 7/6/13
 * Time: 10:20 PM
 */
class ActorTest {

  @Test def send() {

    val service: ExecutorService = Executors.newFixedThreadPool(10)
    val oneActor = new OneActor

    oneActor.start()

    for (i <- (1 to 10)) {
      service.execute(new Runnable() {
        def run() {
          println("Sent to " + i)

          oneActor ! "hello" + i
        }
      })
    }

    Thread.sleep(12000)

    println("OK")

  }

}

class OneActor extends Actor {

  var count: Int = 0

  def act() {
    while (true)
      receive {
        case msg: String =>
          count += 1
          println(msg + " - " + count)

          Thread.sleep(1000)
      }
  }
}

