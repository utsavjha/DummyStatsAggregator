package parser

import model.GenericEntry

object FollowersDecoder {
  import io.circe.parser.decode
  import io.circe.{Decoder, HCursor}
  implicit val decoderWithFollowers: Decoder[GenericEntry] = new Decoder[GenericEntry] {
    final def apply(c: HCursor): Decoder.Result[GenericEntry] =
      for {
        numFollowers <- c.downField("followers").as[Int]
        numPosts <- c.downField("posts").as[Int]
        source <- c.downField("source").as[String]
      } yield {
        new GenericEntry(numFollowers, numPosts, source)
      }
  }

  def decodeData(inpJSON: String): Option[GenericEntry] = {
    decode[GenericEntry](inpJSON) match {
      case Left(ex) => println("ERROR: " + ex.getMessage)
        None
      case Right(genericEntry) => Some(genericEntry)
    }
  }


}


