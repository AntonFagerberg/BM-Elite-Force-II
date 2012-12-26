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

import org.newdawn.slick.{Graphics, GameContainer, Image, Sound}

class Explosion (x: Float, y: Float, scale: Float = 1f) extends Entity {
  private val sprites = Vector(
    new Image("gfx/explosion_1.png", false, Image.FILTER_NEAREST) getScaledCopy scale,
    new Image("gfx/explosion_2.png", false, Image.FILTER_NEAREST) getScaledCopy scale,
    new Image("gfx/explosion_3.png", false, Image.FILTER_NEAREST) getScaledCopy scale,
    new Image("gfx/explosion_4.png", false, Image.FILTER_NEAREST) getScaledCopy scale,
    new Image("gfx/explosion_5.png", false, Image.FILTER_NEAREST) getScaledCopy scale,
    new Image("gfx/explosion_6.png", false, Image.FILTER_NEAREST) getScaledCopy scale
  )

  private val sound = new Sound("sfx/explosion.wav")

  private var lifeDelta = 0

  sound.play(1f, 0.15f)

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    if (lifeDelta + delta < 300) lifeDelta += delta
    else unlink()
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    sprites(lifeDelta / 50).drawCentered(x, y)
  }
}
