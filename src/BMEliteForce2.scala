import collection.mutable
import entity._
import org.lwjgl.{LWJGLUtil, LWJGLException}
import org.newdawn.slick._

object BMEliteForce2 extends BasicGame("BM Elite Force II") {
  val enemyBullets = new Linker("Enemy Bullets")
  val enemies = new Linker("Enemies", Some(enemyBullets))
  val playerBullets = new Linker("Player Bullets", Some(enemies))
  val players = new Linker("Players", Some(playerBullets))
  enemyBullets.setReference(players)
  val background = new Linker("Background")
  val linkers = Seq(background, enemies, players, enemyBullets, playerBullets)
  val level = new Level(enemies)

  def init(gc: GameContainer) {
    background.link(new Background(1440, 900, Settings.random))
    players.link(new Player(gc, 500, 600))
    level.one()
  }

  def update(gc: GameContainer, delta: Int) {
    linkers.foreach(_.update(delta))
    level.update(delta)
    if (gc.getInput.isKeyDown(Input.KEY_ESCAPE))
      gc.setFullscreen(!gc.isFullscreen)
  }

  def render(gc: GameContainer, g: Graphics) {
    linkers.foreach(_.render())
  }

  def main(args: Array[String]) {
//    System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
//    System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
    val gameContainer = new AppGameContainer(new ScalableGame(this, 1440, 900, true))
    gameContainer.setDisplayMode(Settings.width, Settings.height, Settings.fullScreen)
    gameContainer.setUpdateOnlyWhenVisible(false)
    gameContainer.start()
  }
}

class Level(enemies: Linker) {
  var levelDelta = 0L
  val spawn = new mutable.ArrayStack[(Long, Entity)]
  val music = Seq(
    new Music("msc/498935_X-Sentinel---Lift-O.ogg")
  )

  def update(delta: Int) {
    levelDelta += delta
    while (spawn.headOption.isDefined && spawn.head._1 <= levelDelta)
      enemies.link(spawn.pop()._2)
  }

  def one() {
//    spawn push 30000L -> new Asteroid("veins", Settings.random)
//    spawn push 29000L -> new Asteroid("veins", Settings.random)
//    spawn push 28000L -> new Asteroid("veins", Settings.random)
//    spawn push 27000L -> new Asteroid("veins", Settings.random)
//    spawn push 26000L -> new Asteroid("veins", Settings.random)
//    spawn push 25000L -> new Asteroid("veins", Settings.random)
//    spawn push 24000L -> new Asteroid("veins", Settings.random)
//    spawn push 23000L -> new Asteroid("veins", Settings.random)
//    spawn push 22000L -> new Asteroid("veins", Settings.random)
//    spawn push 21000L -> new Asteroid("veins", Settings.random)
//    spawn push 20000L -> new Asteroid("veins", Settings.random)
//    spawn push 19000L -> new Asteroid("veins", Settings.random)
//    spawn push 18000L -> new Asteroid("veins", Settings.random)
//    spawn push 18000L -> new Asteroid("veins", Settings.random)
//    spawn push 16000L -> new Asteroid("veins", Settings.random)
//    spawn push 15000L -> new Asteroid("veins", Settings.random)
//    spawn push 14000L -> new Asteroid("veins", Settings.random)
//    spawn push 13000L -> new Asteroid("veins", Settings.random)
//    spawn push 12000L -> new Asteroid("veins", Settings.random)
//    spawn push 11000L -> new Asteroid("veins", Settings.random)
//    spawn push 10000L -> new Asteroid("veins", Settings.random)
//    spawn push 9000L -> new Asteroid("veins", Settings.random)
//    spawn push 8000L -> new Asteroid("veins", Settings.random)
//    spawn push 7000L -> new Asteroid("veins", Settings.random)
//    spawn push 6000L -> new Asteroid("veins", Settings.random)
//    spawn push 5000L -> new Asteroid("veins", Settings.random)
//    spawn push 4000L -> new Asteroid("veins", Settings.random)
//    spawn push 3000L -> new Asteroid("veins", Settings.random)
//    spawn push 2000L -> new Asteroid("veins", Settings.random)

      spawn push 41201L -> new SaucerBoss(Settings.random)
      spawn push 0L -> new SaucerBoss(Settings.random)

    music(0).play()
    levelDelta = 0L
  }
}