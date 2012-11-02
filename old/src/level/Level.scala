package level

import collection.mutable
import entity.Entity

trait Level {
  def update(delta: Int)
  def start()
  def stop()

  protected val spawn = new mutable.ArrayStack[(Long, Entity)]
  protected var levelDelta = 0L
  protected var setDone = false
  def done(): Boolean = setDone
}
