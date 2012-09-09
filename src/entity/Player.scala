package entity

import org.newdawn.slick.{Input, Image, GameContainer}
import collection.mutable.ArrayBuffer

class Player(gc: GameContainer, var x: Float, var y: Float) extends Entity {
  val ship = new Ship(gc, "green")
  ship.setSpeeder()

  private val speed = 0.6f
  private var velocityX = 0.0f
  private var velocityY = 0.0f
  private var shootDelay = 0.0f

  override def collision(x: (Float, Float), y: (Float, Float)): Boolean = {
    false
  }

  def update(delta: Int, linker: Linker) {
    if (linker.getReference.isDefined) controls(bulletLinker = linker.getReference.get)
    else sys.error("Could not get reference linker in player.")

    ship.update(delta)

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

  private def controls(bulletLinker: Linker) {
    if (!(gc.getInput.isKeyDown(Input.KEY_UP) && gc.getInput.isKeyDown(Input.KEY_DOWN))) {
      if (gc.getInput.isKeyDown(Input.KEY_UP)) velocityY = -speed
      if (gc.getInput.isKeyDown(Input.KEY_DOWN)) velocityY = speed
    }

    if (!(gc.getInput.isKeyDown(Input.KEY_LEFT) && gc.getInput.isKeyDown(Input.KEY_RIGHT))) {
      if (gc.getInput.isKeyDown(Input.KEY_LEFT)) velocityX =  -speed
      if (gc.getInput.isKeyDown(Input.KEY_RIGHT)) velocityX = speed
    }

    if (gc.getInput.isKeyDown(Input.KEY_SPACE) && shootDelay <= 0) {
      bulletLinker.link(ship.bullet(x, y))
      shootDelay = 2.0f
    }
  }

  def render() {
    ship.draw(x, y)
  }
}