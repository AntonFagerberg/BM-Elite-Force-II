package level

import collection.mutable
import org.newdawn.slick.{Graphics, GameContainer, Color, Music}
import entity._
import org.newdawn.slick.geom.{Transform, Rectangle}

class Fight(gameContainer: GameContainer) extends Level {
  val playerStarter = new Starter
  val enemyStarter = new Starter
  val backgroundStarter = new Starter
  val music = mutable.IndexedSeq(
    new Music("msc/498935_X-Sentinel---Lift-O.ogg"),
    new Music("msc/469781_XS-amp-GS---Game-Ov.ogg")
  )

  if (gameContainer.getInput.getControllerCount > 0)
    for (i <- 0 until gameContainer.getInput.getControllerCount)
      playerStarter.link(new Player(gameContainer, enemyStarter, i, 100f, 100f))
  else
    playerStarter.link(new Player(gameContainer, enemyStarter, -1, 100f, 100f))

  new Background(backgroundStarter)
//  music(0).play()

  enemyStarter.link(new Liner(100, 200, false, Vector(Color.green), speedRotation = 0.1f))

  private val hitBox = new Rectangle(250, 250, 100, 200)
  private val newHitBox1 = hitBox.transform(Transform.createRotateTransform(0, hitBox.getCenterX, hitBox.getCenterY))
  private val newHitBox2 = hitBox.transform(Transform.createRotateTransform(math.Pi.toFloat / 2f, hitBox.getCenterX, hitBox.getCenterY))

  def update(implicit gameContainer: GameContainer, delta: Int) {
    playerStarter.update
    enemyStarter.update
    backgroundStarter.update
  }

  def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    playerStarter.render
    enemyStarter.render
    backgroundStarter.render
    graphics.draw(newHitBox1)
    graphics.draw(newHitBox2)
  }
}
