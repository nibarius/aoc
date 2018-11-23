package aoc2016

class Day25(private val instructions: List<String>) {

    fun solvePart1(): Int {
        // What the program does:
        // X = input + 2555
        // X = X / 2
        // print reminder
        // repeat until X = 0
        // Start over from the beginning

        // For infinite 0, 1, 0, 1 ... X needs to be odd, even, odd, even
        // Going backwards from the point just before X wraps around
        // X need to be 0, 1, 2, 5, 10, 21, 42, 85 ...

        var x = 1
        while (x < 2555) {
            if (x % 2 == 0) {
                x = 2 * x + 1
            } else {
                x *= 2
            }
        }

        val bunny = Assembunny(instructions.toMutableList())
        val a = x - 2555
        bunny.registers["a"] = a
        bunny.execute()
        return a
    }
}
