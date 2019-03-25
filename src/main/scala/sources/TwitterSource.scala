package sources

import model.JsonData

class TwitterSource extends DummySourceShape {
  val followerIncrementer = new Incrementer(6)
  val postIncrementer = new Incrementer(4)

  override def generateData() = {
    //generate everytime different arguments and update instance state
    JsonData.generateTwitterJson(followerIncrementer.get(), postIncrementer.get())
  }
}
