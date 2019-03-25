package sources

import model.JsonData

class FacebookSource extends DummySourceShape {
  val friendIncrementer = new Incrementer(2)
  val postIncrementer = new Incrementer(5)

  override def generateData() = {
    JsonData.generateFacebookJson(friendIncrementer.get(), postIncrementer.get())

  }
}
