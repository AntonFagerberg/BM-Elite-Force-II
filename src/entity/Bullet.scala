package entity

import org.newdawn.slick.{Image, Color}

class Bullet(var x: Float, var y: Float, color: Color, speedX: Float = 0f, speedY: Float = -1.3f, angle: Float = 0f) extends Entity {
  private val image = new Image("gfx/bullet.png")
  private val marginX = 15
  private val marginY = 30
  private val hitBoxX = 15
  private val hitBoxY = 7
  image.setRotation(angle)

  def update(delta: Int, linker: Linker) {
    for (i <- 0 until delta) {
      y += speedY
      x += speedX
    }

    if (linker.reference(0).isDefined) {
      val collisionX = (x - hitBoxX) -> (x + hitBoxX)
      val collisionY = (y - hitBoxY) -> (y + hitBoxY)

      var currentItem = linker.reference(0).get.getNext
      while (currentItem.isDefined) {
        if (currentItem.get.collision(collisionX, collisionY, color)) unlink()
        currentItem = currentItem.get.getNext
      }
    }

    if (y < -100 || y > 1000 || x < -100 || x > 1500) unlink()
  }

  def render() {
    image.draw(x - marginX, y - marginY, color)
  }
}