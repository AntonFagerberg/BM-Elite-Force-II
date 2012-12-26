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

import level.{Continue, Level, Fight, Intro}
import org.newdawn.slick._

object Main extends BasicGame("BM Elite Force II") {
  private var currentLevel: Level = null // Don't blame me, blame Slick / Java.
  private var showContinue = false
  private var deathCount = 0

  def init(gameContainer: GameContainer) {
    currentLevel = new Intro
  }

  def update(gameContainer: GameContainer, delta: Int) {
    if (!currentLevel.done) currentLevel.update(gameContainer, delta)
    else {
      currentLevel =
        if (!showContinue) new Fight(gameContainer)
        else {
          deathCount += 1
          new Continue(deathCount % 50 == 0)
        }

      showContinue = !showContinue
    }

    if (gameContainer.getInput isKeyDown Input.KEY_ESCAPE)
      gameContainer.exit()
  }

  def render(gameContainer: GameContainer, graphics: Graphics) {
    currentLevel.render(gameContainer, graphics)
  }

  def main(args: Array[String]) {
    val gameContainer = new AppGameContainer(new ScalableGame(this, 1440, 900, true))
    gameContainer.setDisplayMode(gameContainer.getScreenWidth, gameContainer.getScreenHeight, true)
    gameContainer.setUpdateOnlyWhenVisible(false)
    gameContainer.setMouseGrabbed(true)
    gameContainer.setShowFPS(false)
    gameContainer.start()
  }
}