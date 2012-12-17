package entity

import org.newdawn.slick.{Color, Graphics, GameContainer, Image}
import org.newdawn.slick.geom.{Shape, Rectangle}

class BioSmall(var x: Float, var y: Float, playerStarter: Entity, neutralStarter: Entity, var health: Int = 100) extends Entity {
  private val bulletStarter = new Starter
  private var bulletDelay = 1000
  private val startX = x
  private var superDelta = 0l
  private var colorChange = 0
  private var spriteChange = 0
  private val sprites = Vector(
    new Image("gfx/bio_small_1.png", false, Image.FILTER_NEAREST).getScaledCopy(4f),
    new Image("gfx/bio_small_2.png", false, Image.FILTER_NEAREST).getScaledCopy(4f)
  )
  private val xOffset = sprites.head.getWidth / 2f - 10f
  private val yOffset = 20f
  private val colors = Vector(Color.green, Color.blue, Color.yellow, Color.red)
  private val hitBox = new Rectangle(x - xOffset, y - yOffset, sprites.head.getWidth - 20, 50f)

  override def collision(implicit hitBoxes: List[Shape], color: Color): Boolean = {
    if (hitBoxes.exists(_.intersects(hitBox))) {
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
    superDelta += delta
    bulletDelay -= delta
    spriteChange += delta
    while (spriteChange >= 200) spriteChange -= 200

    if (bulletDelay <= 0) {
      colorChange = (colorChange + 1) % 4
      bulletStarter.link(new Bullet(x, y + 25f, colors(colorChange), playerStarter, speedX = -0.27925268f, speedY = 0.8f, angle = 20f))
      bulletStarter.link(new Bullet(x, y + 25f, colors(colorChange), playerStarter, speedX = -0.13962634f, speedY = 0.8f, angle = 10f))
      bulletStarter.link(new Bullet(x, y + 25f, colors(colorChange), playerStarter, speedY = 0.8f))
      bulletStarter.link(new Bullet(x, y + 25f, colors(colorChange), playerStarter, speedX = 0.13962634f, speedY = 0.8f, angle = -10f))
      bulletStarter.link(new Bullet(x, y + 25f, colors(colorChange), playerStarter, speedX = 0.27925268f, speedY = 0.8f, angle = -20f))
      while (bulletDelay <= 0) bulletDelay += 1000
    }

    y += delta * 0.15f
    x = startX + 150f * math.sin(superDelta * 0.002d).toFloat
    hitBox.setLocation(x - xOffset, y - yOffset)

    if (y > 1100f)
      unlink()

    bulletStarter.linkedUpdate
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    sprites(spriteChange / 100).drawCentered(x, y)
//    graphics.draw(hitBox)
    bulletStarter.linkedRender
  }
}
