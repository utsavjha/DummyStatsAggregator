package model

case class ResultEntry(totalFollowers: Int, totalPosts: Int, followerDistribution: Map[String, Float])
