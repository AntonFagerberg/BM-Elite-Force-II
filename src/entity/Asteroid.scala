package entity

import org.newdawn.slick.Image
import util.Random

class Asteroid(kind: String, random: Random) extends Entity {
  var x = 50f + 1300f * random.nextFloat()
  var y = -50f - 200f * random.nextFloat()
  val randomSpeed = random.nextFloat()
  val speed = 0.5f * randomSpeed
  val randomSize = 2.5f * random.nextFloat()
  val size = 1.5f + randomSize
  val image = new Image("gfx/asteroid_" + kind + ".png", false, Image.FILTER_NEAREST).getScaledCopy(size)
  val collisionHeight = image.getHeight * 0.35f
  val collisionWidth = image.getWidth * 0.35f
  val randomRotate = randomSpeed * 0.5f * (if (random.nextBoolean()) -1 else 1)
  var health = 1f + randomSize

  def update(delta: Int, linker: Linker) {
    image.rotate(randomRotate * delta)
    y += speed * delta

    if (y > 1000) {
      link(new Asteroid("lava", random))
      unlink()
    }
  }

  override def collision(x: (Float, Float), y: (Float, Float)): Boolean = {
    if (
    ((this.x - collisionWidth) < x._1 && (this.x + collisionWidth) > x._1 || (this.x - collisionWidth) < x._2 && (this.x + collisionWidth) > x._2)
    &&
    ((this.y - collisionHeight) < y._1 && (this.y + collisionHeight) > y._1 || (this.y - collisionHeight) < y._2 && (this.y + collisionHeight) > y._2)
    ) {
      if (health <= 0) {
        link(new Asteroid(kind, random))
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

  def render() {
    image.drawCentered(x, y)
  }
}
