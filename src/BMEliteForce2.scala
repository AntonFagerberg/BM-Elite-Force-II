import collection.mutable
import entity._
import org.newdawn.slick._

object BMEliteForce2 extends BasicGame("BM Elite Force II") {
  val enemies = new Linker("Enemies")
  val playerBullets = new Linker("Player Bullets", Some(enemies))
  val players = new Linker("Players", Some(playerBullets))
  val enemyBullets = new Linker("Enemy Bullets")
  val background = new Linker("Background")
  val linkers = Seq(background, enemies, players, enemyBullets, playerBullets)

  def init(gc: GameContainer) {
    background.link(new Background(1440, 900, Settings.random))
    players.link(new Player(gc, 500, 600))
    enemies.link(new Asteroid("lava", Settings.random))
    enemies.link(new Asteroid("lava", Settings.random))
    enemies.link(new Asteroid("lava", Settings.random))
    enemies.link(new Asteroid("lava", Settings.random))
    enemies.link(new Asteroid("lava", Settings.random))
  }

  def update(gc: GameContainer, delta: Int) {
    linkers.foreach(_.update(delta))
    if (gc.getInput.isKeyDown(Input.KEY_ESCAPE)) gc.exit()
  }

  def render(gc: GameContainer, g: Graphics) {
    linkers.foreach(_.render())
  }

  def main(args: Array[String]) {
    val gameContainer = new AppGameContainer(new ScalableGame(this, 1440, 900, true))
    gameContainer.setDisplayMode(Settings.width, Settings.height, Settings.fullScreen)
    gameContainer.start()
  }
}

class GameMaster(enemies: Linker) {
  var superDelta = 0l
  val spawn = new mutable.ArrayStack[(Long, Entity)]
  spawn push 100L -> new Asteroid("lava", Settings.random)
  spawn push 200L -> new Asteroid("lava", Settings.random)
  spawn push 300L -> new Asteroid("lava", Settings.random)

  def update(delta: Int) {
    superDelta += delta
    while (spawn.headOption.isDefined && spawn.head._1 <= superDelta)
      enemies.link(spawn.head._2)
  }
}