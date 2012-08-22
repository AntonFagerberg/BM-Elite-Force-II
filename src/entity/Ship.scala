package entity

import scala.Array
import org.newdawn.slick.{GameContainer, Image, Color}
import collection.mutable.ArrayBuffer

class Ship(gc: GameContainer, color: String) {
  private val flySprites = new ArrayBuffer[Image]
  private var spriteChange = 0
  private var spriteIndex = 0
  private var halfWidth = -1f
  private var halfHeight = -1f

  def xMargin = halfWidth
  def yMargin= halfHeight

  private val speeder = Array(
    new Image("gfx/" + color + "_speeder_mark_1_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/" + color + "_speeder_mark_1_2.png", false, Image.FILTER_NEAREST)
  )

  private def speederScale(imageHeight: Int) =  0.07f * gc.getHeight / imageHeight

  def setSpeeder() {
    for (s <- speeder) {
      flySprites += s.getScaledCopy(speederScale(s.getHeight))
    }

    halfWidth = flySprites.head.getWidth / 2
    halfHeight = flySprites.head.getHeight / 2
  }

  def bullet(x: Float, y: Float): Entity = {
    new Bullet(x, y - halfHeight, gc.getWidth, gc.getHeight, color)
  }

  def update(delta: Int) {
    spriteChange += delta
    if (spriteChange > 50) {
      spriteChange = 0
      spriteIndex = (spriteIndex + 1) % flySprites.size
    }
  }

  def draw(x: Float, y: Float) {
    flySprites(spriteIndex).drawCentered(x, y)
  }
}

private class Bullet(var x: Float, var y: Float, screenWidth: Int, screenHeight: Int, color: String) extends Entity {
  val bulletColor = color match {
    case "green" => Color.green
    case _ => Color.white
  }

  private val image = new Image("gfx/bullet.png")
  val xAlign = image.getWidth / 2
  val yAlign = image.getHeight / 2

  //image.setFilter(Image.FILTER_NEAREST)

  def update(delta: Int) {
    y -= 1.3f * delta

    if (y < -100) {
      unlink()
    }
  }

  def render() {
    image.draw(x - xAlign, y - yAlign, bulletColor)
  }
}