package entity

import org.newdawn.slick.{Color, Graphics, GameContainer, Image}
import org.newdawn.slick.geom.{Shape, Transform, Rectangle}

class Liner(var x: Float, var y: Float, colors: Vector[Color], playerStarter: Entity, neutralStarter: Entity, colorSwitch: Int = 0, angle: Float = 0f, speedX: Float = 0f, speedY: Float = 0f, speedRotation: Float = 0f, bulletSpeed: Float = 1f, destroyable: Boolean = false) extends Entity {
  private var bulletDelay = 0
  private var angleRotation = 0d
  private var angleCos = 0f
  private var angleSin = 0f
  private var colorIndex = 0
  private var colorCounter = 0
  private val sprite =
    if (destroyable) new Image("gfx/liner_white_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2f)
    else new Image("gfx/liner_gold_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2f)
  private val bulletStarter = new Starter
  private val hitBox = new Rectangle(x - sprite.getWidth / 2, y - sprite.getHeight / 2, sprite.getWidth, sprite.getHeight)
  private var health = 50
  sprite.rotate(angle)

  override def collision(implicit hitBoxes: List[Shape], color: Color): Boolean = {
    if (destroyable && colors(colorIndex) != color && hitBoxes.exists(_.intersects(hitBox.transform(Transform.createRotateTransform(math.toRadians(sprite.getRotation).toFloat, x, y))))) {
      health -= 1
      if (health < 0) {
        neutralStarter.link(new Explosion(x, y, 3f))
        unlink()
      }
      true
    } else {
      false
    }
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y < -500f || y > 1400f || x < - 500f || x > 1940f) unlink()
    else {
      for (i <- 0 until delta) {
        sprite.rotate(speedRotation)
        x += speedX
        y += speedY
      }

      hitBox.setLocation(x - sprite.getWidth / 2, y - sprite.getHeight / 2)

      bulletDelay -= delta

      if (bulletDelay <= 0) {
        bulletDelay = 70 - (12 * bulletSpeed.toInt)
        angleRotation = sprite.getRotation * math.Pi / 180d
        angleCos = math.cos(angleRotation).toFloat
        angleSin = math.sin(angleRotation).toFloat
        bulletStarter.link(new Bullet(x + 20f * angleSin, y - 20f * angleCos, colors(colorIndex), playerStarter, bulletSpeed * angleSin, bulletSpeed * -angleCos, sprite.getRotation))
        colorCounter -= 1
        if (colorCounter <= 0) {
          colorCounter += colorSwitch
          colorIndex = (colorIndex + 1) % colors.size
        }
      }

      bulletStarter.linkedUpdate
    }
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
//    graphics.draw(hitBox.transform(Transform.createRotateTransform(math.toRadians(sprite.getRotation).toFloat, x, y)))
    sprite.drawCentered(x, y)
    bulletStarter.linkedRender
  }
}
