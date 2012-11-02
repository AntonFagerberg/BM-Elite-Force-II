package entity

import org.newdawn.slick.geom.{Shape, Rectangle}
import org.newdawn.slick.{Graphics, GameContainer}

class TestBox extends Entity {
  val box = new Rectangle(100, 100, 100, 100)

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    updateNext
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    graphics.draw(box)
    renderNext
  }

  override def collision(hitBoxes: List[Shape]): Boolean = {
    for (hitBox <- hitBoxes if hitBox.contains(box)) println("Hit!")
    false
  }
}