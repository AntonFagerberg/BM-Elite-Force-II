package entity

import org.newdawn.slick.{Color, Sound, Image}
import util.Random

class SaucerBoss(random: Random) extends Entity {
  var x = 720f
  var y = 450f
  var bulletDelay = 100
  var rotationRadians = 0d
  var angle1 = 0d
  var angle1cos = 0f
  var angle1sin = 0f
  var angle2 = 0d
  var angle2cos = 0f
  var angle2sin = 0f
  var angle3 = 0d
  var angle3cos = 0f
  var angle3sin = 0f


  val image = new Image("gfx/saucer_boss_7.png", false, Image.FILTER_NEAREST).getScaledCopy(5f)
  val collisionHeight = image.getHeight * 0.35f
  val collisionWidth = image.getWidth * 0.35f

  var health = 100f
  val hitSound = new Sound("sfx/asteroid_hit.wav")

  def update(delta: Int, linker: Linker) {
    image.rotate(0.1f * delta)
    rotationRadians = image.getRotation * math.Pi / 180d
    bulletDelay -= delta

    if (bulletDelay < 0) {
      angle1 = rotationRadians + 0.48d
      angle1cos = math.cos(angle1).toFloat
      angle1sin = math.sin(angle1).toFloat
      angle2 = rotationRadians + 2.7d
      angle2cos = math.cos(angle2).toFloat
      angle2sin = math.sin(angle2).toFloat
      angle3 = rotationRadians + -1.53d
      angle3cos = math.cos(angle3).toFloat
      angle3sin = math.sin(angle3).toFloat

      if (linker.getReference.isDefined) linker.getReference.get.link(new Bullet(x + 90f * angle1cos, y + 90f * angle1sin, Color.red, angle1cos, angle1sin, 90f + (angle1 * 180d / math.Pi).toFloat))
      if (linker.getReference.isDefined) linker.getReference.get.link(new Bullet(x + 90f * angle2cos, y + 90f * angle2sin, Color.red, angle2cos, angle2sin, 90f + (angle2 * 180d / math.Pi).toFloat))
      if (linker.getReference.isDefined) linker.getReference.get.link(new Bullet(x + 90f * angle3cos, y + 90f * angle3sin, Color.red, angle3cos, angle3sin, 90f + (angle3 * 180d / math.Pi).toFloat))
      else sys.error("Could not get reference linker in SaucerBoss.")
      bulletDelay = 50
    }

    if (y < 450)
      y += 0.2f * delta

    if (y > 1000) {
      unlink()
    }
  }

  override def collision(x: (Float, Float), y: (Float, Float)): Boolean = {
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

  def render() {
    image.drawCentered(x, y)
  }
}
