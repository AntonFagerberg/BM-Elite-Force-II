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
    new Music("msc/498935_X-Sentinel---Lift-O.ogg", true),
    new Music("msc/469781_XS-amp-GS---Game-Ov.ogg", true),
    new Music("msc/476561_LED-MSB---wip.ogg", true)
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

    enemyStack(1) push 191531l -> new BioMedium(320, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 191131l -> new BioMedium(1100, -150, playerStarter, neutralStarter, health = 15)

    enemyStack(1) push 189831l -> new BioSmall(320, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 189131l -> new BioSmall(1100, -150, playerStarter, neutralStarter, health = 15)

    enemyStack(1) push 187131l -> new BioMedium(320, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 187631l -> new BioMedium(1100, -150, playerStarter, neutralStarter, health = 15)

    enemyStack(1) push 185431l -> new BioSmall(220, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 185131l -> new BioSmall(1200, -150, playerStarter, neutralStarter, health = 15)

    enemyStack(1) push 182131l -> new BioLarge(720f, -150f, playerStarter, neutralStarter, speedY = 0.07f)

    enemyStack(1) push 180531l -> new BioMedium(220, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 180331l -> new BioMedium(1100, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 178131l -> new BioSmall(720, -150, playerStarter, neutralStarter, health = 15)

    enemyStack(1) push 176031l -> new BioMedium(1320, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 175231l -> new BioMedium(320, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 173131l -> new BioMedium(720, -150, playerStarter, neutralStarter, health = 15)

    enemyStack(1) push 169131l -> new BioSmall(220, -150, playerStarter, neutralStarter, health = 15)
    enemyStack(1) push 167831l -> new BioSmall(920, -150, playerStarter, neutralStarter, health = 15)

    enemyStack(1) push 109630l -> new Liner(720f, -350f, Vector(Color.white), playerStarter, neutralStarter, angle = 90f, speedY = 0.02f, destroyable = true)
    enemyStack(1) push 109630l -> new Liner(720f, -300f, Vector(Color.white), playerStarter, neutralStarter, angle = -90f, speedY = 0.02f, destroyable = true)
    enemyStack(1) push 109530l -> new BioLarge(720f, -150f, playerStarter, neutralStarter)

    enemyStack(1) push 98280l -> new Liner(680f, -250f, Vector(Color.white), playerStarter, neutralStarter, angle = -90f, speedY = 0.15f, destroyable = true)
    enemyStack(1) push 98280l -> new Liner(760f, -250f, Vector(Color.white), playerStarter, neutralStarter, angle = 90f, speedY = 0.15f, destroyable = true)
    enemyStack(1) push 98280l -> new BioSmall(720, -150, playerStarter, neutralStarter)

    enemyStack(1) push 90280l -> new BioMedium(360, -250, playerStarter, neutralStarter)
    enemyStack(1) push 90280l -> new BioMedium(720, -250, playerStarter, neutralStarter)
    enemyStack(1) push 90280l -> new BioMedium(1080, -250, playerStarter, neutralStarter)
    enemyStack(1) push 90280l -> new Liner(180f, -350f, Vector(Color.white), playerStarter, neutralStarter, angle = 180f, speedY = 0.15f, destroyable = true)
    enemyStack(1) push 90280l -> new Liner(540f, -350f, Vector(Color.white), playerStarter, neutralStarter, angle = 180f, speedY = 0.15f, destroyable = true)
    enemyStack(1) push 90280l -> new Liner(900f, -350f, Vector(Color.white), playerStarter, neutralStarter, angle = 180f, speedY = 0.15f, destroyable = true)
    enemyStack(1) push 90280l -> new Liner(1260f, -350f, Vector(Color.white), playerStarter, neutralStarter, angle = 180f, speedY = 0.15f, destroyable = true)

    enemyStack(1) push 82380l -> new Liner(720, -300f, Vector(Color.white), playerStarter, neutralStarter, angle = -90f, speedY = 0.15f, destroyable = true)
    enemyStack(1) push 82380l -> new Liner(720f, -350f, Vector(Color.white), playerStarter, neutralStarter, angle = 90f, speedY = 0.15f, destroyable = true)
    enemyStack(1) push 82380l -> new BioMedium(720, -150, playerStarter, neutralStarter)

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
        music(1).setVolume(1f)
        music(1).play()
        neutralStarter.link(new Text(3))
        levelPart += 1
      }
    } else if (checkPoints(1) && !checkPoints(2) && superDelta >= 48299L) {
      neutralStarter.link(new Text(4))
      checkPoints(2) = true
    } else if (superDelta >= 200000l && enemyStarter.next.isEmpty && !checkPoints(3)) {
      checkPoints(3) = true
      music(1).stop()
      music(2).play()
      enemyStarter.link(new BigBoss(playerStarter, neutralStarter))
      neutralStarter.link(new Text(5))
    }

    playerStarter.linkedUpdate
    enemyStarter.linkedUpdate
    backgroundStarter.linkedUpdate
    neutralStarter.linkedUpdate
  }

  def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    backgroundStarter.linkedRender
    neutralStarter.linkedRender
    enemyStarter.linkedRender
    playerStarter.linkedRender
  }
}
