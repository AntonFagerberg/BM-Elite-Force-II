import entity.{Player, Background, Starter, TestBox}
import org.newdawn.slick._

object Main extends BasicGame("BM Elite Force II") {
  val playerStarter = new Starter
  val hostileStarter = new Starter
  val backgroundStarter = new Starter

  def init(gameContainer: GameContainer) {
    new Background(backgroundStarter)
    if (gameContainer.getInput.getControllerCount > 0)
      for (i <- 0 until gameContainer.getInput.getControllerCount)
        playerStarter.link(new Player(gameContainer, hostileStarter, i, 100f, 100f))
    else
      playerStarter.link(new Player(gameContainer, hostileStarter, -1, 100f, 100f))
    hostileStarter.link(new TestBox)
  }

  def update(gameContainer: GameContainer, delta: Int) {
    backgroundStarter.update(gameContainer, delta)
    playerStarter.update(gameContainer, delta)
    hostileStarter.update(gameContainer, delta)
  }

  def render(gameContainer: GameContainer, graphics: Graphics) {
    backgroundStarter.render(gameContainer, graphics)
    playerStarter.render(gameContainer, graphics)
    hostileStarter.render(gameContainer, graphics)
  }

  def main(args: Array[String]) {
    val gameContainer = new AppGameContainer(new ScalableGame(this, 1440, 900, true))
    gameContainer.setDisplayMode(1200, 800, false)
    gameContainer.setUpdateOnlyWhenVisible(false)
    gameContainer.setMouseGrabbed(false)
    gameContainer.start()
  }
}
