package entity

import org.newdawn.slick.Image

object Intro extends Entity {
  var superDelta = 0L
  val nama = new Image("gfx/nama.png", false, Image.FILTER_NEAREST)
  val biiru = new Image("gfx/biiru.png", false, Image.FILTER_NEAREST)
  val mitsu = new Image("gfx/mitsu.png", false, Image.FILTER_NEAREST)
  val kudasai = new Image("gfx/kudasai.png", false, Image.FILTER_NEAREST)
  val bm = new Image("gfx/bm_elite_force.png", false, Image.FILTER_NEAREST)

  val i = IndexedSeq(
    new Image("gfx/i_1.png", false, Image.FILTER_NEAREST),
    new Image("gfx/i_2.png", false, Image.FILTER_NEAREST)
  )

  var index = 0
  var indexCount = -1

  var textY = 400f
  var iY = 400f
  var iX = 440f
  var bmY = -100f


  def update(delta: Int, linker: Linker) {
    superDelta += delta

    for (i <- 0 until delta) {
      if (superDelta >= 34400) {
        if (iY > -100 && superDelta < 41000)
          iY -= 0.5f

        indexCount -= delta
        if (indexCount <= 0) {
          indexCount = 50
          index = if (index == 0) 1 else 0
        }

        textY += 0.5f
      }

      if (superDelta >= 41041 && superDelta < 60000) {
        iX = 710f
        if (iY < 910)
          iY += 0.1f
      }

      if (superDelta >= 60023) {
        if (bmY <= 200f)
          bmY += 0.1f
      }


      if (superDelta >= 65016) {
        if (iX < 935f)
          iX = 935f

        if (iY > 209f)
          iY -= 1.5f
        else if (iY < 209f)
          iY = 209f
      }
    }
  }

  def render() {
    bm.draw(415f, bmY)

    if (superDelta >= 22005)
      nama.draw(150f, textY)

    if (superDelta >= 24703) {
      biiru.draw(400f, textY)
      i(index).draw(iX, iY)
      i(index).draw(iX + 20f, iY)
    }

    if (superDelta >= 27392)
      mitsu.draw(630f, textY)

    if (superDelta >= 30074)
      kudasai.draw(1030f, textY)
  }

  /*
  11465
14041
16627
19324
22005
24703
27392
30074

60023 Text fram
65616 II fram
65653
   */
}