package typedactor

import org.junit.Test
import akka.actor.{Props, TypedProps, TypedActor, ActorSystem}
import util.{Failure, Success}
import collection.mutable.ArrayBuffer
import concurrent.Future

/**
 * User: Rogerio
 * Date: 1/10/13 10:32 PM
 */
class OperationManagerTest {

  @Test def run() {

    val system = ActorSystem()

    val operationManagerMonitor: OperationManagerMonitor = TypedActor(system).typedActorOf(
      TypedProps[OperationManagerMonitorImpl](), "operationManagerMonitor"
    )

    val operationManager: OperationManager = TypedActor(system).typedActorOf(
      TypedProps(classOf[OperationManager], new OperationManagerImpl(operationManagerMonitor)),
      "operationManager"
    )

    val list = new ArrayBuffer[Future[Int]]()

    for (i <- 1 to 1000) {

      val f = operationManager.setOperation(i, 10)

      list += f
    }

    println("OK")
//    println(s"Value from manager: ${operationManager.getValue}")
    println(s"Value from manager: ${operationManagerMonitor.getTotal}")

    Thread.sleep(1000)

    TypedActor(system).stop(operationManager)

    Thread.sleep(1000)

  }

}
