package entity

import org.newdawn.slick.{Image, Color}

class Bullet(var x: Float, var y: Float, color: Color, speedX: Float = 0f, speedY: Float = -1.3f, angle: Float = 0f) extends Entity {
  private val image = new Image("gfx/bullet.png")
  private val marginX = image.getWidth / 2f
  private val marginY = image.getHeight / 2f
  image.setRotation(angle)

  def update(delta: Int, linker: Linker) {
    for (i <- 0 until delta) {
      y += speedY
      x += speedX
    }

    if (linker.reference(0).isDefined) {
      val collisionX = (x - marginX) -> (x + marginX)
      val collisionY = (y - marginY + 20) -> (y + marginY + 20)

      var currentItem = linker.reference(0).get.getNext
      while (currentItem.isDefined) {
        if (currentItem.get.collision(collisionX, collisionY)) unlink()
        currentItem = currentItem.get.getNext
      }
    }

    if (y < -100 || y > 1000 || x < -100 || x > 1500) unlink()
  }

  def render() {
    image.draw(x - marginX, y - marginY, color)
  }
}