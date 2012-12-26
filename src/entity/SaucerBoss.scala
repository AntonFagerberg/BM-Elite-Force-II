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
import geom.{Shape, Circle}

class SaucerBoss(playerStarter: Entity, neutralStarter: Entity) extends Entity {
  private val bulletStarter = new Starter
  private val expandSound = new Sound("sfx/saucer_boss_expand.wav")
  private val x = 720f
  private var y = -450f
  private val hitBox = new Circle(x, y, 80f)
  private var health = 450f
  private var bulletDelay = 100
  private var rotationRadians = 0d
  private var angle = 0f
  private var angle1 = 0d
  private var angle1cos = 0f
  private var angle1sin = 0f
  private var angle2 = 0d
  private var angle2cos = 0f
  private var angle2sin = 0f
  private var angle3 = 0d
  private var angle3cos = 0f
  private var angle3sin = 0f
  private var superDelta = 0
  private var spriteChange = 0

  private val sprites = Vector(
    new Image("gfx/saucer_boss_inactive_1.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_inactive_2.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_inactive_3.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_inactive_4.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_active_1.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_active_2.png", false, Image.FILTER_NEAREST).getScaledCopy(5f),
    new Image("gfx/saucer_boss_active_3.png", false, Image.FILTER_NEAREST).getScaledCopy(5f)
  )

  private var sprite = sprites.head

  override def collision(implicit hitBoxes: Seq[Shape], color: Color = Color.white): Boolean = {
    if (superDelta >= 13727 && hitBoxes.exists(_.intersects(hitBox))) {
      health -= 0.2f
      if (health <= 0) {
        neutralStarter.link(new Explosion(x, y, 10f))
        unlink()
      }
      true
    } else {
      false
    }
  }

  override def update(implicit gameContainer: GameContainer, delta: Int) {
    superDelta += delta

    if (superDelta <= 6892) {
      sprite = sprites(0)
    } else if (superDelta <= 8500) {
      if (sprite == sprites(0)) expandSound.play()
      sprite = sprites(1)
    } else if (superDelta <= 10000) {
      if (sprite == sprites(1)) expandSound.play()
      sprite = sprites(2)
    } else if (superDelta <= 11500) {
      if (sprite == sprites(2)) expandSound.play()
      sprite = sprites(3)
    } else {
      spriteChange = (spriteChange + delta) % 400

      if (spriteChange < 100) sprite = sprites(4)
      else if (spriteChange < 200)  sprite = sprites(5)
      else sprite = sprites(6)

      if (superDelta >= 13727) {
        for (i <- 0 until delta) {
          // 0.25

          angle =
            if (health > 400) 0.03f + (0.10f * (450 - health) / 50)
            else if (health > 300) 0.13f * (-350 + health) / 50
            else if (health > 200) 0.13f * (250 - health) / 50
            else 0.13f + (0.12f * (200 - health) / 200)

          sprites(4).rotate(angle)
          sprites(5).rotate(angle)
          sprites(6).rotate(angle)
        }

        rotationRadians = sprite.getRotation * math.Pi / 180d
        bulletDelay -= delta

        if (bulletDelay < 0) {
          angle1 = rotationRadians + 0.48d
          angle1cos = math.cos(angle1).toFloat
          angle1sin = math.sin(angle1).toFloat
          angle2 = rotationRadians + 2.7d
          angle2cos = math.cos(angle2).toFloat
          angle2sin = math.sin(angle2).toFloat
          angle3 = rotationRadians + -1.53d
          angle3cos = math.cos(angle3).toFloat
          angle3sin = math.sin(angle3).toFloat

          bulletStarter.link(new Bullet(x + 100f * angle1cos, y + 100f * angle1sin, Color.red, playerStarter, angle1cos, angle1sin, 90f + (angle1 * 180d / math.Pi).toFloat))
          bulletStarter.link(new Bullet(x + 100f * angle2cos, y + 100f * angle2sin, Color.green, playerStarter, angle2cos, angle2sin, 90f + (angle2 * 180d / math.Pi).toFloat))
          bulletStarter.link(new Bullet(x + 100f * angle3cos, y + 100f * angle3sin, Color.yellow, playerStarter, angle3cos, angle3sin, 90f + (angle3 * 180d / math.Pi).toFloat))
          bulletDelay += 50
        }
      }
    }

    for (i <- 0 until delta) {
      if (y < 450) y += 0.2f
      else if(y != 450) y = 450
    }

    hitBox.setLocation(x - 80f, y - 80f)
    bulletStarter.linkedUpdate
  }

  override def render(implicit gameContainer: GameContainer, graphics: Graphics) {
    bulletStarter.linkedRender
    sprite.drawCentered(x, y)
//    graphics.draw(hitBox)
  }
}
