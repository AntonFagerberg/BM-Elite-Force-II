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

package level

import org.newdawn.slick._
import entity.Background

class Continue(var youCanDoIt: Boolean = false) extends Level {
  private val gameOver = new Image("gfx/game_over.png")
  private val continue = new Image("gfx/continue.png")
  private val background = new Background(Color.pink)
  private val music =
    if (youCanDoIt) new Music("msc/38773_newgrounds_youcan.ogg", true)
    else new Music("msc/481979_Tangerine.ogg", true)

  private var continueDone = false
  private var superDelta = 0l
  private var fadeDelta = 0l

  music.play()

  override def done = continueDone

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    superDelta += delta

    fadeDelta = superDelta % 3000
    if (fadeDelta < 500) continue.setAlpha(fadeDelta / 500f)
    else if (fadeDelta > 2500) continue.setAlpha(1 - ((fadeDelta - 2500f) / 500f))

    background.update

    for (i <- 0 until gameContainer.getInput.getControllerCount if gameContainer.getInput.isButtonPressed(4, i) && (!youCanDoIt || superDelta > 10000))
      continueDone = true
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    gameOver.drawCentered(720f, 340f)
    continue.drawCentered(720f, 400f)
    background.render
  }
}