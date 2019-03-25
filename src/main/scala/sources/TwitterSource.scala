package sources

import model.JsonData

class TwitterSource extends DummySourceShape {
  var numFollowers = 0
  var numPosts = 0

  override def generateData() = {
    //generate everytime different arguments and update instance state
    JsonData.generateTwitterJson(numFollowers, numPosts)
  }
}
