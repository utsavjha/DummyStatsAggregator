package sources

import akka.NotUsed
import akka.stream.scaladsl.{Sink, Source, SourceQueueWithComplete}
import model._

class FacebookSource {

  // Create a source of String using the generateFacebookJson function
  // Update it every n-seconds with some simple generation logic

  // Use scheduler for pushing data to the Source. Maybe use Source.queue

  val bufferSize = 100
  val overflowStrategy = akka.stream.OverflowStrategy.dropHead
  val queue: SourceQueueWithComplete[Nothing] = Source.queue(bufferSize, overflowStrategy)
    .to(Sink foreach println).run()


  queue offer JsonData.generateFacebookJson(3, 2)

}
//case class Weather(zipCode : String, temperature : Double, raining : Boolean)
//
//val bufferSize = 100
//
////if the buffer fills up then this strategy drops the oldest elements
////upon the arrival of a new element.
//val overflowStrategy = akka.stream.OverflowStrategy.dropHead
//
//val queue: SourceQueueWithComplete[Nothing] = Source.queue(bufferSize, overflowStrategy)
//  .to(Sink foreach println)
//  .run() // in order to "keep" the queue Materialized value instead of the Sink's
//
//queue offer Weather("02139", 32.0, true)
