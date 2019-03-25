package sources

import model.JsonData

class TwitterSource extends DummySourceShape {
  val followerIncrementer = new Incrementer(3000)
  val postIncrementer = new Incrementer(2000)

  override def generateData() = {
    //generate everytime different arguments and update instance state
    JsonData.generateTwitterJson(followerIncrementer.get(), postIncrementer.get())
  }
}
