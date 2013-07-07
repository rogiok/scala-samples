package actor

import org.junit.Test

import akka.actor.ActorDSL._
import akka.actor.ActorSystem
import scala.concurrent.duration.Duration

/**
 * User: Rogerio
 * Date: 7/7/13
 * Time: 12:58 PM
 */
class AkkaActorDslTest {

  @Test def sendTest() {

    implicit val system = ActorSystem("mySystem")

    val a = actor(new Act {
      whenStarting {
        println("Starting")
      }

      whenFailing {
        (cause, msg) => println(msg)
      }

      become {
        case msg: String =>
          println(msg)

          Thread.sleep(1000)
      }
    })


    a ! "Hello"

    Thread.sleep(1000)

    system.shutdown

    println("OK")

  }

}
