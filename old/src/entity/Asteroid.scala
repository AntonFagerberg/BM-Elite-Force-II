package entity

import org.newdawn.slick.{Graphics, Sound, Image, Color}
import util.Random

class Asteroid(kind: String, random: Random) extends Entity {
  var x = 50f + 1300f * random.nextFloat()
  var y = -50f - 200f * random.nextFloat()
  val randomSpeedY = random.nextFloat()
  val speedY = 0.2f + 0.3f * randomSpeedY
  val speedX = 0.2f * (-0.5f + random.nextFloat())
  val randomSize = 2.5f * random.nextFloat()
  val size = 1.5f + randomSize

  val image = new Image("gfx/asteroid_" + kind + "_" + (if (random.nextBoolean()) "1" else "2") + ".png", false, Image.FILTER_NEAREST).getScaledCopy(size)
  val collisionHeight = image.getHeight * 0.35f
  val collisionWidth = image.getWidth * 0.35f
  val randomRotate = randomSpeedY * 0.5f * (if (random.nextBoolean()) -1 else 1)
  var health = 2f + randomSize
  val hitSound = new Sound("sfx/hit.wav")

  def update(delta: Int, linker: Linker) {
    image.rotate(randomRotate * delta)
    y += speedY * delta
    x += speedX

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
