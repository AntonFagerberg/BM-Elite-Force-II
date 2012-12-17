import entity.{Player, Background, Starter}
import level.{Continue, Level, Fight, Intro}
import org.newdawn.slick._

object Main extends BasicGame("BM Elite Force II") {
  private var currentLevel: Level = null // Don't blame me, blame Slick / Java.
  private var play = false
  private var deathCount = 0

  def init(gameContainer: GameContainer) {
    currentLevel = new Intro
  }

  def update(gameContainer: GameContainer, delta: Int) {
    if (currentLevel.done) {
      currentLevel =
        if (!play) new Fight(gameContainer)
        else {
          deathCount += 1
          new Continue(deathCount % 50 == 0)
        }

      play = !play
    }

    currentLevel.update(gameContainer, delta)
  }

  def render(gameContainer: GameContainer, graphics: Graphics) {
    currentLevel.render(gameContainer, graphics)
  }

  def main(args: Array[String]) {
    val gameContainer = new AppGameContainer(new ScalableGame(this, 1440, 900, true))
//    gameContainer.setDisplayMode(1920, 1200, true)
    gameContainer.setDisplayMode(1440, 900, false)
    gameContainer.setUpdateOnlyWhenVisible(false)
    gameContainer.setMouseGrabbed(true)
    gameContainer.setShowFPS(false)
    gameContainer.start()
  }
}
