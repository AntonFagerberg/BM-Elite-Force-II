package entity

import org.newdawn.slick._
import org.newdawn.slick.geom.{Shape, Transform, Rectangle}

class Bullet(var x: Float, var y: Float, color: Color, collisionStarter: Entity, speedX: Float = 0f, speedY: Float = -1.3f, angle: Float = 0f) extends Entity {
  private val sprite = new Image("gfx/bullet.png")
  private val sound = new Sound("sfx/hit.wav")
  private val yOffset = 14f
  private val xOffset = 7f
  private val hitBox = new Rectangle(x - xOffset, y - yOffset, 15, 30)
  sprite.setRotation(angle)

//  override def collision(implicit hitBoxes: List[Shape], color: Option[Color] = None): Boolean = {
//    if (hitBoxes.exists(_.intersects(hitBox.transform(Transform.createRotateTransform(math.toRadians(angle).toFloat, hitBox.getX + xOffset, hitBox.getY + yOffset))))) {
//      unlink()
//      true
//    } else {
//      false
//    }
//  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y < -100 || y > 1000 || x < -100 || x > 1500) unlink()
    else {
      for (i <- 0 until delta) {
        y += speedY
        x += speedX
      }

      if (collisionStarter.linkedCollision(List(hitBox.transform(Transform.createRotateTransform(math.toRadians(angle).toFloat, hitBox.getX + xOffset, hitBox.getY + yOffset))), color) > 0) {
        unlink()
        sound.play(1f, 0.5f)
      } else hitBox.setLocation(x - xOffset, y - yOffset)
    }

  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    sprite.draw(x - sprite.getWidth / 2f, y - sprite.getHeight / 2f, color)
    graphics.draw(hitBox.transform(Transform.createRotateTransform(math.toRadians(angle).toFloat, hitBox.getX + xOffset, hitBox.getY + yOffset)))
  }
}
