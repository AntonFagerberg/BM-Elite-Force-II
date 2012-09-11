package entity

import collection.mutable.ArrayBuffer

class Linker(name: String) extends Entity {
  private val references = new ArrayBuffer[Linker]
  private var currentEntity: Option[Entity] = None

  override def toString = name

  def addReference(linker: Linker*) {
    references.append(linker: _*)
  }

  def reference(index: Int): Option[Linker] = {
    try {
      Some(references(index))
    } catch {
      case e: IndexOutOfBoundsException => println("Called non existing Linker in '" + name + "' with index '" + index + "'.") ; None
    }
  }

  def update(delta: Int, linker: Linker = this) {
    currentEntity = getNext
    while (currentEntity.isDefined) {
      currentEntity.get.update(delta, linker)
      currentEntity = currentEntity.get.getNext
    }
  }

  def render() {
    currentEntity = getNext
    while (currentEntity.isDefined) {
      currentEntity.get.render()
      currentEntity = currentEntity.get.getNext
    }
  }
}

