package model

object JsonData {
  def generateFacebookJson(numFriends: Int, numPosts: Int) =
    s"""
       |{
       |"friends": $numFriends,
       |"posts": $numPosts,
       |"source": "facebook"
       |}
    """.stripMargin


  def generateInstagramJson(numFollowers: Int, numPosts: Int) =
    s"""
       |{
       |"followers": $numFollowers,
       |"posts": $numPosts
       |"source": "instagram"
       |}
    """.stripMargin

  def generateTwitterJson(numFollowers: Int, numPosts: Int) =
    s"""
       |{
       |"followers": $numFollowers,
       |"posts": $numPosts
       |"source": "twitter"
       |}
    """.stripMargin
}


