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
import org.newdawn.slick.geom.{Shape, Rectangle}

class BigBoss(playerStarter: Entity, neutralStarter: Entity) extends Entity {
  private val shipGreen = IndexedSeq(
    new Image("gfx/green_boss_F_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/green_boss_F_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/green_boss_L_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/green_boss_L_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/green_boss_R_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/green_boss_R_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f
  )

  private val shipYellow = IndexedSeq(
    new Image("gfx/yellow_boss_F_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/yellow_boss_F_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/yellow_boss_L_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/yellow_boss_L_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/yellow_boss_R_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/yellow_boss_R_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f
  )

  private val shipBlue = IndexedSeq(
    new Image("gfx/blue_boss_F_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/blue_boss_F_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/blue_boss_L_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/blue_boss_L_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/blue_boss_R_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/blue_boss_R_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f
  )

  private val shipRed = IndexedSeq(
    new Image("gfx/red_boss_F_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/red_boss_F_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/red_boss_L_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/red_boss_L_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/red_boss_R_1.png", false, Image.FILTER_NEAREST) getScaledCopy 5f,
    new Image("gfx/red_boss_R_2.png", false, Image.FILTER_NEAREST) getScaledCopy 5f
  )

  private var sprites = shipGreen

  private val xOffset1 = sprites.head.getWidth / 2f - 50f
  private val xOffset2 = sprites.head.getWidth / 2f
  private val yOffset = sprites.head.getHeight / 2f
  private val shoot = new Sound("sfx/boss_shoot.wav")
  private val random = new scala.util.Random
  private val bulletStarter = new Starter
  private val tau = 2d * math.Pi

  private var color = Color.green
  private var x = 720f
  private var y = -1000f
  private var superDelta = 0l
  private var pattern = 0
  private var angle = 0d
  private var spriteChange = 0
  private var spriteIndex = 0
  private var direction = 0
  private var mainBulletDelay = 0
  private var sideBulletDelay = 0
  private var health = 5000
  private var tempHealth = 20
  private var randomNumber = 0

  private val hitBoxes = Vector(
    new Rectangle(x - xOffset1, y - yOffset, sprites.head.getWidth - 100f, sprites.head.getHeight - 25f),
    new Rectangle(x - xOffset2, y - yOffset, sprites.head.getWidth, 110f)
  )

  override def collision(implicit hitBoxes: Seq[Shape], color: Color): Boolean = {
    if (this.color == color || !(this.hitBoxes exists (box => hitBoxes exists (_ intersects box)))) false
    else {
      if (pattern <= 1 || pattern == 5 || (pattern == 6 && tempHealth <= 0)) {
        sprites = color match {
          case Color.green => shipGreen
          case Color.blue => shipBlue
          case Color.yellow => shipYellow
          case Color.red => shipRed
        }

        this.color = color

        if (pattern == 6)
          tempHealth = 20

        false
      } else {
        health -= 1

        if (pattern == 6)
          tempHealth -= 1

        if (health < 0) {
          neutralStarter.link(new Explosion(x, y, 10f))
          unlink()
        }

        true
      }
    }
  }

  private def setRandomColor() {
    sprites = random nextInt 4 match {
      case 0 => color = Color.green ; shipGreen
      case 1 => color = Color.red ; shipRed
      case 2 => color = Color.yellow ; shipYellow
      case 3 => color = Color.blue ; shipBlue
    }
  }

  private def mainFire(delta: Int, autoChangeColor: Boolean = true, bulletDelay: Int = 1000) {
    mainBulletDelay -= delta

    if (mainBulletDelay <= 0) {
      bulletStarter.link(new Bullet(x, y + yOffset - 10, color, playerStarter, speedY = 1f))
      bulletStarter.link(new Bullet(x + 25f, y + yOffset - 15f, color, playerStarter, speedY = 1f))
      bulletStarter.link(new Bullet(x - 25f, y + yOffset - 15f, color, playerStarter, speedY = 1f))

      bulletStarter.link(new Bullet(x + 50f, y + yOffset - 20f, color, playerStarter, speedY = 1f))
      bulletStarter.link(new Bullet(x - 50f, y + yOffset - 20f, color, playerStarter, speedY = 1f))

      bulletStarter.link(new Bullet(x + 75f, y + yOffset - 25f, color, playerStarter, speedY = 1f))
      bulletStarter.link(new Bullet(x - 75f, y + yOffset - 25f, color, playerStarter, speedY = 1f))

      bulletStarter.link(new Bullet(x + 100f, y + yOffset - 30f, color, playerStarter, speedY = 1f))
      bulletStarter.link(new Bullet(x - 100f, y + yOffset - 30f, color, playerStarter, speedY = 1f))

      shoot.play()

      if (autoChangeColor)
        setRandomColor()

      while (mainBulletDelay <= 0)
        mainBulletDelay += bulletDelay
    }
  }


  private def sideFire(delta: Int, fireLeft: Boolean = true, fireRight: Boolean = true) {
    sideBulletDelay -= delta

    if (sideBulletDelay <= 0) {
      if (fireLeft) {
        bulletStarter.link(new Bullet(x - xOffset2 + 32f, y, Color.white, playerStarter, speedY = 1f))
        bulletStarter.link(new Bullet(x - xOffset2 + 12f, y, Color.white, playerStarter, speedY = 1f))
      }

      if (fireRight) {
        bulletStarter.link(new Bullet(x + xOffset2 - 32f, y, Color.white, playerStarter, speedY = 1f))
        bulletStarter.link(new Bullet(x + xOffset2 - 12f, y, Color.white, playerStarter, speedY = 1f))
      }

      while (sideBulletDelay <= 0)
        sideBulletDelay += 100
    }
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    superDelta += delta

    if (pattern == 0) {
      y += delta * 0.1f
      if (y > 150f)
        pattern = 1
    } else if (pattern == 1 && superDelta >= 13664) {
      angle += delta * 0.0005d

      if (angle < tau) {
        x = 720f - 600f * (math sin (angle)).toFloat
        if (angle <= 1.5707963267948966 || angle >= 4.71238898038469) direction = 2
        else direction = 4

        if (angle >= 1.5707963267948966) {
          if (angle < 4.71238898038469) sideFire(delta, fireRight = false)
          else sideFire(delta)
        }
      } else {
        x = 720f
        direction = 0
        pattern = 2
        angle = 0d
        sideFire(delta)
        setRandomColor()
      }
    } else if (pattern == 2) {
      sideFire(delta)

      if (superDelta >= 28600)
        mainFire(delta)

      if (superDelta >= 30000) {
        if (angle < math.Pi) {
          angle += delta * 0.0002d
          y = 150f + 450f * math.sin(angle).toFloat
        } else {
          angle = 0f
          y = 150f
          pattern = 3
        }
      }
    } else if (pattern == 3) {
      sideFire(delta)
      mainFire(delta)

      if (superDelta >= 51085) {
        angle += delta * 0.0001d

        if (angle < tau) {
          x = 720f + 600f * (math sin (angle)).toFloat
          if (angle <= 1.5707963267948966 || angle >= 4.71238898038469) direction = 4
          else direction = 2
        } else {
          x = 720f
          direction = 0
          pattern = 4
          angle = 0d
        }
      }
    } else if (pattern == 4) {
      if (superDelta < 126153) {
        sideFire(delta)
        mainFire(delta)
      } else {
        pattern = 5
      }
    } else if (pattern == 5) {
      if (superDelta >= 133000) {
        pattern = 6
      }
    } else if (pattern == 6) {
      angle = (angle + delta * 0.001d) % tau

      x = 720f - 550f * (math sin (angle)).toFloat
      y = 350f - 200f * (math cos (angle)).toFloat

      if (angle <= 1.5707963267948966 || angle >= 4.71238898038469) direction = 2
      else direction = 4

      randomNumber = random nextInt 500
      if (randomNumber < 10) sideFire(delta, fireRight = false)
      else if (randomNumber >= 10 && randomNumber < 20) sideFire(delta, fireLeft = false)
      else if (randomNumber == 20) sideFire(delta)

      mainFire(delta, autoChangeColor = false, bulletDelay = 500)
    }

    spriteChange += delta
    if (spriteChange > 50) {
      spriteChange = 0
      spriteIndex = if (spriteIndex == 0) 1 else 0
    }

    hitBoxes(0).setLocation(x - xOffset1, y - yOffset)
    hitBoxes(1).setLocation(x - xOffset2, y - yOffset)

    bulletStarter.linkedUpdate
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    sprites(spriteIndex + direction).drawCentered(x, y)
//    hitBoxes.foreach(graphics.draw(_))
    bulletStarter.linkedRender
  }
}