import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, Source}
import model.{GenericEntry, ResultEntry}
import parser.{Encoder, FollowersDecoder, FriendDecoder}
import sources.{FacebookSource, InstagramSource, TwitterSource}

import scala.concurrent.ExecutionContextExecutor


object AggregatorGraph {
  var twitterState: GenericEntry = GenericEntry(0, 0, "twitter")
  var facebookState: GenericEntry = GenericEntry(0, 0, "facebook")
  var instagramState: GenericEntry = GenericEntry(0, 0, "instagram")
  var followerDistribution: Map[String, Float] = Map(("Facebook", 0), ("Instagram", 0), ("Twitter", 0))

  def calculateFollowerDistribution(totalFollowers: Int){
    if (totalFollowers != 0) {
      followerDistribution = followerDistribution.updated("Facebook", facebookState.numFollowers.toFloat / totalFollowers)
      followerDistribution = followerDistribution.updated("Instagram", instagramState.numFollowers.toFloat / totalFollowers)
      followerDistribution = followerDistribution.updated("Twitter", twitterState.numFollowers.toFloat / totalFollowers)
    }
  }

  def calculateResultState() = {
    // update followerDistribution
    val totalFollowers = twitterState.numFollowers + facebookState.numFollowers + instagramState.numFollowers
    calculateFollowerDistribution(totalFollowers)

    ResultEntry(totalFollowers,
      twitterState.numPosts + facebookState.numPosts + instagramState.numPosts,
      followerDistribution)
  }

  def init()(implicit system: ActorSystem, mat: ActorMaterializer, context: ExecutionContextExecutor) = {
    Source
      .fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
        import akka.stream.scaladsl.GraphDSL.Implicits._

        val facebookSource = new FacebookSource()
        val instagramSource = new InstagramSource()
        val twitterSource = new TwitterSource()

        val merger: UniformFanInShape[GenericEntry, GenericEntry] = builder.add(Merge[GenericEntry](3))

        val end: UniformFanOutShape[ResultEntry, ResultEntry] = builder.add(new Broadcast[ResultEntry](1, false))

        val decoderFlowFollowers: Flow[String, GenericEntry, NotUsed] = Flow[String].map(json => {
          FollowersDecoder.decodeData(json).get
        })

        val decoderFlowFriends: Flow[String, GenericEntry, NotUsed] = Flow[String].map(json => {
          FriendDecoder.decodeData(json).get
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


        facebookSource ~> decoderFlowFriends ~> merger ~> updateStateFlow ~> end
        instagramSource ~> decoderFlowFollowers ~> merger
        twitterSource ~> decoderFlowFollowers ~> merger

        SourceShape(end.out(0))
      })
      .named("Foo")
  }

}
