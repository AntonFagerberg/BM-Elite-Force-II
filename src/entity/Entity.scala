package entity

import org.newdawn.slick.{Color, Graphics, GameContainer}
import org.newdawn.slick.geom.Shape

trait Entity {
  private var nextEntity: Option[Entity] = None
  private var previousEntity: Option[Entity] = None

  def next = nextEntity
  def previous = previousEntity

  def update(implicit gameContainer: GameContainer, delta: Int) {}
  def render(implicit gameContainer: GameContainer, graphics: Graphics) {}
  def collision(implicit hitBoxes: Seq[Shape], color: Color = Color.white): Boolean = {
    println("Called collision on Entity [" + this.getClass + "] which does not implement it. ")
    false
  }

  final def linkedUpdate(implicit gameContainer: GameContainer, delta: Int) {
    update
    if (nextEntity.isDefined) nextEntity.get.linkedUpdate
  }

  final def linkedRender(implicit gameContainer: GameContainer, graphics: Graphics) {
    render
    if (nextEntity.isDefined) nextEntity.get.linkedRender
  }

  def linkedCollision(implicit hitBoxes: Seq[Shape], color: Color = Color.white): Int = {
    if (collision && next.isDefined) 1 + next.get.linkedCollision
    else if (!collision && next.isDefined) next.get.linkedCollision
    else if (collision) 1
    else 0
  }

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
