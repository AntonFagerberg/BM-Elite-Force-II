package entity

import org.newdawn.slick.{Color, Graphics, GameContainer, Image}
import org.newdawn.slick.geom.{Shape, Rectangle}
import util.Random

class BioMedium(var x: Float, var y: Float, playerStarter: Entity, neutralStarter: Entity) extends Entity {
  private val bulletStarter = new Starter
  private var health = 100
  private var bulletDelay = 1000
  private val startX = x
  private var superDelta = 0l
  private var colorChange = 0
  private var spriteChange = 0
  private val random = new Random()
  private val sprites = Vector(
    new Image("gfx/bio_medium_1.png", false, Image.FILTER_NEAREST).getScaledCopy(4f),
    new Image("gfx/bio_medium_2.png", false, Image.FILTER_NEAREST).getScaledCopy(4f)
  )
  private val xOffset = sprites.head.getWidth / 2f - 20f
  private val yOffset = sprites.head.getHeight / 2f - 20f
  private val colors = Vector(Color.green, Color.blue, Color.yellow, Color.red)
  private val hitBox = new Rectangle(x - xOffset, y - yOffset, sprites.head.getWidth - 40f, sprites.head.getHeight - 40f)

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
    spriteChange += delta
    while (spriteChange >= 200) spriteChange -= 200

    superDelta += delta
    bulletDelay -= delta

    if (bulletDelay <= 0) {
      colorChange = random.nextInt(4)
      bulletStarter.link(new Bullet(x, y + 75f, colors(colorChange), playerStarter, speedY = 1.5f))
      bulletStarter.link(new Bullet(x + 12f, y + 65f, colors(colorChange), playerStarter, speedY = 1.5f))
      bulletStarter.link(new Bullet(x - 12f, y + 65f, colors(colorChange), playerStarter, speedY = 1.5f))
      while (bulletDelay <= 0) bulletDelay += 1000
    }

    y += delta * 0.15f
    x = startX + 50f * math.sin(superDelta * 0.002d).toFloat
    hitBox.setLocation(x - xOffset, y - yOffset)
    bulletStarter.linkedUpdate
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
   sprites(spriteChange / 100).drawCentered(x, y)
   graphics.draw(hitBox)
   bulletStarter.linkedRender
  }
 }
