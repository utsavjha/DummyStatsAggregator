import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import model.GenericEntry
import sources.FacebookSource

import scala.concurrent.ExecutionContextExecutor




object AggregatorGraph {

  def init()(implicit system: ActorSystem, mat: ActorMaterializer, context: ExecutionContextExecutor) = {
    RunnableGraph
      .fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
        import akka.stream.scaladsl.GraphDSL.Implicits._

        val facebookSource = new FacebookSource()


        val encoderFlow: Flow[String, GenericEntry,  NotUsed] = Flow[String].map( x =>{
          println("ecnoding..")
          new GenericEntry(9, 8, source ="foo")
        })

        val printsink = Sink.foreach(println)


        facebookSource ~> encoderFlow ~> printsink

        ClosedShape
      })
      .named("Foo")
  }

}
