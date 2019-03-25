import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.contrib.PartitionWith
import akka.stream.{FanOutShape2, _}
import akka.stream.scaladsl.{Broadcast, Concat, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}
import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import model.GenericEntry
import sources.{FacebookSource, InstagramSource, TwitterSource}

import scala.concurrent.ExecutionContextExecutor




object AggregatorGraph {

  def init()(implicit system: ActorSystem, mat: ActorMaterializer, context: ExecutionContextExecutor) = {
    Source
      .fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
        import akka.stream.scaladsl.GraphDSL.Implicits._

        val facebookSource = new FacebookSource()
        val instagramSource = new InstagramSource()
        val twitterSource = new TwitterSource()

        val merger: UniformFanInShape[String, String] = builder.add(Merge[String](3))

        val end: UniformFanOutShape[String, String] = builder.add(new Broadcast[String](1, false))


        val encoderFlow: Flow[String, String,  NotUsed] = Flow[String].map( json =>{
          println("ecnoding..", json)
          json
//          new GenericEntry(9, 8, source ="foo")
        })

        val printsink = Sink.foreach(println)


        facebookSource ~> encoderFlow ~> merger ~> end
        instagramSource ~> encoderFlow ~> merger
        twitterSource ~> encoderFlow ~> merger



        SourceShape(end.out(0))
      })
      .named("Foo")
  }

}
