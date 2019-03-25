package sources

import model.JsonData

class InstagramSource extends DummySourceShape {
  val followerIncrementer = new Incrementer(4)
  val postIncrementer = new Incrementer(2)

  override def generateData() = {
    //generate everytime different arguments and update instance state
    JsonData.generateInstagramJson(followerIncrementer.get(), postIncrementer.get())
  }

}
