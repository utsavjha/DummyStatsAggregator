class Person {
  var name: String = _
  var age: Int = _
  override def toString: String =
    s"($name, $age)"
}

object Person {
  def apply(name: String, age: Int): Person = {
    var p = new Person
    p.name = name
    p.age = age
    p
  }
}