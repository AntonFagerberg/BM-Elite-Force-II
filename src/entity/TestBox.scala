package entity

import org.newdawn.slick.geom.{Shape, Rectangle}
import org.newdawn.slick.{Color, Graphics, GameContainer}

class TestBox extends Entity {
  val box = new Rectangle(100, 100, 100, 100)

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    updateNext
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    graphics.draw(box)
    renderNext
  }

  override def collision(implicit hitBoxes: List[Shape], color: Option[Color] = None): Boolean = {
    hitBoxes.exists(_ intersects(box))
  }
}