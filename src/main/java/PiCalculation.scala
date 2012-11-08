package org.sample.akka001

import akka.actor._
import akka.routing.RoundRobinRouter
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.{HttpRequest, HttpResponse, HttpHost}
import org.apache.http.conn.scheme.SchemeRegistry
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.scheme.PlainSocketFactory
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.impl.conn.{BasicClientConnectionManager, PoolingClientConnectionManager}
import org.apache.http.conn.routing.HttpRoute
import com.typesafe.config.ConfigFactory
import akka.routing.DefaultResizer
import org.apache.http.params.HttpParams
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.HttpConnectionParams
import org.apache.http.conn.{ClientConnectionManager, ManagedClientConnection, ClientConnectionRequest}
import java.util.concurrent.TimeUnit
import org.apache.http.message.BasicHttpRequest

object Pi {
/*
  def main(args: Array[String]) {
    println("Running")

    calculate(nrOfWorkers = 4 * 10, nrOfElements = 1000, nrOfMessages = 4000)

    Thread.sleep(120000)
  }

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) = {

    val config = ConfigFactory.load()
    //        val system = ActorSystem("PiSystem", config.getConfig("my-config-app").withFallback(config))
    val system = ActorSystem("PiSystem", config)

    val listener = system.actorOf(Props[Listener], name = "listener")

    val master = system.actorOf(
      Props(new Master(nrOfWorkers, nrOfMessages, nrOfElements, listener)),
      name = "master"
    )

    // Start the calculation
    master ! Calculate
  }


  sealed trait PiMessage

  case object Calculate extends PiMessage

  case class Work(start: Int, nrOfElements: Int, client: HttpClient, cm: ClientConnectionManager) extends PiMessage

  case class Result(value: Double, success: Boolean) extends PiMessage

  case class PiApproximation(pi: Double, nrOfSuccess: Int, duration: Long)

  class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef) extends Actor {

    var pi: Double = _
    var nrOfResults: Int = _
    var nrOfSuccess: Int = _
    var start: Long = System.currentTimeMillis()

    val resizer = DefaultResizer(lowerBound = 80, upperBound = 200)
    val workerRouter = context.actorOf(
      Props[Worker].withRouter(RoundRobinRouter(resizer = Some(resizer), routerDispatcher = "router")).withDispatcher("my-thread-pool-dispatcher"),
      name = "workerRouter")

    //        val resizer = DefaultResizer(60, 200)
    //        val workerRouter = context.actorOf(
    //            Props[Worker].withRouter(RoundRobinRouter(resizer = Some(resizer))),
    //            name = "workerRouter")
    //        val workerRouter = context.actorOf(
    //            Props[Worker].withRouter(RoundRobinRouter(nrOfWorkers)),
    //            name = "workerRouter")

    //        val workerRouter = context.actorOf(
    //            Props[Worker].withRouter(RoundRobinRouter(nrOfWorkers, routerDispatcher = "router")).withDispatcher("my-thread-pool-dispatcher"),
    //            name = "workerRouter")
    //        val workerRouter = context.actorOf(
    //            Props[Worker].withDispatcher("my-thread-pool-executor-2"),
    //            name = "workerRouter")
    val params: HttpParams = new BasicHttpParams()
    // Connection timeout
    HttpConnectionParams.setConnectionTimeout(params, 60000)
    // Maximum period between two packets
    HttpConnectionParams.setSoTimeout(params, 10000)

    val schemeRegistry: SchemeRegistry = new SchemeRegistry()
    schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()))
    //        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

    // Pool connection with timeToLive
    val cm: PoolingClientConnectionManager = new PoolingClientConnectionManager(schemeRegistry, 10000, TimeUnit.MILLISECONDS)
//    val cm: BasicClientConnectionManager = new BasicClientConnectionManager(schemeRegistry)
    // Increase max total connection to 200
    cm.setMaxTotal(200)
    // Increase default max connection per route to 20
    cm.setDefaultMaxPerRoute(20)
    // Increase max connections for localhost:80 to 50
    val localhost: HttpHost = new HttpHost("locahost", 80)
    cm.setMaxPerRoute(new HttpRoute(localhost), 50)

    //        val client: HttpClient = new DefaultHttpClient(cm)
    val client: HttpClient = new DefaultHttpClient(cm, params)
//    val client: HttpClient = null

    def receive = {
      case Calculate =>
        for (i <- 0 until nrOfMessages)
          workerRouter ! Work(i, nrOfElements, client, cm)
      //                    workerRouter ! Work(i * nrOfElements, nrOfElements)
      case Result(value, success) =>
        //                pi += value
        nrOfResults += 1

        if (success)
          nrOfSuccess += 1

        if (nrOfResults == nrOfMessages) {
          //                    listener ! PiApproximation(pi, duration = (System.currentTimeMillis() - start).millis)
          listener ! PiApproximation(nrOfResults, nrOfSuccess, duration = (System.currentTimeMillis() - start))

          context.stop(self)
        }
    }

  }

  class Listener extends Actor {
    def receive = {
      case PiApproximation(pi, nrOfSuccess, duration) =>
        println("TOtal: " + pi + " - Success: " + nrOfSuccess)
        println(duration)

        context.system.shutdown()
    }
  }

  class Worker extends Actor {

    def receive = {

      case Work(start, nrOfElements, client, cm1) =>
        // println(start)

        val startTime: Long = System.currentTimeMillis()

        try {
//          val schemeRegistry: SchemeRegistry = new SchemeRegistry()
//          schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()))

//          val cm: BasicClientConnectionManager = new BasicClientConnectionManager(schemeRegistry)
//          val connRequest: ClientConnectionRequest = cm.requestConnection(new HttpRoute(new HttpHost("www.amazon.com", 80)), null)
//          val conn: ManagedClientConnection = connRequest.getConnection(10, TimeUnit.SECONDS)

//          val params: HttpParams = new BasicHttpParams()
          // Connection timeout
//          HttpConnectionParams.setConnectionTimeout(params, 60000)
          // Maximum period between two packets
//          HttpConnectionParams.setSoTimeout(params, 10000)

//          conn.open(new HttpRoute(new HttpHost("www.amazon.com", 80)), null, params)

//          val request: HttpRequest = new BasicHttpRequest("GET", "/")

//          conn.sendRequestHeader(request)

//          val response: HttpResponse = conn.receiveResponseHeader()

//          conn.receiveResponseEntity(response)

          val request: HttpGet = new HttpGet("http://www.google.com")

          val response: HttpResponse = client.execute(request)
          println(self.path.name + " = " + response.getStatusLine() + " - " + (System.currentTimeMillis() - startTime) + " ms")

//          request.abort()
          request.releaseConnection();

//          conn.releaseConnection()

          sender ! Result(start, true)
          //                sender ! Result(calculatePiFor(start, nrOfElements))
        } catch {
          case e: Exception =>
            // e.printStackTrace()
            println("Error: " + e.getMessage() + " - " + (System.currentTimeMillis() - startTime) + " ms")

            sender ! Result(start, false)
        } finally {
        }
    }

    def calculatePiFor(start: Int, nrOfElements: Int): Double = {
      var acc = 0.0

      for (i <- start until (start + nrOfElements))
        acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)

      acc
    }

  }
*/
}


