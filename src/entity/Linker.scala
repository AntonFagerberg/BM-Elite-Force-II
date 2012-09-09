package entity

class Linker(name: String, var reference: Option[Linker] = None) extends Entity {
  def getReference = reference
  override def toString = name

  def update(delta: Int, linker: Linker = this) {
    var currentEntity = getNext
    while (currentEntity.isDefined) {
      currentEntity.get.update(delta, this)
      currentEntity = currentEntity.get.getNext
    }
  }

  def setReference(linker: Linker) {
    reference = Some(linker)
  }

  def render() {
    var currentEntity = getNext
    while (currentEntity.isDefined) {
      currentEntity.get.render()
      currentEntity = currentEntity.get.getNext
    }
  }
}

