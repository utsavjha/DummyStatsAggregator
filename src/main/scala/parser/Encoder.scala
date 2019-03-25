package parser


import io.circe.generic.auto._
import io.circe.syntax._
import model.GenericEntry

object Encoder {
  def encodeData(inpEntry : GenericEntry) : String = {
    inpEntry.asJson.noSpaces
  }

}
