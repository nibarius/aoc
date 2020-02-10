import org.junit.Assert
import org.junit.Test

class DirectionTest {
    private val left = Direction.Left
    private val right = Direction.Right
    private val up = Direction.Up
    private val down = Direction.Down

    @Test
    fun turnLeft() {
        Assert.assertEquals(left, up.turnLeft())
        Assert.assertEquals(down, left.turnLeft())
        Assert.assertEquals(right, down.turnLeft())
        Assert.assertEquals(up, right.turnLeft())
    }

    @Test
    fun turnRight() {
        Assert.assertEquals(right, up.turnRight())
        Assert.assertEquals(down, right.turnRight())
        Assert.assertEquals(left, down.turnRight())
        Assert.assertEquals(up, left.turnRight())
    }

    @Test
    fun turn() {
        Assert.assertEquals(right, up.turn(Direction.Right))
        Assert.assertEquals(down, right.turn(Direction.Right))
        Assert.assertEquals(left, down.turn(Direction.Right))
        Assert.assertEquals(up, left.turn(Direction.Right))
        Assert.assertEquals(left, up.turn(Direction.Left))
        Assert.assertEquals(down, left.turn(Direction.Left))
        Assert.assertEquals(right, down.turn(Direction.Left))
        Assert.assertEquals(up, right.turn(Direction.Left))
    }

    @Test(expected = IllegalArgumentException::class)
    fun turnUp() {
        right.turn(Direction.Up)
    }

    @Test(expected = IllegalArgumentException::class)
    fun turnDown() {
        right.turn(Direction.Down)
    }

    @Test
    fun opposite() {
        Assert.assertEquals(right, left.opposite())
        Assert.assertEquals(left, right.opposite())
        Assert.assertEquals(up, down.opposite())
        Assert.assertEquals(up, down.opposite())
    }

    @Test
    fun from() {
        Assert.assertEquals(Pos(0, 7), down.from(Pos(0, 0), 7))
        Assert.assertEquals(Pos(-4, 3), left.from(Pos(3, 3), 7))
        Assert.assertEquals(Pos(10, 3), right.from(Pos(3, 3), 7))
        Assert.assertEquals(Pos(-3, -4), up.from(Pos(-3, 3), 7))
    }
}