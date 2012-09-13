package entity

import collection.mutable
import org.newdawn.slick.{Input, GameContainer, Music}
import util.Random

class Level(random: Random, backgrounds: Linker, players: Linker, enemies: Linker) {
  var levelDelta = 0L
  val spawn = new mutable.ArrayStack[(Long, Entity)]
  val music = mutable.IndexedSeq(
    new Music("msc/intro.ogg"),
    new Music("msc/498935_X-Sentinel---Lift-O.ogg")
  )

  var gc: GameContainer = null

  def update(delta: Int) {
    levelDelta += delta
    if (gc != null && gc.getInput.isKeyPressed(Input.KEY_SPACE))
      println(levelDelta)
    while (spawn.headOption.isDefined && spawn.head._1 <= levelDelta)
      enemies.link(spawn.pop()._2)
  }

  private def clearLevel(gameContainer: GameContainer) {
    enemies.newLink(None)
    players.newLink(None)
    spawn.clear()
    music.foreach(_.stop())
    for (i <- 0 until gameContainer.getInput.getControllerCount)
      players.link(new Player(gameContainer, 0f, 0f, i))
  }

  def test(gameContainer: GameContainer) {
    clearLevel(gameContainer)
    backgrounds.newLink(Some(new Background(1440, 900, random)))
    spawn push 0L -> new SaucerBoss
    levelDelta = 0L
  }

  def one(gameContainer: GameContainer) {
    clearLevel(gameContainer)
    backgrounds.newLink(Some(new Background(1440, 900, random)))

    spawn push 41201L -> new SaucerBoss

    music(1).play()
    levelDelta = 0L
  }

  def intro(gameContainer: GameContainer) {
    spawn push 34400L -> new Background(1440, 900, random)
    gc = gameContainer
    spawn push 0L -> Intro
    music(0).play()
    levelDelta = 0L
  }
}