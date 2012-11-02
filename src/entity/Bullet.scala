package entity

import org.newdawn.slick.{Graphics, GameContainer, Color, Image}
import org.newdawn.slick.geom.Rectangle

class Bullet(var x: Float, var y: Float, color: Color, speedX: Float = 0f, speedY: Float = -1.3f, angle: Float = 0f) extends Entity {
  private val sprite = new Image("gfx/bullet.png")
  private val hitBox = new Rectangle(x - 10, y - 50, 20, 40)
  sprite.setRotation(angle)

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y < -100 || y > 1000 || x < -100 || x > 1500) unlink()
    else {
      for (i <- 0 until delta) {
        y += speedY
        x += speedX
      }

      hitBox.setLocation(x - 10, y - 50)
      updateNext
    }
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    sprite.draw(x - 15, y - 60, color)
    graphics.draw(hitBox)
    renderNext
  }
}
