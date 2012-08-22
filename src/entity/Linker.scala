package entity

class Linker extends Entity {
  def update(delta: Int) {
    var currentEntity = this.getNext
    while (currentEntity.isDefined) {
      currentEntity.get.update(delta)
      currentEntity = currentEntity.get.getNext
    }
  }
  def render() {
    var currentEntity = this.getNext
    while (currentEntity.isDefined) {
      currentEntity.get.render()
      currentEntity = currentEntity.get.getNext
    }
  }

  def initiate(entity: Entity) {
    link(entity)
  }
}
