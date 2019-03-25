package sources

import model.JsonData

class FacebookSource extends DummySourceShape {
  var numFriends = 0
  var numPosts = 0

  override def generateData() = {
    //generate everytime different arguments and update instance state
    JsonData.generateFacebookJson(numFriends, numPosts)
  }
}
