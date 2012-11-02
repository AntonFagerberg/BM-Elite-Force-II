package entity

import org.newdawn.slick.{Graphics, Color}

trait Entity {
  private var next: Option[Entity] = None
  private var previous: Option[Entity] = None

  def update(delta: Int, linker: Linker)

  def render(graphics: Graphics)

  def collision(x: (Float, Float), y: (Float, Float), color: Color): Boolean = {
    println("Called collision on Entity which does not implement it.")
    false
  }

  def link(newEntity: Entity) {
    if (next.isDefined) {
      newEntity.nextEntity = next
      next.get.previousEntity = Some(newEntity)
    }

    next = Some(newEntity)
    newEntity.previousEntity = Some(this)
  }

  def unlink() {
    previous.get.nextEntity = next
    if (next.isDefined) {
      next.get.previousEntity = previous
    }
  }

  def newLink(newEntity: Option[Entity]) {
    next = newEntity
  }

  def getNext: Option[Entity] = next
}