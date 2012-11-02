package entity

import util.Random
import org.newdawn.slick.{Graphics, GameContainer, Image}

class Background(starter: Entity) extends Entity {
  private val random = new Random
  private val image = new Image("gfx/star.png", false, Image.FILTER_NEAREST)
  for (i <- 0 to 100) starter.link(new Star)

  private class Star extends Entity {
    private val speed = 0.2f + random.nextFloat()
    private val scale = 0.1f + random.nextFloat()
    private val x = 1440f * random.nextFloat()
    private var y = -20f

    override def update(implicit gameContainer: GameContainer, delta: Int) {
      for (i <- 0 until delta) y += speed

      if (y > 920f) {
        link(new Star)
        unlink()
      }

      updateNext
    }

    override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
      image.draw(x, y, scale)
      renderNext
    }
  }
}
