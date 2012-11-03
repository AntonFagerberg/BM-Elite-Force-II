package level

import collection.mutable
import org.newdawn.slick._
import entity._

class Fight(gameContainer: GameContainer) extends Level {
  private val playerStarter = new Starter
  private val enemyStarter = new Starter
  private val backgroundStarter = new Starter
  private val neutralStarter = new Starter
  private val music = mutable.IndexedSeq(
    new Music("msc/498935_X-Sentinel---Lift-O.ogg"),
    new Music("msc/469781_XS-amp-GS---Game-Ov.ogg")
  )
  private val enemyStack = Vector(
    new collection.mutable.Stack[(Long, Entity)]
  )
  private var levelPart = 0
  private var superDelta = 0l

  initialize()

  private def initialize() {
    backgroundStarter.link(new Background)
    neutralStarter.link(new Text(1))

    if (gameContainer.getInput.getControllerCount > 0)
      for (i <- 0 until gameContainer.getInput.getControllerCount)
        playerStarter.link(new Player(gameContainer, enemyStarter, neutralStarter, i))
    else
      playerStarter.link(new Player(gameContainer, enemyStarter, neutralStarter, -1))

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



    levelPart = 0
    superDelta = 0l
    music(0).play()
  }

  def update(implicit gameContainer: GameContainer, delta: Int) {
    superDelta += delta
    println(superDelta)

    while (enemyStack(levelPart).headOption.isDefined && enemyStack(levelPart).head._1 <= superDelta)
      enemyStarter.link(enemyStack(levelPart).pop()._2)

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
