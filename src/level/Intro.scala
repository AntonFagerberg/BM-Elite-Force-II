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
  private val continue = new Image("gfx/continue.png")
  private val background = new Background
  private val i = Vector(
    new Image("gfx/i_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/i_2.png", false, Image.FILTER_NEAREST)
  )
  private val creditImages = Vector(
    new Image("gfx/credits_1.png"),
    new Image("gfx/credits_2.png"),
    new Image("gfx/credits_3.png")
  )
  private var index = 0
  private var indexCount = -1
  private var textY = 400f
  private var iY = 400f
  private var iX = 440f
  private var bmY = -100f
  private var introDone = false
  private var fadeDelta = 0l
  private var levelDelta = 0L

  continue.setAlpha(0f)
  music.play()

  override def done = introDone

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    levelDelta += delta

    if (levelDelta > 34340)
      background.update

    for (i <- 0 until delta) {
      if (levelDelta >= 34340) {
        if (iY > -100 && levelDelta < 43000)
          iY -= 0.5f

        indexCount -= delta
        if (indexCount <= 0) {
          indexCount = 50
          index = if (index == 0) 1 else 0
        }

        textY += 0.5f
      }

      if (levelDelta >= 45041 && levelDelta < 60000) {
        iX = 710f
        if (iY < 910f) iY += 0.1f
        else iY = 910f
      }

      if (levelDelta >= 60023) {
        if (bmY <= 200f) bmY += 0.1f
        else bmY = 200f
      }


      if (levelDelta >= 65040) {
        if (iX < 935f)
          iX = 935f

        if (iY > 209f) iY -= 2.5f
        else if (iY < 209f) iY = 209f
        else {
          if (levelDelta < 600000)
            levelDelta = 600000

          fadeDelta = levelDelta % 3000
          if (fadeDelta < 500) continue.setAlpha(fadeDelta / 500f)
          else if (fadeDelta > 2500) continue.setAlpha(1 - ((fadeDelta - 2500f) / 500f))
        }
      }
    }

    if (gameContainer.getInput.getControllerCount == 0) {
      if (gameContainer.getInput.isKeyDown(Input.KEY_ENTER))
        introDone = true
    } else {
      for (i <- 0 until gameContainer.getInput.getControllerCount if gameContainer.getInput.isButtonPressed(4, i))
        introDone = true
    }
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    if (levelDelta < 11228)
      creditImages(0).draw(0, 0)

    if (levelDelta > 13926 && levelDelta < 16661)
      creditImages(1).draw(0, 0)

    if (levelDelta > 16661 && levelDelta < 19365)
      creditImages(2).draw(0, 0)

    bm.draw(415f, bmY)

    if (levelDelta >= 21900)
      nama.draw(150f, textY)

    if (levelDelta >= 24613) {
      biiru.draw(400f, textY)
      i(index).draw(iX, iY)
      i(index).draw(iX + 20f, iY)
    }

    if (levelDelta >= 27317)
      mitsu.draw(630f, textY)

    if (levelDelta >= 29965)
      kudasai.draw(1030f, textY)

    if (levelDelta > 34340)
      background.render

    continue.drawCentered(670f, 320f)
  }
}