package sources

class Incrementer(threshold: Int) {
  var counter = 0
  var valNew = 0

  val r = scala.util.Random

  def get(): Int = {
    counter += 1

    if (counter == threshold) {
      counter = 0
      valNew = valNew + r.nextInt(5)
    }
    valNew
  }
}
