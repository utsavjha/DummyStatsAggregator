package model

object JsonData {
  def generateFacebookJson(numFriends: Int, numPosts: Int) =
    s"""
       |{
       |"friends": $numFriends,
       |"posts": $numPosts
       |}
    """.stripMargin


  def generateInstagramJson(numFollowers: Int, numPosts: Int) =
    s"""
       |{
       |"followers": $numFollowers,
       |"posts": $numPosts
       |}
    """.stripMargin

  def generateTwitterJson(numFollowers: Int, numPosts: Int) =
    s"""
       |{
       |"followers": $numFollowers,
       |"posts": $numPosts
       |}
    """.stripMargin
}


