package entity

import org.newdawn.slick.{Color, Image, Graphics, GameContainer}

class Dinosaur extends Entity {
  private val dinosaur = Vector(
    new Image("gfx/dino_1.png", false, Image.FILTER_NEAREST).getScaledCopy(12f),
    new Image("gfx/dino_2.png", false, Image.FILTER_NEAREST).getScaledCopy(12f),
    new Image("gfx/dino_3.png", false, Image.FILTER_NEAREST).getScaledCopy(12f),
    new Image("gfx/dino_4.png", false, Image.FILTER_NEAREST).getScaledCopy(12f),
    new Image("gfx/dino_5.png", false, Image.FILTER_NEAREST).getScaledCopy(12f),
    new Image("gfx/dino_6.png", false, Image.FILTER_NEAREST).getScaledCopy(12f),
    new Image("gfx/dino_7.png", false, Image.FILTER_NEAREST).getScaledCopy(12f),
    new Image("gfx/dino_8.png", false, Image.FILTER_NEAREST).getScaledCopy(12f),
    new Image("gfx/dino_9.png", false, Image.FILTER_NEAREST).getScaledCopy(12f)
  )
  private val colors = Vector(
    Color.green,
    Color.red,
    Color.yellow,
    Color.blue,
    Color.magenta,
    Color.cyan,
    Color.orange,
    Color.pink
  )
  private val merryDinosaurmas = new Image("gfx/merry_dinosaurmas.png")
  private val best = new Image("gfx/best.png")
  private val x = 1100f
  private val y = 640f
  private val yBest = 120f
  private var yBestSin = 0f
  private var angle = 0d
  private var spriteChange = 0
  private var colorChange = 0

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    angle += delta * 0.003d
    yBestSin = 40f * math.sin(angle).toFloat
    spriteChange = (spriteChange + delta) % 2899
    colorChange = (colorChange + delta) % 799
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    best.draw(720f - best.getWidth / 2f, yBest + yBestSin, colors(colorChange / 100))
    if (spriteChange < 600) dinosaur(spriteChange / 100).drawCentered(x, y)
    else if (spriteChange >= 2600) dinosaur((spriteChange - 2000) / 100).drawCentered(x, y)
    else {
      dinosaur(5).drawCentered(x, y)
      merryDinosaurmas.drawCentered(300f, 700f)
    }
  }
}
