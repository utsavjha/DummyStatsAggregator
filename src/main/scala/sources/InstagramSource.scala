package sources

import model.JsonData

class InstagramSource extends DummySourceShape {
  val followerIncrementer = new Incrementer(2000)
  val postIncrementer = new Incrementer(1000)

  override def generateData() = {
    //generate everytime different arguments and update instance state
    JsonData.generateInstagramJson(followerIncrementer.get(), postIncrementer.get())
  }

}
