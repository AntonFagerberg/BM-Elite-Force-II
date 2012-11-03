package entity

import org.newdawn.slick.geom.Shape
import org.newdawn.slick.Color

class Starter extends Entity {
  override def collision(implicit hitBoxes: List[Shape], color: Option[Color] = None): Boolean = false
}