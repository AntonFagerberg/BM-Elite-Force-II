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

import org.newdawn.slick.{Color, Graphics, GameContainer}
import org.newdawn.slick.geom.Shape

trait Entity {
  private var nextEntity: Option[Entity] = None
  private var previousEntity: Option[Entity] = None

  def next = nextEntity
  def previous = previousEntity

  def update(implicit gameContainer: GameContainer, delta: Int) {}
  def render(implicit gameContainer: GameContainer, graphics: Graphics) {}
  def collision(implicit hitBoxes: Seq[Shape], color: Color = Color.white): Boolean = {
    println("Called collision on Entity [" + this.getClass + "] which does not implement it. ")
    false
  }

  final def linkedUpdate(implicit gameContainer: GameContainer, delta: Int) {
    update
    if (nextEntity.isDefined) nextEntity.get.linkedUpdate
  }

  final def linkedRender(implicit gameContainer: GameContainer, graphics: Graphics) {
    render
    if (nextEntity.isDefined) nextEntity.get.linkedRender
  }

  def linkedCollision(implicit hitBoxes: Seq[Shape], color: Color = Color.white): Int = {
    if (collision && next.isDefined) 1 + next.get.linkedCollision
    else if (!collision && next.isDefined) next.get.linkedCollision
    else if (collision) 1
    else 0
  }

  def link(entity: Entity) {
    if (nextEntity.isDefined) nextEntity.get.previousEntity = Some(entity)
    entity.nextEntity = nextEntity
    nextEntity = Some(entity)
    entity.previousEntity = Some(this)
  }

  def unlink() {
    if (nextEntity.isDefined) nextEntity.get.previousEntity = previousEntity
    if (previousEntity.isDefined) previousEntity.get.nextEntity = nextEntity
  }
}
