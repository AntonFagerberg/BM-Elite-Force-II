package level

import org.newdawn.slick._
import entity.Background

class Intro extends Level {
  private val music = new Music("msc/intro.ogg")
  private val nama = new Image("gfx/nama.png", false, Image.FILTER_NEAREST)
  private val biiru = new Image("gfx/biiru.png", false, Image.FILTER_NEAREST)
  private val mitsu = new Image("gfx/mitsu.png", false, Image.FILTER_NEAREST)
  private val kudasai = new Image("gfx/kudasai.png", false, Image.FILTER_NEAREST)
  private val bm = new Image("gfx/bm_elite_force.png", false, Image.FILTER_NEAREST)
  private val continue = new Image("gfx/continue.png", false, Image.FILTER_NEAREST)
  private val i = Vector(
    new Image("gfx/i_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/i_2.png", false, Image.FILTER_NEAREST)
  )
  private val creditImages = Vector(
    new Image("gfx/credits_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/credits_2.png", false, Image.FILTER_NEAREST),
    new Image("gfx/credits_3.png", false, Image.FILTER_NEAREST)
  )
  private val background = new Background

  private var index = 0
  private var indexCount = -1
  private var textY = 400f
  private var iiY = 400f
  private var iiX = 440f
  private var bmY = -100f
  private var fadeDelta = 0l
  private var superDelta = 0l
  private var introDone = false

  continue.setAlpha(0f)
  music.play()

  override def done = introDone

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    superDelta += delta

    if (superDelta > 34340)
      background.update

    for (i <- 0 until delta) {
      if (superDelta >= 34340) {
        if (iiY > -100 && superDelta < 43000)
          iiY -= 0.5f

        indexCount -= delta
        if (indexCount <= 0) {
          indexCount = 50
          index = if (index == 0) 1 else 0
        }

        textY += 0.5f
      }

      if (superDelta >= 45041 && superDelta < 60000) {
        iiX = 710f
        if (iiY < 910f) iiY += 0.1f
        else iiY = 910f
      }

      if (superDelta >= 60023) {
        if (bmY <= 200f) bmY += 0.1f
        else bmY = 200f
      }


      if (superDelta >= 65040) {
        if (iiX < 935f)
          iiX = 935f

        if (iiY > 209f) iiY -= 2.5f
        else if (iiY < 209f) iiY = 209f
        else {
          if (superDelta < 600000)
            superDelta = 600000

          fadeDelta = superDelta % 3000

          if (fadeDelta < 500) continue.setAlpha(fadeDelta / 500f)
          else if (fadeDelta > 2500) continue.setAlpha(1 - ((fadeDelta - 2500f) / 500f))
        }
      }
    }

    if (gameContainer.getInput.getControllerCount == 0) {
      introDone = gameContainer.getInput.isKeyDown(Input.KEY_ENTER)
    } else {
      for (i <- 0 until gameContainer.getInput.getControllerCount if gameContainer.getInput.isButtonPressed(4, i))
        introDone = true
    }
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    if (superDelta < 11228)
      creditImages(0).draw(0, 0)

    if (superDelta > 13926 && superDelta < 16661)
      creditImages(1).draw(0, 0)

    if (superDelta > 16661 && superDelta < 19365)
      creditImages(2).draw(0, 0)

    bm.draw(415f, bmY)

    if (superDelta >= 21900)
      nama.draw(150f, textY)

    if (superDelta >= 24613) {
      biiru.draw(400f, textY)
      i(index).draw(iiX, iiY)
      i(index).draw(iiX + 20f, iiY)
    }

    if (superDelta >= 27317)
      mitsu.draw(630f, textY)

    if (superDelta >= 29965)
      kudasai.draw(1030f, textY)

    if (superDelta > 34340)
      background.render

    continue.drawCentered(670f, 320f)
  }
}