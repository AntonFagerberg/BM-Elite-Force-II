package level

import org.newdawn.slick._
import entity.{Starter, Background}

class Intro extends Level {
  private val music = new Music("msc/intro.ogg")
  private val nama = new Image("gfx/nama.png", false, Image.FILTER_NEAREST)
  private val biiru = new Image("gfx/biiru.png", false, Image.FILTER_NEAREST)
  private val mitsu = new Image("gfx/mitsu.png", false, Image.FILTER_NEAREST)
  private val kudasai = new Image("gfx/kudasai.png", false, Image.FILTER_NEAREST)
  private val bm = new Image("gfx/bm_elite_force.png", false, Image.FILTER_NEAREST)
  private val background = new Background
  private val i = IndexedSeq(
    new Image("gfx/i_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/i_2.png", false, Image.FILTER_NEAREST)
  )
  private var index = 0
  private var indexCount = -1
  private var textY = 400f
  private var iY = 400f
  private var iX = 440f
  private var bmY = -100f

  music.play()
  private var levelDelta = 0L

  def update(implicit gameContainer: GameContainer, delta: Int) {
    levelDelta += delta

    if (levelDelta > 36815)
      background.linkedUpdate

    for (i <- 0 until delta) {
      if (levelDelta >= 36815) {
        if (iY > -100 && levelDelta < 43000)
          iY -= 0.5f

        indexCount -= delta
        if (indexCount <= 0) {
          indexCount = 50
          index = if (index == 0) 1 else 0
        }

        textY += 0.5f
      }

      if (levelDelta >= 50041 && levelDelta < 62000) {
        iX = 710f
        if (iY < 910)
          iY += 0.1f
      }

      if (levelDelta >= 62023) {
        if (bmY <= 200f)
          bmY += 0.1f
      }

      if (levelDelta >= 67216) {
        if (iX < 935f)
          iX = 935f

        if (iY > 209f)
          iY -= 1.5f
        else if (iY < 209f)
          iY = 209f
      }

    }
  }

  def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    bm.draw(415f, bmY)

    if (levelDelta >= 24432)
      nama.draw(150f, textY)

    if (levelDelta >= 27157) {
      biiru.draw(400f, textY)
      i(index).draw(iX, iY)
      i(index).draw(iX + 20f, iY)
    }

    if (levelDelta >= 29861)
      mitsu.draw(630f, textY)

    if (levelDelta >= 32494)
      kudasai.draw(1030f, textY)

    if (levelDelta > 36815)
      background.linkedRender
  }
}