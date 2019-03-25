package sources

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source, SourceQueueWithComplete}
import akka.stream.{ActorMaterializer, Materializer}

class TwitterSource {
  // Create a source of String using the generateTwitterJson function
  // Update it every n-seconds with some simple generation logic

  // Use scheduler for pushing data to the Source. Maybe use Source.queue
  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: Materializer = ActorMaterializer()
  val bufferSize = 100

  val overflowStrategy = akka.stream.OverflowStrategy.dropHead

  val queue: SourceQueueWithComplete[String] = Source.queue(bufferSize, overflowStrategy)
    .to(Sink foreach println)
    .run() // in order to "keep" the queue Materialized value instead of the Sink's

  queue offer model.JsonData.generateFacebookJson(2, 3)
}
