import level.{Continue, Level, Fight, Intro}
import org.newdawn.slick._

object Main extends BasicGame("BM Elite Force II") {
  private var currentLevel: Level = null // Don't blame me, blame Slick / Java.
  private var showContinue = false
  private var deathCount = 0

  def init(gameContainer: GameContainer) {
    currentLevel = new Intro
  }

  def update(gameContainer: GameContainer, delta: Int) {
    if (!currentLevel.done) currentLevel.update(gameContainer, delta)
    else {
      currentLevel =
        if (!showContinue) new Fight(gameContainer)
        else {
          deathCount += 1
          new Continue(deathCount % 50 == 0)
        }

      showContinue = !showContinue
    }

    if (gameContainer.getInput isKeyDown Input.KEY_ESCAPE)
      gameContainer.exit()
  }

  def render(gameContainer: GameContainer, graphics: Graphics) {
    currentLevel.render(gameContainer, graphics)
  }

  def main(args: Array[String]) {
    val gameContainer = new AppGameContainer(new ScalableGame(this, 1440, 900, true))
    gameContainer.setDisplayMode(gameContainer.getScreenWidth, gameContainer.getScreenHeight, true)
    gameContainer.setUpdateOnlyWhenVisible(false)
    gameContainer.setMouseGrabbed(true)
    gameContainer.setShowFPS(false)
    gameContainer.start()
  }
}