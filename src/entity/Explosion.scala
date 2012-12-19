package entity

import org.newdawn.slick.{Graphics, GameContainer, Image, Sound}

class Explosion (x: Float, y: Float, scale: Float = 1f) extends Entity {
  private val sound = new Sound("sfx/explosion.wav")
  private val sprites = Vector(
    new Image("gfx/explosion_1.png", false, Image.FILTER_NEAREST).getScaledCopy(scale),
    new Image("gfx/explosion_2.png", false, Image.FILTER_NEAREST).getScaledCopy(scale),
    new Image("gfx/explosion_3.png", false, Image.FILTER_NEAREST).getScaledCopy(scale),
    new Image("gfx/explosion_4.png", false, Image.FILTER_NEAREST).getScaledCopy(scale),
    new Image("gfx/explosion_5.png", false, Image.FILTER_NEAREST).getScaledCopy(scale),
    new Image("gfx/explosion_6.png", false, Image.FILTER_NEAREST).getScaledCopy(scale)
  )
  private var lifeDelta = 0
  sound.play

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (lifeDelta + delta < 300) lifeDelta += delta
    else unlink()
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    sprites(lifeDelta / 50).drawCentered(x, y)
  }
}
