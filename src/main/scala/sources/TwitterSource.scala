package sources

import model.JsonData

class TwitterSource extends DummySourceShape {
  var numFollowers = 6
  var numPosts = 77

  override def generateData() = {
    //generate everytime different arguments and update instance state
    JsonData.generateTwitterJson(numFollowers, numPosts)
  }
}
