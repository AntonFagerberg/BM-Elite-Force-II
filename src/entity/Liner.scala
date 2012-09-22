package entity

import org.newdawn.slick.{Color, Image}

class Liner(kind: String, var x: Float, var y: Float, colors: IndexedSeq[Color] = IndexedSeq(Color.white), colorSwitch: Int = 0, angle: Float = 0f, speedX: Float = 0f, speedY: Float = 0f, speedRotation: Float = 0f, bulletSpeed: Float = 1f) extends Entity {
  private var bulletDelay = 0
  private var angleRotation = 0d
  private var angleCos = 0f
  private var angleSin = 0f
  private var bullets: Option[Linker] = None
  private var colorIndex = 0
  private var colorCounter = 0
  private val sprite = IndexedSeq(
    new Image("gfx/liner_" + kind + "_1.png", false, Image.FILTER_NEAREST).getScaledCopy(2f)
  )

  rotate(angle)

  private def rotate(degrees: Float) {
    sprite.foreach(sprite => sprite.rotate(degrees))
  }

  def update(delta: Int, linker: Linker) {
    for (i <- 0 until delta) {
      rotate(speedRotation)
      x += speedX
      y += speedY
    }

    bulletDelay -= delta

    if (bullets.isEmpty) bullets = linker.reference(0)
    else if (bulletDelay <= 0) {
      bulletDelay = 70 - (12 * bulletSpeed.toInt)
      angleRotation = sprite(0).getRotation * math.Pi / 180d
      angleCos = math.cos(angleRotation).toFloat
      angleSin = math.sin(angleRotation).toFloat
      if (bullets.isDefined) bullets.get.link(new Bullet(x + 20f * angleSin, y - 20f * angleCos, colors(colorIndex), bulletSpeed * angleSin, bulletSpeed * -angleCos, sprite(0).getRotation))
      colorCounter -= 1
      if (colorCounter <= 0) {
        colorCounter += colorSwitch
        colorIndex = (colorIndex + 1) % colors.size
      }
    }

    if (y < -50f || y > 950f || x < - 50f || x > 1490f)
      unlink()
  }

  def render() {
    sprite(0).drawCentered(x, y)
  }

  override def collision(x: (Float, Float), y: (Float, Float), color: Color): Boolean = false
}
