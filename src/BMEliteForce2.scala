import entity._
import org.newdawn.slick._

object BMEliteForce2 extends BasicGame("BM Elite Force II") {
  val mainLinker = new Linker
  var currentEntity: Option[Entity] = None

  def init(gc: GameContainer) {
    mainLinker.initiate(new Player(gc, 500, 600))
    mainLinker.link(new Background(gc.getWidth, gc.getHeight, Settings.random))
  }

  def update(gc: GameContainer, delta: Int) {
    mainLinker.update(delta)
    if (gc.getInput.isKeyDown(Input.KEY_ESCAPE)) gc.exit()
  }

  def render(gc: GameContainer, g: Graphics) {
    mainLinker.render()
  }

  def main(args: Array[String]) {
    val gameContainer = new AppGameContainer(this)
    gameContainer.setDisplayMode(Settings.width, Settings.height, Settings.fullScreen)
    gameContainer.start()
  }
}
