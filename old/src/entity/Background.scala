package entity

import util.Random
import org.newdawn.slick.{Graphics, Color, Image}
import collection.mutable.ArrayBuffer
import collection.immutable.IntMap

class Background(screenWidth: Float, screenHeight: Float, random: Random) extends Entity {
  val backgroundLinker = new Linker("Background Item")
  for (i <- 0 to 100) backgroundLinker.link(new Star(screenWidth, screenHeight, random))

  def update(delta: Int, linker: Linker) {
    backgroundLinker.update(delta)
  }

  def render(graphics: Graphics) {
    backgroundLinker.render(graphics: Graphics)
  }
}

class Star(screenWidth: Float, screenHeight: Float, random: Random) extends Entity {
  private val speed = 0.2f + random.nextFloat()
  private val image = new Image("gfx/star.png", false, Image.FILTER_NEAREST, Color.green)
  val scale = random.nextFloat()
  private val x = screenWidth * random.nextFloat()
  private var y = -20f

  def update(delta: Int, linker: Linker) {
    y += speed * delta
    if (y > screenHeight + 20) {
      link(new Star(screenWidth, screenHeight, random))
      unlink()
    }
  }

  def render(graphics: Graphics) {
    image.draw(x, y, scale)
  }
}