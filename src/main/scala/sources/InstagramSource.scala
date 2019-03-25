package sources

import model.JsonData

class InstagramSource extends DummySourceShape {
  var numFollowers = 3
  var numPosts = 99

  override def generateData() = {
    //generate everytime different arguments and update instance state
    JsonData.generateInstagramJson(numFollowers, numPosts)
  }
}
