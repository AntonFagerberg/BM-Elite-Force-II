package level

import org.newdawn.slick._
import entity.Background

class Continue(var youCanDoIt: Boolean = false) extends Level {
  private val gameOver = new Image("gfx/game_over.png")
  private val continue = new Image("gfx/continue.png")
  private val background = new Background(Color.pink)
  private val music =
    if (youCanDoIt) new Music("msc/38773_newgrounds_youcan.ogg", true)
    else new Music("msc/481979_Tangerine.ogg", true)

  private var continueDone = false
  private var superDelta = 0l
  private var fadeDelta = 0l

  music.play()

  override def done = continueDone

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    superDelta += delta

    fadeDelta = superDelta % 3000
    if (fadeDelta < 500) continue.setAlpha(fadeDelta / 500f)
    else if (fadeDelta > 2500) continue.setAlpha(1 - ((fadeDelta - 2500f) / 500f))

    background.update

    for (i <- 0 until gameContainer.getInput.getControllerCount if gameContainer.getInput.isButtonPressed(4, i) && (!youCanDoIt || superDelta > 10000))
      continueDone = true
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    gameOver.drawCentered(720f, 340f)
    continue.drawCentered(720f, 400f)
    background.render
  }
}