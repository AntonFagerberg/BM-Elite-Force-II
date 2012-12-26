package entity

import org.newdawn.slick._
import geom.{Ellipse, Shape, Rectangle}

class Player(gameContainer: GameContainer, enemyStarter: Entity, neutralStarter: Entity, controllerIndex: Int) extends Entity {
  private val bulletSound = new Sound("sfx/bullet.wav")
  private val bulletStarter = new Starter
  private val speed = 0.6f

  private var x = 720f
  private var y = 1400f
  private var velocityX = 0.0f
  private var velocityY = 0.0f
  private var shootDelay = 0.0f
  private var axisY = 0f
  private var axisX = 0f
  private var lives = 3
  private var showHealth = false

  override def collision(implicit hitBoxes: Seq[Shape], color: Color): Boolean = Ship.collision

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (controllerIndex < gameContainer.getInput.getControllerCount && controllerIndex >= 0) {
      axisY = gameContainer.getInput.getAxisValue(controllerIndex, 1)
      axisX = gameContainer.getInput.getAxisValue(controllerIndex, 0)

      if (axisY > 0.25 || axisY < -0.25) velocityY = axisY * speed
      if (axisX > 0.25 || axisX < -0.25) velocityX = axisX * speed

      if (shootDelay < 0f && gameContainer.getInput.getAxisValue(controllerIndex, 5) > 0) Ship.fire()
      if (gameContainer.getInput.isButtonPressed(11, controllerIndex) || gameContainer.getInput.getAxisValue(controllerIndex, 3) > 0.5) Ship.green()
      else if (gameContainer.getInput.isButtonPressed(12, controllerIndex) || gameContainer.getInput.getAxisValue(controllerIndex, 2) > 0.5) Ship.red()
      else if (gameContainer.getInput.isButtonPressed(13, controllerIndex) || gameContainer.getInput.getAxisValue(controllerIndex, 2) < -0.5) Ship.blue()
      else if (gameContainer.getInput.isButtonPressed(14, controllerIndex) || gameContainer.getInput.getAxisValue(controllerIndex, 3) < -0.5) Ship.yellow()

      showHealth = gameContainer.getInput.isButtonPressed(6, controllerIndex)
    } else if (controllerIndex == -1) {

      if (shootDelay < 0f && gameContainer.getInput.isKeyDown(Input.KEY_SPACE)) Ship.fire()
      if (gameContainer.getInput.isKeyDown(Input.KEY_S)) Ship.green()
      else if (gameContainer.getInput.isKeyDown(Input.KEY_D)) Ship.red()
      else if (gameContainer.getInput.isKeyDown(Input.KEY_A)) Ship.blue()
      else if (gameContainer.getInput.isKeyDown(Input.KEY_W)) Ship.yellow()

      if (gameContainer.getInput.isKeyDown(Input.KEY_UP)) velocityY = -speed
      else if (gameContainer.getInput.isKeyDown(Input.KEY_DOWN)) velocityY = speed
      if (gameContainer.getInput.isKeyDown(Input.KEY_LEFT)) velocityX = -speed
      else if (gameContainer.getInput.isKeyDown(Input.KEY_RIGHT)) velocityX = speed

      showHealth = gameContainer.getInput.isKeyDown(Input.KEY_LSHIFT)
    }

    for (i <- 0 until delta) {
      if (x + Ship.getMarginX <= 1440 && x - Ship.getMarginX >= 0) {
        x = x + velocityX
        velocityX = if (velocityX < 0.001 && velocityX > -0.001) 0 else velocityX * 0.99f

        if (velocityX > 0.1) Ship.right()
        else if (velocityX < -0.1) Ship.left()
        else Ship.forward()
      } else {
        velocityX = 0
        x = if (x <= Ship.getMarginX) Ship.getMarginX else 1440 - Ship.getMarginX
      }

      if (y + Ship.getMarginY <= 900 && y - Ship.getMarginY >= 0) {
        y = y + velocityY
        velocityY = if (velocityY < 0.001 && velocityY > -0.001) 0 else velocityY * 0.99f
      } else {
        velocityY = 0
        y = if (y <= Ship.getMarginY) Ship.getMarginY else 900 - Ship.getMarginY
      }

      shootDelay = if (shootDelay >= 0) shootDelay - 0.02f else 0f
    }

