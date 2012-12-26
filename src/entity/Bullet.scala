/*
 * BM Elite Force II
 * Copyright (C) 2012 Anton Fagerberg
 * www.antonfagerberg.com | anton [at] antonfagerberg [dot] com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package entity

import org.newdawn.slick._
import org.newdawn.slick.geom.{Shape, Transform, Rectangle}

class Bullet(var x: Float, var y: Float, color: Color, collisionStarter: Entity, speedX: Float = 0f, speedY: Float = -1.3f, angle: Float = 0f) extends Entity {
  private val sprite = new Image("gfx/bullet.png")
  private val yOffset = 14f
  private val xOffset = 7f
  private val hitBox = new Rectangle(x - xOffset, y - yOffset, 15, 30)

  sprite.setRotation(angle)

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y < -100 || y > 1000 || x < -100 || x > 1500) unlink()
    else {
      for (i <- 0 until delta) {
        y += speedY
        x += speedX
      }

      if (collisionStarter.linkedCollision(List(hitBox.transform(Transform.createRotateTransform(math.toRadians(angle).toFloat, hitBox.getX + xOffset, hitBox.getY + yOffset))), color) > 0) unlink()
      else hitBox.setLocation(x - xOffset, y - yOffset)
    }
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    sprite.draw(x - sprite.getWidth / 2f, y - sprite.getHeight / 2f, color)
//    graphics.draw(hitBox.transform(Transform.createRotateTransform(math.toRadians(angle).toFloat, hitBox.getX + xOffset, hitBox.getY + yOffset)))
  }
}
