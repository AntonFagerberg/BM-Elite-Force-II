import entity.{Player, Background, Starter, TestBox}
import level.{Level, Fight, Intro}
import org.newdawn.slick._

object Main extends BasicGame("BM Elite Force II") {
  private var currentLevel: Level = null

  def init(gameContainer: GameContainer) {
    currentLevel = new Fight(gameContainer)
//    currentLevel = new Intro
  }

  def update(gameContainer: GameContainer, delta: Int) {
    currentLevel.update(gameContainer, delta)
  }

  def render(gameContainer: GameContainer, graphics: Graphics) {
    currentLevel.render(gameContainer, graphics)
  }

  def main(args: Array[String]) {
    val gameContainer = new AppGameContainer(new ScalableGame(this, 1440, 900, true))
    gameContainer.setDisplayMode(1920, 1200, true)
    gameContainer.setUpdateOnlyWhenVisible(false)
    gameContainer.setMouseGrabbed(true)
    gameContainer.setShowFPS(false)
    gameContainer.start()
  }
}
