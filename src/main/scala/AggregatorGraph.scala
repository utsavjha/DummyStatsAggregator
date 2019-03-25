import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, Sink, Source}
import model.{GenericEntry, ResultEntry}
import sources.{FacebookSource, InstagramSource, TwitterSource}

import scala.concurrent.ExecutionContextExecutor




object AggregatorGraph {
  var twitterState: GenericEntry = GenericEntry(0, 0, "twitter")
  var facebookState: GenericEntry = GenericEntry(0, 0, "facebook")
  var instagramState: GenericEntry = GenericEntry(0, 0, "instagram")


  def calculateResultState() = new ResultEntry(
    twitterState.numFollowers + facebookState.numFollowers + instagramState.numFollowers,
    twitterState.numPosts + facebookState.numPosts + instagramState.numPosts
  )


  def init()(implicit system: ActorSystem, mat: ActorMaterializer, context: ExecutionContextExecutor) = {
    Source
      .fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
        import akka.stream.scaladsl.GraphDSL.Implicits._

        val facebookSource = new FacebookSource()
        val instagramSource = new InstagramSource()
        val twitterSource = new TwitterSource()

        val merger: UniformFanInShape[GenericEntry, GenericEntry] = builder.add(Merge[GenericEntry](3))

        val end: UniformFanOutShape[ResultEntry, ResultEntry] = builder.add(new Broadcast[ResultEntry](1, false))


        val encoderFlow: Flow[String, GenericEntry,  NotUsed] = Flow[String].map( json =>{
          // USe real encoder here
          GenericEntry(0, 0, "instagram")
        })

        val updateStateFlow: Flow[GenericEntry, ResultEntry, NotUsed] = Flow[GenericEntry].map(entry =>
          entry.source match {
            case "facebook" =>
              facebookState = entry
              calculateResultState()
            case "twitter" =>
              twitterState = entry
              calculateResultState()
            case "instagram" =>
              instagramState = entry
              calculateResultState()
        })


        facebookSource ~> encoderFlow ~> merger ~> updateStateFlow ~> end
        instagramSource ~> encoderFlow ~> merger
        twitterSource ~> encoderFlow ~> merger



        SourceShape(end.out(0))
      })
      .named("Foo")
  }

}
