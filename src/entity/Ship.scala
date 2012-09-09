package entity

import scala.Array
import org.newdawn.slick.{Sound, GameContainer, Image, Color}
import collection.mutable.ArrayBuffer

class Ship(gc: GameContainer, color: String) {
  private val flySprites = new ArrayBuffer[Image]
  private var spriteChange = 0
  private var spriteIndex = 0
  private var halfWidth = -1f
  private var halfHeight = -1f
  private var direction = 0

  def xMargin = halfWidth
  def yMargin= halfHeight

  private val speeder = Array(
    new Image("gfx/" + color + "_speeder_mark_1_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/" + color + "_speeder_mark_1_2.png", false, Image.FILTER_NEAREST),
    new Image("gfx/" + color + "_speeder_mark_1_L_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/" + color + "_speeder_mark_1_L_2.png", false, Image.FILTER_NEAREST),
    new Image("gfx/" + color + "_speeder_mark_1_R_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/" + color + "_speeder_mark_1_R_2.png", false, Image.FILTER_NEAREST)
  )

  def setSpeeder() {
    for (s <- speeder) {
      flySprites += s.getScaledCopy(2.5f)
    }

    halfWidth = flySprites.head.getWidth / 2
    halfHeight = flySprites.head.getHeight / 2
  }

  val bulletSound = new Sound("sfx/bullet.wav")

  def bullet(x: Float, y: Float): Entity = {
    bulletSound.play(1.0f, 0.5f)
    new Bullet(x, y - halfHeight, color)
  }

  def update(delta: Int) {
    spriteChange += delta
    if (spriteChange > 50) {
      spriteChange = 0
      spriteIndex = if (spriteIndex == 0) 1 else 0
    }
  }

  def forward() { direction = 0 }
  def left() { direction = 1 }
  def right() { direction = 2 }

  def draw(x: Float, y: Float) {
    flySprites((direction * 2) + spriteIndex).drawCentered(x, y)
  }
}

private class Bullet(var x: Float, var y: Float, color: String) extends Entity {
  val bulletColor = color match {
    case "green" => Color.green
    case _ => Color.white
  }

  private val image = new Image("gfx/bullet.png")
  private val imageAlignX = image.getWidth / 2f
  private val imageAlignY = image.getHeight / 2f

  def update(delta: Int, linker: Linker) {
    y -= 1.3f * delta

    if (linker.getReference.isEmpty) sys.error("Could not get linker reference from Bullet.")
    else {
      val collisionX = (x - imageAlignX) -> (x + imageAlignX)
      val collisionY = (y - imageAlignY + 20) -> (y + imageAlignY + 20)

      var currentItem = linker.getReference.get.getNext
      while (currentItem.isDefined) {
        if (currentItem.get.collision(collisionX, collisionY))
          unlink()
        currentItem = currentItem.get.getNext
      }
    }

    if (y < -100)
      unlink()
  }

  def render() {
    image.draw(x - imageAlignX, y - imageAlignY, bulletColor)
  }
}