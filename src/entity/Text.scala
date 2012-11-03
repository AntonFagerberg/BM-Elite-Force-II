package entity

import org.newdawn.slick.{Image, Graphics, GameContainer}

class Text(id: Int) extends Entity {
  private var y = -300f
  private var holdTime = 1500
  private val image = id match {
    case 1 => new Image("gfx/part_one.png")
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y < 450) y += delta * 0.2f
    else if (holdTime > 0) holdTime -= delta
    else image.setAlpha(image.getAlpha - delta * 0.0015f)

    if (image.getAlpha <= 0) unlink()
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    image.drawCentered(720, y)
  }
}
