package sources

import model.JsonData

class FacebookSource extends DummySourceShape {
  val friendIncrementer = new Incrementer(10)
  val postIncrementer = new Incrementer(20)

  override def generateData() = {
    //generate everytime different arguments and update instance state
    val record = JsonData.generateFacebookJson(friendIncrementer.get(), postIncrementer.get())
    println(record)
    record
  }
}
