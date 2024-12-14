import org.junit.Assert
import org.junit.Test

class PosTest {

    @Test
    fun distanceToOrigin() {
        Assert.assertEquals(0, Pos(0, 0).distanceToOrigin)
        Assert.assertEquals(10, Pos(-5, -5).distanceToOrigin)
        Assert.assertEquals(10, Pos(5, -5).distanceToOrigin)
        Assert.assertEquals(10, Pos(-5, 5).distanceToOrigin)
        Assert.assertEquals(10, Pos(5, 5).distanceToOrigin)
    }

    @Test
    fun distanceTo() {
        Assert.assertEquals(0, Pos(0, 0).distanceTo(Pos(0, 0)))
        Assert.assertEquals(20, Pos(5, 5).distanceTo(Pos(-5, -5)))
        Assert.assertEquals(10, Pos(5, -5).distanceTo(Pos(-5, -5)))
    }

    @Test
    fun move() {
        Assert.assertEquals(Pos(0, 7), Pos(0, 0).move(Direction.Down, 7))
        Assert.assertEquals(Pos(-4, 3), Pos(3, 3).move(Direction.Left, 7))
        Assert.assertEquals(Pos(10, 3), Pos(3, 3).move(Direction.Right, 7))
        Assert.assertEquals(Pos(-3, -4), Pos(-3, 3).move(Direction.Up, 7))
    }

    @Test
    fun allNeighbours() {
        println(Pos(10, 10).allNeighbours(true))
        Assert.assertEquals(listOf(Pos(0, -1), Pos(-1, 0), Pos(1, 0), Pos(0, 1)),
                Pos(0, 0).allNeighbours(includeDiagonals = false))
        Assert.assertEquals(listOf(Pos(9, 9), Pos(10, 9), Pos(11, 9), Pos(9, 10), Pos(11, 10), Pos(9, 11), Pos(10, 11), Pos(11, 11)),
                Pos(10, 10).allNeighbours(includeDiagonals = true))
    }

    @Test
    fun plus() {
        Assert.assertEquals(Pos(5, 5), Pos(5, 0) + Pos(0, 5))
        Assert.assertEquals(Pos(5, 5), Pos(0, 0) + Pos(5, 5))
        Assert.assertEquals(Pos(5, 5), Pos(10, 10) + Pos(-5, -5))
    }

    @Test
    fun times() {
        Assert.assertEquals(Pos(5, 5), Pos(1, 1) * 5)
        Assert.assertEquals(Pos(5, 5), Pos(-5, -5) * -1)
        Assert.assertEquals(Pos(0, 0), Pos(10, 10) * 0)
    }

    @Test
    fun rotate() {
        Assert.assertEquals(Pos(1, -1), Pos(1, 1).rotate(Direction.Left))
        Assert.assertEquals(Pos(-10, -10), Pos(10, -10).rotate(Direction.Left))
        Assert.assertEquals(Pos(-19, 1), Pos(-1, -19).rotate(Direction.Left))
        Assert.assertEquals(Pos(1, 21), Pos(-21, 1).rotate(Direction.Left))

        Assert.assertEquals(Pos(1, 16), Pos(16, -1).rotate(Direction.Right))
        Assert.assertEquals(Pos(91, -12), Pos(-12, -91).rotate(Direction.Right))
        Assert.assertEquals(Pos(-61, -16), Pos(-16, 61).rotate(Direction.Right))
        Assert.assertEquals(Pos(-1, 1), Pos(1, 1).rotate(Direction.Right))
    }

    @Test
    fun wrapWithin() {
        Assert.assertEquals(Pos(1,1), Pos(1,1).wrapWithin(10,10))
        Assert.assertEquals(Pos(1,1), Pos(11,11).wrapWithin(10,10))
        Assert.assertEquals(Pos(9,9), Pos(-1,-1).wrapWithin(10,10))
    }
}