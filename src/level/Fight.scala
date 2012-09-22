package level

import entity.{Background, Entity, Linker}
import collection.mutable
import org.newdawn.slick.{GameContainer, Color, Input, Music}
import util.Random
import entity._

class Fight(gameContainer: GameContainer, random: Random, backgrounds: Linker, players: Linker, enemies: Linker) extends Level {
  val music = mutable.IndexedSeq(
    new Music("msc/498935_X-Sentinel---Lift-O.ogg"),
    new Music("msc/469781_XS-amp-GS---Game-Ov.ogg")
  )

  override def update(delta: Int) {
    levelDelta += delta
    if (gameContainer.getInput.isKeyPressed(Input.KEY_SPACE))
      println(levelDelta)
    while (spawn.headOption.isDefined && spawn.head._1 <= levelDelta)
      enemies.link(spawn.pop()._2)

    if (levelDelta > 41300L && enemies.getNext.isEmpty) {
      loadWave(2)
      levelDelta = 0L
    }
  }

  override def start() {
    enemies.newLink(None)
    players.newLink(None)
    backgrounds.newLink(Some(new Background(1440, 900, random)))
    loadWave(1)
    for (i <- 0 until gameContainer.getInput.getControllerCount)
      players.link(new Player(gameContainer, 720f, 900f, i))

    levelDelta = 0L
  }

  override def stop() {
    music.foreach(_.stop())
  }

  private def loadWave(wave: Int) {
    wave match {
      case 1 => {
        spawn push 41201L -> new SaucerBoss
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
        music(0).play()
      }
      case 2 => {
        /*
        28376
        31884
        35301
        38622
        42079
        45527
        48898

        54963 <-- big wave
         */
        spawn push 41201L -> new SaucerBoss
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
      }
    }
  }
}
