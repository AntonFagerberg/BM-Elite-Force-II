package entity

import org.newdawn.slick.{Image, Color}

class Bullet(var x: Float, var y: Float, color: Color, speedX: Float = 0f, speedY: Float = -1.3f, angle: Float = 0f) extends Entity {
  private val image = new Image("gfx/bullet.png")
  image.setRotation(angle)
  private val imageAlignX = image.getWidth / 2f
  private val imageAlignY = image.getHeight / 2f

  def update(delta: Int, linker: Linker) {
    y += speedY * delta
    x += speedX * delta

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
    image.draw(x - imageAlignX, y - imageAlignY, color)
  }
}