    Ship.update(delta)
    bulletStarter.linkedUpdate
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    Ship.render(graphics)
    bulletStarter.linkedRender
  }

  private object Ship {
    private val ShipGreen = Vector(
      new Image("gfx/green_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_F_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/green_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_F_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/green_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_L_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/green_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_L_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/green_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_R_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/green_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_R_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f
    )

    private val ShipYellow = Vector(
      new Image("gfx/yellow_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_F_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/yellow_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_F_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/yellow_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_L_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/yellow_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_L_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/yellow_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_R_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/yellow_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_R_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f
    )

    private val ShipBlue = Vector(
      new Image("gfx/blue_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_F_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/blue_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_F_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/blue_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_L_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/blue_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_L_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/blue_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_R_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/blue_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_R_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f
    )

    private val ShipRed = Vector(
      new Image("gfx/red_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_F_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/red_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_F_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/red_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_L_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/red_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_L_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/red_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_R_1.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f,
      new Image("gfx/red_" + (if (controllerIndex == -1) 1 else controllerIndex + 1) + "_R_2.png", false, Image.FILTER_NEAREST) getScaledCopy 2.5f
    )

    private var shipSprites = ShipGreen

    private val heart = new Image("gfx/heart.png", false, Image.FILTER_NEAREST) getScaledCopy 2f
    private val marginX: Float = shipSprites(0).getWidth / 2f
    private val marginY: Float = shipSprites(0).getHeight / 2f

    private val hitBoxes = controllerIndex match {
      case -1 => Vector(new Rectangle(x - 15, y - marginY, 30, 80), new Rectangle(x - marginX + 3, y - 17, 64, 35))
      case 0 => Vector(new Rectangle(x - 15, y - marginY, 30, 80), new Rectangle(x - marginX + 3, y - 17, 64, 35))
      case 1 => Vector(new Ellipse(x, y, marginX, marginX - 8f))
      case 2 => Vector(new Ellipse(x - 50, y - marginY, 8, marginY - 5), new Ellipse(x - 50, y - marginY, 8, marginY - 5), new Ellipse(x - 50, y - marginY, 8, marginY - 5), new Rectangle(x - marginX + 3, y - 17, marginX, 30))
      case 3 => Vector(new Ellipse(x, y, marginX - 6f, marginY))
    }
    
    private var deadDelta = Int.MinValue
    private var spriteChange = 0
    private var spriteIndex = 0
    private var direction = 0
    private var color = Color.green

    def forward() { direction = 0 }
    def left() { direction = 2 }
    def right() { direction = 4 }

    def getMarginX: Float = marginX
    def getMarginY: Float = marginY

    def green() {
      shipSprites = ShipGreen
      color = Color.green
    }

    def red() {
      color = Color.red
      shipSprites = ShipRed
    }

    def blue() {
      color = Color.blue
      shipSprites = ShipBlue
    }

    def yellow() {
      shipSprites = ShipYellow
      color = Color.yellow
    }

    def fire() {
      bulletSound.play(1f, 0.2f)
      controllerIndex match {
        case -1 => bulletStarter.link(new Bullet(x + 20, y - 15, color, enemyStarter)) ; bulletStarter.link(new Bullet(x - 20, y - 15, color, enemyStarter))
        case 0 => bulletStarter.link(new Bullet(x + 20, y - 15, color, enemyStarter)) ; bulletStarter.link(new Bullet(x - 20, y - 15, color, enemyStarter))
        case 1 => bulletStarter.link(new Bullet(x + 23, y - marginY, color, enemyStarter)) ; bulletStarter.link(new Bullet(x - 23, y - marginY, color, enemyStarter))
        case 2 => bulletStarter.link(new Bullet(x + 30, y - 30, color, enemyStarter)) ; bulletStarter.link(new Bullet(x - 30, y - 30, color, enemyStarter))
        case 3 => bulletStarter.link(new Bullet(x + 20, y - 30, color, enemyStarter)) ; bulletStarter.link(new Bullet(x - 20, y - 30, color, enemyStarter))
      }

      shootDelay = 2.0f
    }

    def getColor: Color = color

    def getHitBoxes: Vector[Shape] = hitBoxes

    def collision(implicit hitBoxes: Seq[Shape], color: Color): Boolean = {
      if (deadDelta > 0 || Ship.getColor == color || !hitBoxes.exists(hitBox => this.hitBoxes exists (_ intersects hitBox))) false
      else {
        explode()
        true
      }
    }

    private def explode() {
      lives -= 1
      neutralStarter.link(new Explosion(x, y, 3f))

      if (lives < 0) unlink()
      else {
        ShipRed foreach (_.setAlpha(0.5f))
        ShipGreen foreach (_.setAlpha(0.5f))
        ShipBlue foreach (_.setAlpha(0.5f))
        ShipYellow foreach (_.setAlpha(0.5f))
        deadDelta = 2500
        x = 720f
        y = 1440f
      }
    }

    def update(delta: Int) {
      if (deadDelta > 0) deadDelta -= delta
      else if (deadDelta != Int.MinValue) {
        ShipGreen foreach (_.setAlpha(1f))
        ShipBlue foreach (_.setAlpha(1f))
        ShipYellow foreach (_.setAlpha(1f))
        ShipRed foreach (_.setAlpha(1f))
        deadDelta = Int.MinValue
      }

      if (deadDelta <= 0 && enemyStarter.linkedCollision(hitBoxes, color) > 0) {
        explode()
      }

      spriteChange += delta
      if (spriteChange > 50) {
        spriteChange = 0
        spriteIndex = if (spriteIndex == 0) 1 else 0
      }

      controllerIndex match {
        case -1 => hitBoxes(0).setLocation(x - 15, y - Ship.marginY) ; hitBoxes(1).setLocation(x - Ship.marginX + 3, y - 17)
        case 0 => hitBoxes(0).setLocation(x - 15, y - Ship.marginY) ; hitBoxes(1).setLocation(x - Ship.marginX + 3, y - 17)
        case 1 => hitBoxes(0).setLocation(x - Ship.marginX, y - Ship.marginY + 19f)
        case 2 => hitBoxes(0).setLocation(x - 38, y - Ship.marginY + 7) ; hitBoxes(1).setLocation(x + 22, y - Ship.marginY + 7) ; hitBoxes(2).setLocation(x - 8, y - Ship.marginY) ; hitBoxes(3).setLocation(x - 20, y - 15)
        case 3 => hitBoxes(0).setLocation(x - marginX + 6f, y - Ship.marginY)
      }
    }

    def render(graphics: Graphics) {
      shipSprites(direction + spriteIndex).drawCentered(x, y)
//      hitBoxes.foreach(graphics.draw(_))
      if (showHealth)
        for (i <- 0 until lives)
          heart.drawCentered(x + 50f + 20f * (i / 3), y - 20f + 20f * i - 60f * (i / 3))
    }
  }
}
