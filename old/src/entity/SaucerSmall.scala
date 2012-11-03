package entity

import org.newdawn.slick.{Graphics, Color, Sound, Image}
import util.Random

class SaucerSmall(random: Random) extends Entity {
  var x = 50f + 1300f * random.nextFloat()
  var y = -50f - 200f * random.nextFloat()

  val image = new Image("gfx/saucer_small_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
  val collisionHeight = image.getHeight * 0.35f
  val collisionWidth = image.getWidth * 0.35f

  var health = 10f
  val hitSound = new Sound("sfx/hit.wav")

  def update(delta: Int, linker: Linker) {
    image.rotate(0.1f * delta)
    y += 0.2f * delta

    if (y > 1000) {
      unlink()
    }
  }

  override def collision(x: (Float, Float), y: (Float, Float), color: Color): Boolean = {
    if (
    ((this.x - collisionWidth) < x._1 && (this.x + collisionWidth) > x._1 || (this.x - collisionWidth) < x._2 && (this.x + collisionWidth) > x._2)
    &&
    ((this.y - collisionHeight) < y._1 && (this.y + collisionHeight) > y._1 || (this.y - collisionHeight) < y._2 && (this.y + collisionHeight) > y._2)
    ) {
      hitSound.play()

      if (health <= 0) {
        unlink()
        true
      } else {
        health -= 1
        true
      }
    } else {
      false
    }
  }

  def render(graphics: Graphics) {
    image.drawCentered(x, y)
  }
}
