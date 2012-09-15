package entity

import collection.mutable
import org.newdawn.slick.{Color, Input, GameContainer, Music}
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
      players.link(new Player(gameContainer, 720f, 900f, i))
  }

  def test(gameContainer: GameContainer) {
    clearLevel(gameContainer)
    backgrounds.newLink(Some(new Background(1440, 900, random)))
    spawn push 0L -> new SaucerBoss
    levelDelta = 0L
  }

  def one(gameContainer: GameContainer) {
    gc = gameContainer
    clearLevel(gameContainer)
    backgrounds.newLink(Some(new Background(1440, 900, random)))

    spawn push 41201L -> new SaucerBoss

    // 27746
    spawn push 38500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
    spawn push 38000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
    spawn push 37500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
    spawn push 37000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)

    spawn push 35000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.blue), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
    spawn push 34000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
    spawn push 33000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
    spawn push 32000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)

    spawn push 26000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 90f, speedY = 0.2f)
    spawn push 26000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 270f, speedY = 0.2f)

    spawn push 20500L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = 0.35f, bulletSpeed = 3f)
    spawn push 20500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.35f, bulletSpeed = 3f)
    spawn push 20500L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 90f, speedY = 0.3f, bulletSpeed = 2f)
    spawn push 20500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 270f, speedY = 0.3f, bulletSpeed = 2f)

    spawn push 14100L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 90f, speedY = 0.2f)
    spawn push 14100L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 270f, speedY = 0.2f)

    spawn push 8000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = 0.3f, bulletSpeed = 5f)
    spawn push 8000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.3f, bulletSpeed = 5f)

    spawn push 1500L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)
    spawn push 1500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red), angle = 270f, speedY = 0.2f, bulletSpeed = 5f)

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