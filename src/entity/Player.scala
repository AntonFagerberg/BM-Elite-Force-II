package entity

import org.newdawn.slick.{Input, Image, GameContainer}
import collection.mutable.ArrayBuffer

class Player(gc: GameContainer, var x: Float, var y: Float) extends Entity {
  val ship = new Ship(gc, "green")
  ship.setSpeeder()

  private var velocityX = 0.0f
  private var velocityY = 0.0f
  private var shootDelay = 0.0f

  def update(delta: Int) {
    controls()
    ship.update(delta)

    if (x + ship.xMargin <= gc.getWidth && x - ship.xMargin >= 0) {
      x = x + velocityX * delta
      velocityX *= 0.99f
    } else {
      velocityX = 0
      x = if (x <= ship.xMargin) ship.xMargin else gc.getWidth - ship.xMargin
    }

    if (y + ship.yMargin <= gc.getHeight && y - ship.yMargin >= 0) {
      y = y + velocityY * delta
      velocityY *= 0.99f
    } else {
      velocityY = 0
      y = if (y <= ship.yMargin) ship.yMargin else gc.getHeight - ship.yMargin
    }

    shootDelay = if (shootDelay >= 0) shootDelay - 0.02f * delta else 0f
  }

  private def controls() {
    if (gc.getInput.isKeyDown(Input.KEY_UP) && velocityY > -0.4f) velocityY -= 0.009f
    if (gc.getInput.isKeyDown(Input.KEY_DOWN) && velocityY < 0.4f) velocityY += 0.009f
    if (gc.getInput.isKeyDown(Input.KEY_LEFT) && velocityX > -0.4f) velocityX -= 0.009f
    if (gc.getInput.isKeyDown(Input.KEY_RIGHT) && velocityX < 0.4f) velocityX += 0.009f

    if (gc.getInput.isKeyDown(Input.KEY_SPACE) && shootDelay <= 0) {
      link(ship.bullet(x, y))
      shootDelay = 2.0f
    }
  }

  def render() {
    ship.draw(x, y)
  }
}
