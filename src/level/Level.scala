package level

import org.newdawn.slick.{Graphics, GameContainer}

trait Level {
  def done: Boolean
  def update(implicit gameContainer: GameContainer, delta: Int)
  def render(implicit gameContainer: GameContainer, graphics: Graphics)
}