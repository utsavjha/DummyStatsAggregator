import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
 implicit val system = ActorSystem()
 implicit val mat = ActorMaterializer()
 implicit val executionContext: ExecutionContextExecutor = system.dispatcher

 AggregatorGraph.init().to(Sink.foreach(println)).run()
}
