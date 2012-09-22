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

  var wave = 0

  override def update(delta: Int) {
    levelDelta += delta
    if (gameContainer.getInput.isKeyPressed(Input.KEY_SPACE))
      println(levelDelta)
    while (spawn.headOption.isDefined && spawn.head._1 <= levelDelta)
      enemies.link(spawn.pop()._2)

    if (wave == 1 && levelDelta > 41300L && enemies.getNext.isEmpty) {
      loadWave(2)
      levelDelta = 0L
    }
  }

  override def start() {
    enemies.newLink(None)
    players.newLink(None)
    backgrounds.newLink(Some(new Background(1440, 900, random)))
    loadWave(2)
    for (i <- 0 until gameContainer.getInput.getControllerCount)
      players.link(new Player(gameContainer, 720f, 900f, i))

    levelDelta = 0L
  }

  override def stop() {
    wave = 0
    music.foreach(_.stop())
  }

  private def loadWave(wave: Int) {
    wave match {
      case 1 => {
        this.wave = 1
        spawn push 41201L -> new SaucerBoss
        //        spawn push 38500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
        //        spawn push 38000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
        //        spawn push 37500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
        //        spawn push 37000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)

        //        spawn push 35000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.blue), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
        //        spawn push 34000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
        //        spawn push 33000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
        //        spawn push 32000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)

        //        spawn push 26000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 90f, speedY = 0.2f)
        //        spawn push 26000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 270f, speedY = 0.2f)

//        spawn push 37000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 90f, speedY = 0.2f)
//        spawn push 37000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 270f, speedY = 0.2f)

//        spawn push 30000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 90f, speedY = 0.2f)
//        spawn push 30000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 270f, speedY = 0.2f)

//        spawn push 26000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = 0.3f, bulletSpeed = 5f)
//        spawn push 26000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.3f, bulletSpeed = 5f)

        spawn push 37000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = 0.3f, bulletSpeed = 5f)
        spawn push 37000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = -0.3f, bulletSpeed = 5f)

        spawn push 31500L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 180f, speedX = 0.3f, bulletSpeed = 5f)
        spawn push 31500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red), angle = 180f, speedX = -0.3f, bulletSpeed = 5f)

        spawn push 26000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = 0.3f, bulletSpeed = 5f)
        spawn push 26000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.3f, bulletSpeed = 5f)


        //        spawn push 20500L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = 0.35f, bulletSpeed = 3f)
        //        spawn push 20500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.35f, bulletSpeed = 3f)
        //        spawn push 20500L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 90f, speedY = 0.3f, bulletSpeed = 2f)
        //        spawn push 20500L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 270f, speedY = 0.3f, bulletSpeed = 2f)

        //        spawn push 14100L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 90f, speedY = 0.2f)
        //        spawn push 14100L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 270f, speedY = 0.2f)

