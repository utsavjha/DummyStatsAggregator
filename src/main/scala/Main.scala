import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import scala.concurrent.duration._
import scala.concurrent.ExecutionContextExecutor

object Main extends App {
 implicit val system = ActorSystem()
 implicit val mat = ActorMaterializer()
 implicit val executionContext: ExecutionContextExecutor = system.dispatcher


 AggregatorGraph.init()
   .throttle(1, 1.seconds)
   .to(Sink.foreach((entry) => println(s"totalFollowers: ${entry.totalFollowers}, totalPosts: ${entry.totalPosts}, followerDistribution: ${entry.followerDistribution}")))
   .run()


}
