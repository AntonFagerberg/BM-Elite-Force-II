package entity

import org.newdawn.slick.{Color, Sound, Image}
import util.Random

class SaucerBoss extends Entity {
  private val expandSound = new Sound("sfx/saucer_boss_expand.wav")
  private val hitSound = new Sound("sfx/asteroid_hit.wav")
  private var bullets: Option[Linker] = None
  private val x = 720f
  private var y = -450f
  private var health = 450f
  private var bulletDelay = 100
  private var rotationRadians = 0d
  private var angle = 0f
  private var angle1 = 0d
  private var angle1cos = 0f
  private var angle1sin = 0f
  private var angle2 = 0d
  private var angle2cos = 0f
  private var angle2sin = 0f
  private var angle3 = 0d
  private var angle3cos = 0f
  private var angle3sin = 0f
  private var superDelta = 0
  private var spriteChange = 0

  private val sprites = IndexedSeq(
    new Image("gfx/saucer_boss_inactive_1.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_inactive_2.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_inactive_3.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_inactive_4.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_active_1.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_active_2.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_active_3.png", false, Image.FILTER_NEAREST).getScaledCopy(5f)
  )

  private val collisionHeight = sprites.head.getHeight * 0.35f
  private val collisionWidth = sprites.head.getWidth * 0.35f
  private var sprite = sprites.head
  private val explosion = new Sound("sfx/explosion_big.wav")

  def update(delta: Int, linker: Linker) {
    superDelta += delta

    if (superDelta <= 6892) {
      sprite = sprites(0)
    } else if (superDelta <= 8500) {
      if (sprite == sprites(0)) expandSound.play()
      sprite = sprites(1)
    } else if (superDelta <= 10000) {
      if (sprite == sprites(1)) expandSound.play()
      sprite = sprites(2)
    } else if (superDelta <= 11500) {
      if (sprite == sprites(2)) expandSound.play()
      sprite = sprites(3)
    } else {
      spriteChange = (spriteChange + delta) % 400

      if (spriteChange < 100) sprite = sprites(4)
      else if (spriteChange < 200)  sprite = sprites(5)
      else sprite = sprites(6)

      if (superDelta >= 13727) {
        for (i <- 0 until delta) {
          // 0.25

          angle =
            if (health > 400) 0.03f + (0.10f * (450 - health) / 50)
            else if (health > 300) 0.13f * (-350 + health) / 50
            else if (health > 200) 0.13f * (250 - health) / 50
            else 0.13f + (0.12f * (200 - health) / 200)

          sprites(4).rotate(angle)
          sprites(5).rotate(angle)
          sprites(6).rotate(angle)
        }

        rotationRadians = sprite.getRotation * math.Pi / 180d
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
          if (bullets.isEmpty) bullets = linker.reference(0)

          if (bullets.isDefined) {
            bullets.get.link(new Bullet(x + 100f * angle1cos, y + 100f * angle1sin, Color.red, angle1cos, angle1sin, 90f + (angle1 * 180d / math.Pi).toFloat))
            bullets.get.link(new Bullet(x + 100f * angle2cos, y + 100f * angle2sin, Color.green, angle2cos, angle2sin, 90f + (angle2 * 180d / math.Pi).toFloat))
            bullets.get.link(new Bullet(x + 100f * angle3cos, y + 100f * angle3sin, Color.yellow, angle3cos, angle3sin, 90f + (angle3 * 180d / math.Pi).toFloat))
          }
          bulletDelay += 50
        }
      }
    }

    for (i <- 0 until delta) {
      if (y < 450) y += 0.2f
      else if(y != 450) y = 450
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
        explosion.play()
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
    sprite.drawCentered(x, y)
  }
}
