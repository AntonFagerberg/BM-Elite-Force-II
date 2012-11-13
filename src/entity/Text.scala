package entity

import org.newdawn.slick.{Image, Graphics, GameContainer}

class Text(id: Int, var holdTime: Int = 2500) extends Entity {
  private var y = -300f
  private val image = id match {
    case 1 => new Image("gfx/part_one.png")
    case 2 => new Image("gfx/part_two.png")
    case 3 => new Image("gfx/part_three.png")
    case 4 => new Image("gfx/part_four.png")
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y < 450) y += delta * 0.2f
    else if (holdTime > 0) holdTime -= delta
    else image.setAlpha(image.getAlpha - delta * 0.001f)

    if (image.getAlpha <= 0) unlink()
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    image.drawCentered(720, y)
  }
}
