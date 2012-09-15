import entity._
import org.newdawn.slick._

object BMEliteForce2 extends BasicGame("BM Elite Force II") {
  val enemyBullets = new Linker("Enemy Bullets")
  val enemies = new Linker("Enemies")
  val playerBullets = new Linker("Player Bullets")
  val players = new Linker("Players")
  val background = new Linker("Background")
  val linkers = Seq(background, enemies, players, enemyBullets, playerBullets)
  val level = new Level(Setting.random, background, players, enemies)

  players.addReference(playerBullets)
  playerBullets.addReference(enemies)
  enemies.addReference(enemyBullets)
  enemyBullets.addReference(players)

  def init(gameContainer: GameContainer) {
    level.one(gameContainer)
  }

  def update(gameContainer: GameContainer, delta: Int) {
    linkers.foreach(_.update(delta))
    level.update(delta)

    if (gameContainer.getInput.isKeyDown(Input.KEY_ESCAPE))
      gameContainer.setFullscreen(!gameContainer.isFullscreen)
  }

  def render(gameContainer: GameContainer, graphics: Graphics) {
    linkers.foreach(_.render())
  }

  def main(args: Array[String]) {
//    System.setProperty("org.lwjgl.librarypath", "/Users/anton/Development/Code/BM-Elite-Force-II/lib/native/macosx/")
//    System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"))
    val gameContainer = new AppGameContainer(new ScalableGame(this, 1440, 900, true))
    gameContainer.setDisplayMode(Setting.width, Setting.height, Setting.fullScreen)
    gameContainer.setUpdateOnlyWhenVisible(false)
    gameContainer.setForceExit(false)
    gameContainer.start()
  }
}