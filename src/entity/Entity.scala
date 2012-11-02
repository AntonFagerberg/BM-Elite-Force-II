package entity

import org.newdawn.slick.{Graphics, GameContainer}
import org.newdawn.slick.geom.Shape

trait Entity {
  private var nextEntity: Option[Entity] = None
  private var previousEntity: Option[Entity] = None

  def next = nextEntity
  def previous = previousEntity

  def update(implicit gameContainer: GameContainer, delta: Int) {
    updateNext
  }

  final def updateNext(implicit gameContainer: GameContainer, delta: Int) {
    if (nextEntity.isDefined) nextEntity.get.update(gameContainer, delta)
  }

  def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    renderNext
  }

  final def renderNext(implicit gameContainer: GameContainer, graphics: Graphics) {
    if (nextEntity.isDefined) nextEntity.get.render(gameContainer, graphics)
  }

  def collision(hitBoxes: List[Shape]): Boolean = false

  def link(entity: Entity) {
    if (nextEntity.isDefined) nextEntity.get.previousEntity = Some(entity)
    entity.nextEntity = nextEntity
    nextEntity = Some(entity)
    entity.previousEntity = Some(this)
  }

  def unlink() {
    if (nextEntity.isDefined) nextEntity.get.previousEntity = previousEntity
    if (previousEntity.isDefined) previousEntity.get.nextEntity = nextEntity
  }
}
