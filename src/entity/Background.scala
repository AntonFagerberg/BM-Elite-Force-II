package entity

import util.Random
import org.newdawn.slick.{Color, Image}
import collection.mutable.ArrayBuffer

class Background(screenWidth: Float, screenHeight: Float, random: Random) extends Entity {
  val backgroundLinker = new Linker
  for (i <- 0 to 100) backgroundLinker.link(new Star(screenWidth, screenHeight, random))

  def update(delta: Int) {
    backgroundLinker.update(delta)
  }

  def render() {
    backgroundLinker.render()
  }
}

class Star(screenWidth: Float, screenHeight: Float, random: Random) extends Entity {
  private val speed = 0.2f + random.nextFloat()
  private val image = new Image("gfx/star.png", false, Image.FILTER_NEAREST, Color.green)
  val scale = random.nextFloat()
  private val x = screenWidth * random.nextFloat()
  private var y = -20f

  def update(delta: Int) {
    y += speed * delta
    if (y > screenHeight + 20) {
      link(new Star(screenWidth, screenHeight, random))
      unlink()
    }
  }

  def render() {
    image.draw(x, y, scale)
  }
}