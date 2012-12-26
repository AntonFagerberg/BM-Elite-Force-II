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

import org.newdawn.slick.{Image, Graphics, GameContainer}

class Text(id: Int, var holdTime: Int = 2500) extends Entity {
  private var y = -300f
  private val image = id match {
    case 1 => new Image("gfx/part_one.png")
    case 2 => new Image("gfx/part_two.png")
    case 3 => new Image("gfx/part_three.png")
    case 4 => new Image("gfx/part_four.png")
    case 5 => new Image("gfx/part_final.png")
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (y < 450) y += delta * 0.2f
    else if (holdTime > 0) holdTime -= delta
    else image.setAlpha(image.getAlpha - delta * 0.001f)

    if (image.getAlpha <= 0)
      unlink()
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    image.drawCentered(720, y)
  }
}
