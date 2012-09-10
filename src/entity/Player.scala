package entity

import org.newdawn.slick.{Input, Image, GameContainer}
import collection.mutable.ArrayBuffer

class Player(gc: GameContainer, var x: Float, var y: Float) extends Entity {
  val ship = new Ship(gc, "speeder")

  private val speed = 0.6f
  private var velocityX = 0.0f
  private var velocityY = 0.0f
  private var shootDelay = 0.0f
  private val input = gc.getInput

  private var axisY = 0f
  private var axisX = 0f

  override def collision(x: (Float, Float), y: (Float, Float)): Boolean = {
    false
  }

  def update(delta: Int, linker: Linker) {
    ship.update(delta)

    if (!(gc.getInput.isKeyDown(Input.KEY_UP) && gc.getInput.isKeyDown(Input.KEY_DOWN))) {
      if (gc.getInput.isKeyDown(Input.KEY_UP)) velocityY = -speed
      if (gc.getInput.isKeyDown(Input.KEY_DOWN)) velocityY = speed
    }

    if (!(gc.getInput.isKeyDown(Input.KEY_LEFT) && gc.getInput.isKeyDown(Input.KEY_RIGHT))) {
      if (gc.getInput.isKeyDown(Input.KEY_LEFT)) velocityX =  -speed
      if (gc.getInput.isKeyDown(Input.KEY_RIGHT)) velocityX = speed
    }

    if ((gc.getInput.isKeyDown(Input.KEY_SPACE) || (input.getControllerCount > 0 && input.getAxisValue(0, 5) > 0)) && shootDelay <= 0) {
      linker.getReference.get.link(ship.bullet(x, y))
      shootDelay = 2.0f
    }

    if (input.getControllerCount > 0) {
      axisY = input.getAxisValue(0, 1)
      axisX = input.getAxisValue(0, 0)

      if (axisY > 0.25 || axisY < -0.25)
        velocityY = axisY  * speed

      if (axisX > 0.25 || axisX < -0.25)
        velocityX = axisX * speed

      if (input.isButtonPressed(11, 0))
        ship.green()

      if (input.isButtonPressed(12, 0))
        ship.red()

      if (input.isButtonPressed(13, 0))
        ship.blue()

      if (input.isButtonPressed(14, 0))
        ship.yellow()
    }


    if (x + ship.xMargin <= 1440 && x - ship.xMargin >= 0) {
      for (i <- 0 until delta)
        x = x + velocityX

      if (velocityX < 0.1 && velocityX > -0.1) {
        ship.forward()
        velocityX = 0
      } else {
        if (velocityX < 0) ship.left()
        else ship.right()
        for (i <- 0 until delta)
          velocityX *= 0.992f
      }
    } else {
      velocityX = 0
      x = if (x <= ship.xMargin) ship.xMargin else 1440 - ship.xMargin
    }

    if (y + ship.yMargin <= 900 && y - ship.yMargin >= 0) {
      for (i <- 0 until delta) {
        y = y + velocityY
        velocityY *= 0.99f
      }
    } else {
      velocityY = 0
      y = if (y <= ship.yMargin) ship.yMargin else 900 - ship.yMargin
    }


    for (i <- 0 until delta)
      shootDelay = if (shootDelay >= 0) shootDelay - 0.02f else 0f
  }

  def render() {
    ship.draw(x, y)
  }
}