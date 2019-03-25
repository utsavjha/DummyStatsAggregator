package sources

import model.JsonData

class FacebookSource extends DummySourceShape {
  val friendIncrementer = new Incrementer(1000)
  val postIncrementer = new Incrementer(500)

  override def generateData() = {
    JsonData.generateFacebookJson(friendIncrementer.get(), postIncrementer.get())

  }
}
