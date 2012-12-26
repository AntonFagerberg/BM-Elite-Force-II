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

import org.newdawn.slick.{Color, Graphics, GameContainer, Image}
import org.newdawn.slick.geom.{Circle, Shape}

class Asteroid(playerStarter: Entity, neutralStarter: Entity, small: Boolean = false) extends Entity {
  private val sprite =
    if (small) new Image("gfx/asteroid_small.png", false, Image.FILTER_NEAREST) getScaledCopy 3f
    else new Image("gfx/asteroid_large.png", false, Image.FILTER_NEAREST) getScaledCopy 3f
  private val radius = sprite.getWidth / 2f
  private val random = new util.Random
  private val outOfScreen = 910f + radius * 2f
  private val explosionSize = if (small) 2.2f else 3.8f

  private var x = 100f + random.nextFloat() * 1340f
  private var y = -sprite.getHeight / 2f
  private var health = if (small) 5 else 15

  private val hitBox = new Circle(x - radius, y - radius, radius)
  private val speedX = 0.1f * (random.nextFloat() - (x - 720f) / 720f)
  private val speedY =  0.1f + 0.3f * random.nextFloat()

  override def collision(implicit hitBoxes: Seq[Shape], color: Color): Boolean = {
    if (!(hitBoxes exists (_ intersects hitBox))) false
    else {
      health -= 1

      if (health <= 0) {
        neutralStarter.link(new Explosion(x, y, explosionSize))
        unlink()
      }

      true
    }
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y > outOfScreen) unlink()
    else {
      sprite.rotate(delta * speedX)
      hitBox.setLocation(x - radius, y - radius)
      x += speedX
      y += delta * speedY
    }
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    sprite.drawCentered(x, y)
//    graphics.draw(hitBox)
  }
}