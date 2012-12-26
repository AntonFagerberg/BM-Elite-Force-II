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

class Background(color: Color = Color.white) extends Entity {
  private val random = new util.Random
  private val starter = new Starter
  0 to 100 foreach (i => starter.link(new Star))

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    starter.linkedUpdate
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    starter.linkedRender
  }

  private class Star extends Entity {
    private val image = new Image("gfx/star.png", false, Image.FILTER_NEAREST)
    private val speed = 0.2f + (random nextFloat())
    private val scale = 0.1f + (random nextFloat())
    private val x = 1440f * (random nextFloat())
    private var y = -20f

    override def update(implicit gameContainer: GameContainer, delta: Int) {
      0 until delta foreach(i => y += speed)

      if (y > 920f) {
        link(new Star)
        unlink()
      }
    }

    override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
      image.draw(x, y, scale, color)
    }
  }
}