//        spawn push 14100L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = 0.3f, bulletSpeed = 5f)
//        spawn push 14100L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.3f, bulletSpeed = 5f)

        spawn push 20000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.blue), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 20000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 270f, speedY = 0.2f, bulletSpeed = 5f)

        spawn push 14000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.yellow), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 14000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 270f, speedY = 0.2f, bulletSpeed = 5f)

        spawn push 8000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 8000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.green), angle = 270f, speedY = 0.2f, bulletSpeed = 5f)

        spawn push 2000L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 2000L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red), angle = 270f, speedY = 0.2f, bulletSpeed = 5f)
        music(0).play()
      }
      case 2 => {
        this.wave = 2
        /*
        14645
        20622
        27535
        30919
        34352
        37753
        41242

        31699
         */

        // 78846

        spawn push 72143L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.white), angle = -90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 72143L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.white), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)

        spawn push 72143L -> new Liner("white", 640f, -20f, colors = IndexedSeq(Color.white), angle = -90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 72143L -> new Liner("white", 800f, -20f, colors = IndexedSeq(Color.white), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)

        spawn push 66130L -> new Liner("gold", 680f, -20f, colors = IndexedSeq(Color.blue), angle = -90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 66130L -> new Liner("white", 760f, -20f, colors = IndexedSeq(Color.blue), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 65830L -> new Liner("gold", 680f, -20f, colors = IndexedSeq(Color.yellow), angle = -90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 65830L -> new Liner("white", 760f, -20f, colors = IndexedSeq(Color.yellow), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 65530L -> new Liner("gold", 680f, -20f, colors = IndexedSeq(Color.green), angle = -90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 65530L -> new Liner("white", 760f, -20f, colors = IndexedSeq(Color.green), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 65230L -> new Liner("gold", 680f, -20f, colors = IndexedSeq(Color.red), angle = -90f, speedY = 0.2f, bulletSpeed = 5f)
        spawn push 65230L -> new Liner("white", 760f, -20f, colors = IndexedSeq(Color.red), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)

        spawn push 59961L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 90f, speedY = 0.2f)
        spawn push 58961L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 270f, speedY = 0.2f)
        spawn push 57961L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 90f, speedY = 0.2f)
        spawn push 56961L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 270f, speedY = 0.2f)
        spawn push 55961L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 90f, speedY = 0.2f)
        spawn push 54961L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 270f, speedY = 0.2f)

        spawn push 49799L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
        spawn push 49299L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
        spawn push 48799L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)
        spawn push 48299L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = -0.5f, bulletSpeed = 5f)

        spawn push 45037L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.blue), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
        spawn push 44037L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
        spawn push 43037L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)
        spawn push 42037L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = 0.5f, bulletSpeed = 5f)

        spawn push 35913L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 90f, speedY = 0.2f)
        spawn push 35913L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red, Color.green, Color.yellow, Color.blue), colorSwitch = 10, angle = 270f, speedY = 0.2f)

        spawn push 31720L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 90f, speedY = 0.3f, bulletSpeed = 2f)
        spawn push 31720L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 270f, speedY = 0.3f, bulletSpeed = 2f)

        spawn push 27392L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 90f, speedY = 0.3f, bulletSpeed = 2f)
        spawn push 27392L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 270f, speedY = 0.3f, bulletSpeed = 2f)

        spawn push 27392L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.red), angle = 90f, speedY = 0.3f, bulletSpeed = 2f)
        spawn push 27392L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.blue), angle = 270f, speedY = 0.3f, bulletSpeed = 2f)

        spawn push 20528L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.green), angle = 180f, speedX = 0.3f, bulletSpeed = 5f)
        spawn push 20528L -> new Liner("white", -20f, -20f, colors = IndexedSeq(Color.blue), angle = 90f, speedY = 0.2f, bulletSpeed = 5f)

        spawn push 13792L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.yellow), angle = 180f, speedX = -0.3f, bulletSpeed = 5f)
        spawn push 13792L -> new Liner("white", 1460f, -20f, colors = IndexedSeq(Color.red), angle = 270f, speedY = 0.2f, bulletSpeed = 5f)

//        spawn push 1200L -> new Liner("gold", -20f, -20f, colors = IndexedSeq(Color.white), angle = -90f, speedY = 0.2f, speedX = 0.2f, bulletSpeed = 5f)
//        spawn push 1200L -> new Liner("gold", 1460f, -20f, colors = IndexedSeq(Color.white), angle = 90f, speedY = 0.2f, speedX = -0.2f, bulletSpeed = 5f)
//        spawn push 900L -> new Liner("gold", -20f, -20f, colors = IndexedSeq(Color.white), angle = -90f, speedY = 0.2f, speedX = 0.2f, bulletSpeed = 5f)
//        spawn push 900L -> new Liner("gold", 1460f, -20f, colors = IndexedSeq(Color.white), angle = 90f, speedY = 0.2f, speedX = -0.2f, bulletSpeed = 5f)
//        spawn push 600L -> new Liner("gold", -20f, -20f, colors = IndexedSeq(Color.white), angle = -90f, speedY = 0.2f, speedX = 0.2f, bulletSpeed = 5f)
//        spawn push 600L -> new Liner("gold", 1460f, -20f, colors = IndexedSeq(Color.white), angle = 90f, speedY = 0.2f, speedX = -0.2f, bulletSpeed = 5f)
//        spawn push 300L -> new Liner("gold", -20f, -20f, colors = IndexedSeq(Color.white), angle = -90f, speedY = 0.2f, speedX = 0.2f, bulletSpeed = 5f)
//        spawn push 300L -> new Liner("gold", 1460f, -20f, colors = IndexedSeq(Color.white), angle = 90f, speedY = 0.2f, speedX = -0.2f, bulletSpeed = 5f)
//        spawn push 0L -> new Liner("gold", -20f, -20f, colors = IndexedSeq(Color.white), angle = -90f, speedY = 0.2f, speedX = 0.2f, bulletSpeed = 5f)
//        spawn push 0L -> new Liner("gold", 1460f, -20f, colors = IndexedSeq(Color.white), angle = 90f, speedY = 0.2f, speedX = -0.2f, bulletSpeed = 5f)

        music(1).play(1, 0.3f)
      }
    }
  }
}
