package entity

import org.newdawn.slick._
import geom.Rectangle

class Player(gameContainer: GameContainer, hostileStarter: Entity, controllerIndex: Int, var x: Float, var y: Float) extends Entity {
  private val bulletStarter = new Starter
  private val ship = new Ship
  private val speed = 0.6f
  private var velocityX = 0.0f
  private var velocityY = 0.0f
  private var shootDelay = 0.0f
  private var axisY = 0f
  private var axisX = 0f

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (controllerIndex < gameContainer.getInput.getControllerCount && controllerIndex >= 0) {
      if (shootDelay < 0f && gameContainer.getInput.getAxisValue(controllerIndex, 5) > 0) {
        bulletStarter.link(new Bullet(x + 20, y - 15, ship.getColor))
        bulletStarter.link(new Bullet(x - 20, y - 15, ship.getColor))
        shootDelay = 2.0f
      }

      axisY = gameContainer.getInput.getAxisValue(controllerIndex, 1)
      axisX = gameContainer.getInput.getAxisValue(controllerIndex, 0)

      if (axisY > 0.25 || axisY < -0.25) velocityY = axisY  * speed
      if (axisX > 0.25 || axisX < -0.25) velocityX = axisX * speed

      if (gameContainer.getInput.isButtonPressed(11, controllerIndex) || gameContainer.getInput.getAxisValue(controllerIndex, 3) > 0.5) ship.green()
      if (gameContainer.getInput.isButtonPressed(12, controllerIndex) || gameContainer.getInput.getAxisValue(controllerIndex, 2) > 0.5) ship.red()
      if (gameContainer.getInput.isButtonPressed(13, controllerIndex) || gameContainer.getInput.getAxisValue(controllerIndex, 2) < -0.5) ship.blue()
      if (gameContainer.getInput.isButtonPressed(14, controllerIndex) || gameContainer.getInput.getAxisValue(controllerIndex, 3) < -0.5) ship.yellow()
    } else if (controllerIndex == -1) {
      if (shootDelay < 0f && gameContainer.getInput.isKeyDown(Input.KEY_SPACE)) {
        bulletStarter.link(new Bullet(x, y, ship.getColor))
        bulletStarter.link(new Bullet(x, y, ship.getColor))
        shootDelay = 2.0f
      }

      if (gameContainer.getInput.isKeyDown(Input.KEY_UP)) velocityY = -speed
      else if (gameContainer.getInput.isKeyDown(Input.KEY_DOWN)) velocityY = speed
      if (gameContainer.getInput.isKeyDown(Input.KEY_LEFT)) velocityX = -speed
      else if (gameContainer.getInput.isKeyDown(Input.KEY_RIGHT)) velocityX = speed
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

    ship.update(delta, x, y)
    bulletStarter.update
    updateNext
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    ship.render(graphics, x, y)
    bulletStarter.render
    renderNext
  }

  private class Ship {
//    private trait HitBox {
//      val hitBoxes: List[Rectangle] = IndexedSeq()
//    }

    private class HitBoxSpeeder /*extends HitBox*/ {
      /*override*/ val hitBoxes = List(new Rectangle(x, y, 30, 80), new Rectangle(x, y, 64, 35))

      def getHitBoxes = hitBoxes

      def render(graphics: Graphics) {
        for (hitBox <- hitBoxes) graphics.draw(hitBox)
      }

      def update(x: Float, y: Float) {
        hitBoxes(0).setLocation(x - 15, y - ship.marginY)
        hitBoxes(1).setLocation(x - ship.marginX + 3, y - 17)
      }
    }

    private var spriteChange = 0
    private var spriteIndex = 0
    private var direction = 0
    private var color = Color.green

    private val kind = controllerIndex match {
      case -1 => "speeder"
      case 0 => "speeder"
      case 1 => "speeder"
      case _ => println("Called undefined index '" + controllerIndex + "' in Ship.") ; "speeder"
    }

    private val hitBox = controllerIndex match {
      case _ => new HitBoxSpeeder
    }

    private val shipGreen = IndexedSeq(
      new Image("gfx/green_" + kind + "_F_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/green_" + kind + "_F_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/green_" + kind + "_L_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/green_" + kind + "_L_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/green_" + kind + "_R_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/green_" + kind + "_R_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
    )

    private val shipYellow = IndexedSeq(
      new Image("gfx/yellow_" + kind + "_F_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/yellow_" + kind + "_F_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/yellow_" + kind + "_L_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/yellow_" + kind + "_L_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/yellow_" + kind + "_R_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/yellow_" + kind + "_R_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
    )

    private val shipBlue = IndexedSeq(
      new Image("gfx/blue_" + kind + "_F_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/blue_" + kind + "_F_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/blue_" + kind + "_L_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/blue_" + kind + "_L_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/blue_" + kind + "_R_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/blue_" + kind + "_R_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
    )

    private val shipRed = IndexedSeq(
      new Image("gfx/red_" + kind + "_F_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/red_" + kind + "_F_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/red_" + kind + "_L_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/red_" + kind + "_L_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/red_" + kind + "_R_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
      new Image("gfx/red_" + kind + "_R_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
    )

    private var shipSprites = shipGreen

    val marginX: Float = shipSprites(0).getWidth / 2f
    val marginY: Float = shipSprites(0).getHeight / 2f
//    private val bulletSound = new Sound("sfx/bullet.wav")

    def forward() {
      direction = 0
    }

    def left() {
      direction = 2
    }

    def right() {
      direction = 4
    }

    def green() {
      shipSprites = shipGreen
      color = Color.green
    }

    def red() {
      color = Color.red
      shipSprites = shipRed
    }

    def blue() {
      color = Color.blue
      shipSprites = shipBlue
    }

    def yellow() {
      shipSprites = shipYellow
      color = Color.yellow
    }

    def getColor: Color = color

//    def bullet(x: Float, y: Float): Entity = {
//      bulletSound.play(1.0f, 0.5f)
//      new Bullet(x, y - halfHeight, color)
//    }

    def update(delta: Int, x: Float, y: Float) {
      if (hostileStarter.linkedCollision(hitBox.getHitBoxes, Some(color)) > 0) println("hit!")

      spriteChange += delta
      if (spriteChange > 50) {
        spriteChange = 0
        spriteIndex = if (spriteIndex == 0) 1 else 0
      }
      hitBox.update(x, y)
    }

    def render(graphics: Graphics, x: Float, y: Float) {
      shipSprites(direction + spriteIndex).drawCentered(x, y)
      hitBox.render(graphics)
    }
  }
}
