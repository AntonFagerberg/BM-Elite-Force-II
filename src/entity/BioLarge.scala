package entity

import org.newdawn.slick.{Color, Graphics, GameContainer, Image}
import org.newdawn.slick.geom.{Shape, Rectangle}
import util.Random

class BioLarge(var x: Float, playerStarter: Entity, neutralStarter: Entity, var speedY: Float) extends Entity {
  private val bulletStarter = new Starter
  private var health = 500
  private var bulletDelay = 1000
  private val startX = x
  private var superDelta = 0l
  private var spriteChange = 0
  private var hitWait = 0
  private val sprites = Vector(
    new Image("gfx/bio_large_1.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/bio_large_2.png", false, Image.FILTER_NEAREST).getScaledCopy(5f)
  )
  private var y = -sprites.head.getHeight / 2f
  private val xOffset = sprites.head.getWidth / 2f - 20f
  private val yOffset = sprites.head.getHeight / 2f - 20f
  private val hitBox = new Rectangle(x - xOffset, y - yOffset, sprites.head.getWidth - 40f, sprites.head.getHeight - 40f)

  override def collision(implicit hitBoxes: Seq[Shape], color: Color): Boolean = {
    if (hitBoxes.exists(_.intersects(hitBox))) {

      if (hitWait <= 0) {
        val returnColor = color match {
          case Color.green => Color.yellow
          case Color.yellow => Color.green
          case Color.blue => Color.red
          case _ => Color.blue
        }

        bulletStarter.link(new Bullet(x - 20f, y + 120f, returnColor, playerStarter, speedX = -0.27925268f, speedY = 0.8f, angle = 20f))
        bulletStarter.link(new Bullet(x - 10f, y + 120f, returnColor, playerStarter, speedX = -0.13962634f, speedY = 0.8f, angle = 10f))
        bulletStarter.link(new Bullet(x, y + 120f, returnColor, playerStarter, speedY = 0.8f))
        bulletStarter.link(new Bullet(x + 10f, y + 120f, returnColor, playerStarter, speedX = 0.13962634f, speedY = 0.8f, angle = -10f))
        bulletStarter.link(new Bullet(x + 20f, y + 120f, returnColor, playerStarter, speedX = 0.27925268f, speedY = 0.8f, angle = -20f))
        hitWait = 5
      }

      health -= 1
      if (health <= 0) {
        neutralStarter.link(new Explosion(x, y, 4f))
        unlink()
      }
      true
    } else {
      false
    }
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y > 1100f) unlink()
    else {
      spriteChange += delta
      while (spriteChange >= 200) spriteChange -= 200

      superDelta += delta
      bulletDelay -= delta

      if (hitWait > 0)
        hitWait -= delta

      y += delta * speedY
      x = startX + 50f * math.sin(superDelta * 0.002d).toFloat
      hitBox.setLocation(x - xOffset, y - yOffset)

      bulletStarter.linkedUpdate
    }
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
   sprites(spriteChange / 100).drawCentered(x, y)
//   graphics.draw(hitBox)
   bulletStarter.linkedRender
  }
 }
