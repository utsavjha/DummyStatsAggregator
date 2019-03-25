import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent.Future
object Stream {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Sys")
    implicit val materializer = ActorMaterializer()
    val numbers = 1 to 100

    val persons = Array(Person("Tomas", 99), Person("Zitong", 100), Person("Tomas", 100))

    //We create a Source that will iterate over the number sequence
    val numberSource: Source[Person, NotUsed] = Source.fromIterator(() => persons.iterator)
    //Only let pass even numbers through the Flow
    val isEvenFlow: Flow[Person, Person, NotUsed] = Flow[Person].filter((p) => p.age > 99)
    //Create a Source of even random numbers by combining the random number Source with the even number filter Flow
    val evenNumbersSource: Source[Person, NotUsed] = numberSource.via(isEvenFlow)

    //A Sink that will write its input onto the console
    val consoleSink: Sink[Person, Future[Done]] = Sink.foreach[Person](println)
    //Connect the Source with the Sink and run it using the materializer
    numberSource.runWith(consoleSink)
    evenNumbersSource.runWith(consoleSink)
  }
}