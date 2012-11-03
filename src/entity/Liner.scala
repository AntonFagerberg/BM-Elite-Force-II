package entity

import org.newdawn.slick.{Color, Graphics, GameContainer, Image}
import org.newdawn.slick.geom.{Shape, Transform, Rectangle}

class Liner(var x: Float, var y: Float, destroyable: Boolean, colors: Vector[Color], neutralStarter: Entity, colorSwitch: Int = 0, angle: Float = 0f, speedX: Float = 0f, speedY: Float = 0f, speedRotation: Float = 0f, bulletSpeed: Float = 1f) extends Entity {
  private var bulletDelay = 0
  private var angleRotation = 0d
  private var angleCos = 0f
  private var angleSin = 0f
  private var colorIndex = 0
  private var colorCounter = 0
  private val sprite = new Image("gfx/liner_white_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2f)
  private val bulletStarter = new Starter
  private val hitBox = new Rectangle(x - sprite.getWidth / 2, y - sprite.getHeight / 2, sprite.getWidth, sprite.getHeight)
  sprite.rotate(angle)

  override def collision(implicit hitBoxes: List[Shape], color: Option[Color] = None): Boolean = {
    val rotatedHitBox = hitBox.transform(Transform.createRotateTransform(math.toRadians(sprite.getRotation).toFloat, x, y))
    hitBoxes.exists(_.intersects(rotatedHitBox))
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y < -50f || y > 950f || x < - 50f || x > 1490f) unlink()
    else {
      for (i <- 0 until delta) {
        sprite.rotate(speedRotation)
        x += speedX
        y += speedY
      }

      bulletDelay -= delta

      if (bulletDelay <= 0) {
        bulletDelay = 70 - (12 * bulletSpeed.toInt)
        angleRotation = sprite.getRotation * math.Pi / 180d
        angleCos = math.cos(angleRotation).toFloat
        angleSin = math.sin(angleRotation).toFloat
        bulletStarter.link(new Bullet(x + 20f * angleSin, y - 20f * angleCos, colors(colorIndex), bulletSpeed * angleSin, bulletSpeed * -angleCos, sprite.getRotation))
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
    graphics.draw(hitBox.transform(Transform.createRotateTransform(math.toRadians(sprite.getRotation).toFloat, x, y)))
    sprite.drawCentered(x, y)
    bulletStarter.linkedRender
  }
}
