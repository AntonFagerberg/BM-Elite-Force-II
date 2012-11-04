package level

import collection.mutable
import org.newdawn.slick._
import entity._

class Fight(gameContainer: GameContainer) extends Level {
  private val playerStarter = new Starter
  private val enemyStarter = new Starter
  private val backgroundStarter = new Starter
  private val neutralStarter = new Starter
  private val music = Vector(
    new Music("msc/498935_X-Sentinel---Lift-O.ogg"),
    new Music("msc/469781_XS-amp-GS---Game-Ov.ogg")
  )
  private val enemyStack = Vector(
    new collection.mutable.Stack[(Long, Entity)],
    new collection.mutable.Stack[(Long, Entity)]
  )
  private var levelPart = 0
  private var superDelta = 0l

  private val checkPoints = mutable.ArrayBuffer(
    false,
    false,
    false
  )

  initialize()

  private def initialize() {
    backgroundStarter.link(new Background)
    neutralStarter.link(new Text(1))

    if (gameContainer.getInput.getControllerCount > 0)
      for (i <- 0 until gameContainer.getInput.getControllerCount)
        playerStarter.link(new Player(gameContainer, enemyStarter, neutralStarter, i))
    else
      playerStarter.link(new Player(gameContainer, enemyStarter, neutralStarter, -1))

//    /*
    enemyStack(0) push 41201l -> new SaucerBoss(playerStarter, neutralStarter)

    enemyStack(0) push 31500l -> new Liner(680f, -50f, Vector(Color.white), playerStarter, neutralStarter, angle = -90f, speedY = 0.15f)
    enemyStack(0) push 31500l -> new Liner(760f, -50f, Vector(Color.white), playerStarter, neutralStarter, angle = 90f, speedY = 0.15f, destroyable = true)

    enemyStack(0) push 25000l -> new Liner(1430f, -50f, Vector(Color.blue), playerStarter, neutralStarter, angle = -90f, speedY = 0.2f)
    enemyStack(0) push 24000l -> new Liner(1430f, -50f, Vector(Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = 0.2f)
    enemyStack(0) push 23000l -> new Liner(1430f, -50f, Vector(Color.red), playerStarter, neutralStarter, angle = -90f, speedY = 0.2f)
    enemyStack(0) push 22000l -> new Liner(1430f, -50f, Vector(Color.green), playerStarter, neutralStarter, angle = -90f, speedY = 0.2f)

    enemyStack(0) push 15000l -> new Liner(-50f, 10f, Vector(Color.red), playerStarter, neutralStarter, angle = 180f, speedX = 0.25f)
    enemyStack(0) push 15000l -> new Liner(1490f, 10f, Vector(Color.red), playerStarter, neutralStarter, angle = 180f, speedX = -0.25f)

    enemyStack(0) push 6000l -> new Liner(1430f, -50f, Vector(Color.green), playerStarter, neutralStarter, angle = -90f, speedY = 0.2f)
    enemyStack(0) push 6000l -> new Liner(10f, -50f, Vector(Color.green), playerStarter, neutralStarter, angle = 90f, speedY = 0.2f)
//       */

    /*
    109831
113268
116676
120057
123468
126924
130404
133764
168131
171587
174908
178356
181763
185257
188660
192068
     */

    //82813 lite tidigare

    enemyStack(1) push 75602l -> new Liner(1430f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 75602l -> new Liner(10f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 74802l -> new Liner(1430f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 74802l -> new Liner(10f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 74002l -> new Liner(1430f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 74002l -> new Liner(10f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 73202l -> new Liner(1430f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 73202l -> new Liner(10f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 72402l -> new Liner(1430f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 72402l -> new Liner(10f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f, colorSwitch = 10)

    enemyStack(1) push 64209l -> new Liner(1490f, 10f, Vector(Color.blue), playerStarter, neutralStarter, angle = 180f, speedX = -0.25f)
    enemyStack(1) push 64209l -> new Liner(-50f, 10f, Vector(Color.yellow), playerStarter, neutralStarter, angle = 180f, speedX = 0.25f)
    enemyStack(1) push 63409l -> new Liner(1490f, 10f, Vector(Color.green), playerStarter, neutralStarter, angle = 180f, speedX = -0.25f)
    enemyStack(1) push 63409l -> new Liner(-50f, 10f, Vector(Color.blue), playerStarter, neutralStarter, angle = 180f, speedX = 0.25f)
    enemyStack(1) push 62609l -> new Liner(1490f, 10f, Vector(Color.red), playerStarter, neutralStarter, angle = 180f, speedX = -0.25f)
    enemyStack(1) push 62609l -> new Liner(-50f, 10f, Vector(Color.green), playerStarter, neutralStarter, angle = 180f, speedX = 0.25f)
    enemyStack(1) push 61809l -> new Liner(1490f, 10f, Vector(Color.yellow), playerStarter, neutralStarter, angle = 180f, speedX = -0.25f)
    enemyStack(1) push 61809l -> new Liner(-50f, 10f, Vector(Color.red), playerStarter, neutralStarter, angle = 180f, speedX = 0.25f)

    enemyStack(1) push 54961l -> new Liner(1430f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 54961l -> new Liner(10f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 54961l -> new Liner(1430f, 950f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = -0.25f, colorSwitch = 10)
    enemyStack(1) push 54961l -> new Liner(10f, 950f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = -0.25f, colorSwitch = 10)

    enemyStack(1) push 42037l -> new Liner(1430f, -50f, Vector(Color.white, Color.white, Color.red), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f, colorSwitch = 10)
    enemyStack(1) push 42037l -> new Liner(10f, -50f, Vector(Color.white, Color.white, Color.red), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f, colorSwitch = 10)

    enemyStack(1) push 33913l -> new Liner(1410f, -50f, Vector(Color.white), playerStarter, neutralStarter, angle = -90f, speedY = 0.1f, destroyable = true)
    enemyStack(1) push 33913l -> new Liner(30f, -50f, Vector(Color.white), playerStarter, neutralStarter, angle = 90f, speedY = 0.1f, destroyable = true)

    enemyStack(1) push 27392l -> new Liner(1430f, -50f, Vector(Color.green), playerStarter, neutralStarter, angle = -90f, speedY = 0.3f)
    enemyStack(1) push 27392l -> new Liner(-50f, 10f, Vector(Color.red), playerStarter, neutralStarter, angle = 180f, speedX = 0.4f)

    enemyStack(1) push 20528l -> new Liner(1430f, -50f, Vector(Color.green), playerStarter, neutralStarter, angle = -90f, speedY = 0.3f, bulletSpeed = 2.5f)
    enemyStack(1) push 20528l -> new Liner(10f, -50f, Vector(Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = 0.3f, bulletSpeed = 2.5f)

    enemyStack(1) push 13792l -> new Liner(1430f, -50f, Vector(Color.blue), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f)
    enemyStack(1) push 13792l -> new Liner(10f, -50f, Vector(Color.red), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f)

//    enemyStack(1) push 800l -> new Liner(1430f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = -90f, speedY = 0.25f, colorSwitch = 10)
//    enemyStack(1) push 800l -> new Liner(10f, -50f, Vector(Color.blue, Color.green, Color.red, Color.yellow), playerStarter, neutralStarter, angle = 90f, speedY = 0.25f, colorSwitch = 10)


    levelPart = 0
    superDelta = 0l
    music(0).setVolume(1f)
    music(0).play()
  }

  def update(implicit gameContainer: GameContainer, delta: Int) {
    superDelta += delta
    if (gameContainer.getInput.isKeyPressed(Input.KEY_ENTER))
      println(superDelta)

    while (enemyStack(levelPart).headOption.isDefined && enemyStack(levelPart).head._1 <= superDelta)
      enemyStarter.link(enemyStack(levelPart).pop()._2)

    if (superDelta > 39000l && !checkPoints(0)) {
      checkPoints(0) = true
      neutralStarter.link(new Text(2))
    } else if (!checkPoints(1) && enemyStack(0).headOption.isEmpty && enemyStarter.next.isEmpty) {
      music(0).setVolume(music(0).getVolume - 0.0003f * delta)
      if (music(0).getVolume <= 0) {
        superDelta = 0
        checkPoints(1) = true
        music(0).stop()
        music(1).setVolume(0.5f)
        music(1).play()
        neutralStarter.link(new Text(3))
        levelPart += 1
      }
    } else if (checkPoints(1) && !checkPoints(2) && superDelta >= 48299L) {
      neutralStarter.link(new Text(4))
      checkPoints(2) = true
    }

    playerStarter.linkedUpdate
    enemyStarter.linkedUpdate
    backgroundStarter.linkedUpdate
    neutralStarter.linkedUpdate
  }

  def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    playerStarter.linkedRender
    enemyStarter.linkedRender
    backgroundStarter.linkedRender
    neutralStarter.linkedRender
  }
}
