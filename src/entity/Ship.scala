package entity

import scala.Array
import org.newdawn.slick.{Sound, GameContainer, Image, Color}
import collection.mutable.ArrayBuffer

class Ship(gc: GameContainer, kind: String = "speeder") {
  private var spriteChange = 0
  private var spriteIndex = 0
  private var direction = 0

  private var color = Color.green

  def xMargin = halfWidth
  def yMargin = halfHeight

  private val shipGreen = Array(
    new Image("gfx/green_" + kind + "_F_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/green_" + kind + "_F_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/green_" + kind + "_L_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/green_" + kind + "_L_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/green_" + kind + "_R_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/green_" + kind + "_R_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
  )

  private val shipYellow = Array(
    new Image("gfx/yellow_" + kind + "_F_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/yellow_" + kind + "_F_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/yellow_" + kind + "_L_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/yellow_" + kind + "_L_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/yellow_" + kind + "_R_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/yellow_" + kind + "_R_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
  )

  private val shipBlue = Array(
    new Image("gfx/blue_" + kind + "_F_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/blue_" + kind + "_F_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/blue_" + kind + "_L_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/blue_" + kind + "_L_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/blue_" + kind + "_R_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/blue_" + kind + "_R_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
  )

  private val shipRed = Array(
    new Image("gfx/red_" + kind + "_F_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/red_" + kind + "_F_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/red_" + kind + "_L_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/red_" + kind + "_L_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/red_" + kind + "_R_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f),
    new Image("gfx/red_" + kind + "_R_2.png", false, Image.FILTER_NEAREST).getScaledCopy(2.5f)
  )

  private var shipSprites = shipGreen
  private val halfWidth = shipGreen.head.getWidth / 2
  private val halfHeight = shipGreen.head.getHeight / 2

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

  val bulletSound = new Sound("sfx/bullet.wav")

  def bullet(x: Float, y: Float): Entity = {
    bulletSound.play(1.0f, 0.5f)
    new Bullet(x, y - halfHeight, color)
  }

  def update(delta: Int) {
    spriteChange += delta
    if (spriteChange > 50) {
      spriteChange = 0
      spriteIndex = if (spriteIndex == 0) 1 else 0
    }
  }

  def forward() { direction = 0 }
  def left() { direction = 1 }
  def right() { direction = 2 }

  def draw(x: Float, y: Float) {
    shipSprites((direction * 2) + spriteIndex).drawCentered(x, y)
  }
}