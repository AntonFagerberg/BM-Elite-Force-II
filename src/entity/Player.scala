package entity

import org.newdawn.slick.{Input, Image, GameContainer}
import collection.mutable.ArrayBuffer

class Player(gc: GameContainer, var x: Float, var y: Float) extends Entity {
  val ship = new Ship(gc, "green")
  ship.setSpeeder()

  private var speedX = 1f
  private var speedY = 1f
  private var accelerationX = 0.009f
  private var accelerationY = 0.2f
  private var velocityX = 0.0f
  private var velocityY = 0.0f
  private var shootDelay = 0.0f

  def update(delta: Int, linker: Linker) {
    if (linker.getReference.isDefined) controls(bulletLinker = linker.getReference.get)
    else sys.error("Could not get reference linker in player.")

    ship.update(delta)

    if (x + ship.xMargin <= 1440 && x - ship.xMargin >= 0) {
      x = x + velocityX * delta
      velocityX *= 0.99f
    } else {
      velocityX = 0
      x = if (x <= ship.xMargin) ship.xMargin else 1440 - ship.xMargin
    }

    println(velocityX)

    if (y + ship.yMargin <= 900 && y - ship.yMargin >= 0) {
      y = y + velocityY * delta
      velocityY *= 0.99f
    } else {
      velocityY = 0
      y = if (y <= ship.yMargin) ship.yMargin else 900 - ship.yMargin
    }

    if (velocityX >= -0.2f && velocityX <= 0.2f) ship.forward()
    else if (velocityX < 0) ship.left()
    else ship.right()

    shootDelay = if (shootDelay >= 0) shootDelay - 0.02f * delta else 0f
  }

  private def controls(bulletLinker: Linker) {
    if (gc.getInput.isKeyDown(Input.KEY_UP)) velocityY -= speedY
    if (gc.getInput.isKeyDown(Input.KEY_DOWN)) velocityY += speedY
    if (gc.getInput.isKeyDown(Input.KEY_LEFT)) velocityX = if (velocityX > -speedX) velocityX - accelerationX else -speedX
    if (gc.getInput.isKeyDown(Input.KEY_RIGHT)) velocityX = if (velocityX < speedX) velocityX + accelerationX else speedX

    if (gc.getInput.isKeyDown(Input.KEY_SPACE) && shootDelay <= 0) {
      bulletLinker.link(ship.bullet(x, y))
      shootDelay = 2.0f
    }
  }

  def render() {
    ship.draw(x, y)
  }
}