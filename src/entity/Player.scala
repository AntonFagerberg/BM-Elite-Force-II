package entity

import org.newdawn.slick.{Color, GameContainer}

class Player(gc: GameContainer, var x: Float, var y: Float, index: Int) extends Entity {
  private val ship = new Ship(gc, index)
  private val speed = 0.6f
  private val input = gc.getInput

  private var velocityX = 0.0f
  private var velocityY = 0.0f
  private var shootDelay = 0.0f
  private var axisY = 0f
  private var axisX = 0f

  override def collision(x: (Float, Float), y: (Float, Float), color: Color): Boolean = {
    if (
        color != ship.getColor
      &&
        ((this.x - ship.hitBoxX) < x._1 && (this.x + ship.hitBoxX) > x._1 || (this.x - ship.hitBoxX) < x._2 && (this.x + ship.hitBoxX) > x._2)
      &&
        ((this.y - ship.hitBoxY) < y._1 && (this.y + ship.hitBoxY) > y._1 || (this.y - ship.hitBoxY) < y._2 && (this.y + ship.hitBoxY) > y._2)
    ) {
//      hitSound.play()

      this.x = 0f
      this.y = 0f
      true
    } else {
      false
    }
  }

  def update(delta: Int, linker: Linker) {
    ship.update(delta)

    if (input.getControllerCount >= index) {
      if (shootDelay < 0f && input.getAxisValue(index, 5) > 0 && linker.reference(0).isDefined) {
        linker.reference(0).get.link(ship.bullet(x, y))
        shootDelay = 2.0f
      }

      axisY = input.getAxisValue(index, 1)
      axisX = input.getAxisValue(index, 0)

      if (axisY > 0.25 || axisY < -0.25)
        velocityY = axisY  * speed

      if (axisX > 0.25 || axisX < -0.25)
        velocityX = axisX * speed

      if (input.isButtonPressed(11, index) || input.getAxisValue(index, 3) > 0.5)
        ship.green()

      if (input.isButtonPressed(12, index) || input.getAxisValue(index, 2) > 0.5)
        ship.red()

      if (input.isButtonPressed(13, index) || input.getAxisValue(index, 2) < -0.5)
        ship.blue()

      if (input.isButtonPressed(14, index) || input.getAxisValue(index, 3) < -0.5)
        ship.yellow()
    }

    for (i <- 0 until delta) {
      if (x + ship.marginX <= 1440 && x - ship.marginX >= 0) {
        x = x + velocityX
        velocityX = if (velocityX < 0.001 && velocityX > -0.001) 0 else velocityX * 0.99f

        if (velocityX > 0.1) ship.right()
        else if (velocityX < -0.1) ship.left()
        else ship.forward()
      } else {
        velocityX = 0
        x = if (x <= ship.marginX) ship.marginX else 1440 - ship.marginX
      }

      if (y + ship.marginY <= 900 && y - ship.marginY >= 0) {
        y = y + velocityY
        velocityY = if (velocityY < 0.001 && velocityY > -0.001) 0 else velocityY * 0.99f
      } else {
        velocityY = 0
        y = if (y <= ship.marginY) ship.marginY else 900 - ship.marginY
      }

      shootDelay = if (shootDelay >= 0) shootDelay - 0.02f else 0f
    }
  }

  def render() {
    ship.draw(x, y)
  }
}