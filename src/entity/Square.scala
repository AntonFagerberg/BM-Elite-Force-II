package entity

import org.newdawn.slick.{Color, Image}

class Square extends Entity {
  private val x = 500f
  private val y = 500f
  private val image = new Image("gfx/square.png")
  private val marginX = image.getHeight / 2f
  private val marginY = image.getWidth / 2f
  private var hitTime = 0

  override def collision(x: (Float, Float), y: (Float, Float), color: Color): Boolean = {
    if (
        ((this.x - marginX) < x._1 && (this.x + marginX) > x._1 || (this.x - marginX) < x._2 && (this.x + marginX) > x._2)
        &&
        ((this.y - marginX) < y._1 && (this.y + marginX) > y._1 || (this.y - marginX) < y._2 && (this.y + marginX) > y._2)
    ) {
      hitTime = 10
      true
    } else {
      false
    }
  }

  def update(delta: Int, linker: Linker) {
    hitTime -= delta
  }

  def render() {
    if (hitTime <= 0) image.drawCentered(x, y)
    else image.draw(x - marginX, y - marginY, Color.orange)
  }
}
