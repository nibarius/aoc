import org.junit.Assert
import org.junit.Test

class PosTest {

    @Test
    fun distanceToOrigin() {
        Assert.assertEquals(0, Pos(0,0).distanceToOrigin)
        Assert.assertEquals(10, Pos(-5,-5).distanceToOrigin)
        Assert.assertEquals(10, Pos(5,-5).distanceToOrigin)
        Assert.assertEquals(10, Pos(-5,5).distanceToOrigin)
        Assert.assertEquals(10, Pos(5,5).distanceToOrigin)
    }

    @Test
    fun distanceTo() {
        Assert.assertEquals(0, Pos(0,0).distanceTo(Pos(0,0)))
        Assert.assertEquals(20, Pos(5,5).distanceTo(Pos(-5,-5)))
        Assert.assertEquals(10, Pos(5,-5).distanceTo(Pos(-5,-5)))
    }

    @Test
    fun move() {
        Assert.assertEquals(Pos(0,7), Pos(0,0).move(Direction.Down, 7))
        Assert.assertEquals(Pos(-4,3), Pos(3,3).move(Direction.Left, 7))
        Assert.assertEquals(Pos(10,3), Pos(3,3).move(Direction.Right, 7))
        Assert.assertEquals(Pos(-3,-4), Pos(-3,3).move(Direction.Up, 7))
    }
}