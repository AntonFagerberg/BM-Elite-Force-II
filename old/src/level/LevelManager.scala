package level

import org.newdawn.slick.GameContainer
import util.Random
import entity.Linker

class LevelManager(random: Random, backgrounds: Linker, players: Linker, enemies: Linker) {
  private var levelIndex = 1
  private val level = new collection.mutable.ArrayBuffer[Level]

  def init(gameContainer: GameContainer) {
    level += new Intro(gameContainer, random: Random, backgrounds: Linker, players: Linker, enemies: Linker)
    level += new Fight(gameContainer, random: Random, backgrounds: Linker, players: Linker, enemies: Linker)
    level(levelIndex).start
  }

  def update(delta: Int) {
    if (level(levelIndex).done()) {
      level(levelIndex).stop()

      levelIndex = levelIndex match {
        case 0 => 1
      }

      level(levelIndex).start()
    }

    level(levelIndex).update(delta)
  }
}