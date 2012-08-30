package entity

trait Entity {
  private var next: Option[Entity] = None
  private var previous: Option[Entity] = None

  def update(delta: Int, linker: Linker)
  def render()
  def collision(x: (Float, Float), y: (Float, Float)): Boolean = {
    sys.error("Called collision on Entity which do not implement it.")
    false
  }

  def link(newEntity: Entity) {
    if (next.isDefined) {
      newEntity.next = next
      next.get.previous = Some(newEntity)
    }

    next = Some(newEntity)
    newEntity.previous = Some(this)
  }

  def unlink() {
    previous.get.next = next
    if (next.isDefined) {
      next.get.previous = previous
    }
  }

  def getNext: Option[Entity] = next
}