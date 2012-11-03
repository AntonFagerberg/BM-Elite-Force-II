package level

import collection.mutable
import org.newdawn.slick.{Graphics, GameContainer, Color, Music}
import entity._
import org.newdawn.slick.geom.{Transform, Rectangle}

class Fight(gameContainer: GameContainer) extends Level {
  val playerStarter = new Starter
  val enemyStarter = new Starter
  val backgroundStarter = new Starter
  val neutralStarter = new Starter
  val music = mutable.IndexedSeq(
    new Music("msc/498935_X-Sentinel---Lift-O.ogg"),
    new Music("msc/469781_XS-amp-GS---Game-Ov.ogg")
  )

  backgroundStarter.link(new Background)

  if (gameContainer.getInput.getControllerCount > 0)
    for (i <- 0 until gameContainer.getInput.getControllerCount)
      playerStarter.link(new Player(gameContainer, enemyStarter, neutralStarter, i, 500f, 500f))
  else
    playerStarter.link(new Player(gameContainer, enemyStarter, neutralStarter, -1, 500f, 500f))

  enemyStarter.link(new Liner(100, 200, false, Vector(Color.green), neutralStarter, speedRotation = 0.1f))
//  music(0).play()

  def update(implicit gameContainer: GameContainer, delta: Int) {
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
