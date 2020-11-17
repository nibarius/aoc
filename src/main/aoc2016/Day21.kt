package aoc2016

class Day21(private val start: String, private val input: List<String>) {

    class Scrambler(initialState: String) {
        private val buffer = initialState.toCharArray()
        private var startPointer = 0

        private fun getRealIndex(virtualIndex: Int): Int {
            return (startPointer + virtualIndex) % buffer.size
        }

        private fun getVirtualIndex(realIndex: Int): Int {
            var index = realIndex - startPointer
            if (index < 0) {
                index += buffer.size
            }
            return index
        }

        private fun internalSwap(x: Int, y: Int) {
            val tmp = buffer[x]
            buffer[x] = buffer[y]
            buffer[y] = tmp
        }

        fun swap(x: Int, y: Int) = internalSwap(getRealIndex(x), getRealIndex(y))
        fun swap(x: Char, y: Char) = internalSwap(buffer.indexOf(x), buffer.indexOf(y))

        // negative means rotate left, positive means rotate right
        // rotating left moves start pointer to the right and vise versa
        fun rotate(steps: Int) {
            startPointer -= steps
            while (startPointer < 0) {
                startPointer += buffer.size
            }
            startPointer %= buffer.size
        }

        fun rotate(basedOn: Char) {
            val index = getVirtualIndex(buffer.indexOf(basedOn))
            val toRotate = if (index >= 4) {
                index + 2
            } else {
                index + 1
            }
            rotate(toRotate)
        }

        fun reverse(from: Int, to: Int) {
            var left = from
            var right = to
            while (left < right) {
                swap(left, right)
                left++
                right--
            }
        }

        fun move(from: Int, to: Int) {
            val direction = if (from < to) 1 else -1
            var toMove = from
            while (toMove != to) {
                swap(toMove, toMove + direction)
                toMove += direction
            }
        }

        fun getCurrentValue() = buffer.slice(startPointer until buffer.size).joinToString("") +
                buffer.slice(0 until startPointer).joinToString("")
    }

    private fun scramble(initialValue: String, instructions: List<String>): String {
        val scrambler = Scrambler(initialValue)
        for (instruction in instructions) {
            val words = instruction.split(" ")
            when {
                instruction.startsWith("swap position") -> {
                    scrambler.swap(words[2].toInt(), words[5].toInt())
                }
                instruction.startsWith("swap letter") -> {
                    scrambler.swap(words[2][0], words[5][0])
                }
                instruction.startsWith("rotate based") -> {
                    scrambler.rotate(words.last()[0])
                }
                instruction.startsWith("rotate") -> {
                    val direction = if (words[1] == "left") -1 else 1
                    scrambler.rotate(direction * words[2].toInt())
                }
                instruction.startsWith("reverse") -> {
                    scrambler.reverse(words[2].toInt(), words.last().toInt())
                }
                instruction.startsWith("move") -> {
                    scrambler.move(words[2].toInt(), words.last().toInt())
                }
            }
        }
        return scrambler.getCurrentValue()
    }

    private fun unScramble(initialValue: String, instructions: List<String>): String {
        val scrambler = Scrambler(initialValue)
        for (instruction in instructions.asReversed()) {
            val words = instruction.split(" ")
            when {
                instruction.startsWith("swap position") -> {
                    // Same as when scrambling
                    scrambler.swap(words[2].toInt(), words[5].toInt())
                }
                instruction.startsWith("swap letter") -> {
                    // Same as when scrambling
                    scrambler.swap(words[2][0], words[5][0])
                }
                instruction.startsWith("rotate based") -> {
                    /*
                        With a size 8 buffer (which is what we need to unscrambled)
                        01234567         01234567  to rotate
                        x....... 0 ==> 1 .x......  1
                        .x...... 1 ==> 3 ...x....  2
                        ..x..... 2 ==> 5 .....x..  3
                        ...x.... 3 ==> 7 .......x  4
                        ....x... 4 ==> 2 ..x.....  6
                        .....x.. 5 ==> 4 ....x...  7
                        ......x. 6 ==> 6 ......x.  8
                        .......x 7 ==> 0 x.......  9
                    */
                    val index = scrambler.getCurrentValue().indexOf(words.last())
                    val toRotate = mapOf(1 to 1, 3 to 2, 5 to 3, 7 to 4, 2 to 6, 4 to 7, 6 to 8, 0 to 9).getValue(index)

                    scrambler.rotate(-1 * toRotate)
                }
                instruction.startsWith("rotate") -> {
                    // Rotate in the opposite direction
                    val direction = if (words[1] == "left") 1 else -1
                    scrambler.rotate(direction * words[2].toInt())
                }
                instruction.startsWith("reverse") -> {
                    // Same as when scrambling
                    scrambler.reverse(words[2].toInt(), words.last().toInt())
                }
                instruction.startsWith("move") -> {
                    // Swap arguments to de-scramble
                    scrambler.move(words.last().toInt(), words[2].toInt())
                }
            }
        }
        return scrambler.getCurrentValue()
    }

    fun solvePart1(): String {
        return scramble(start, input)
    }

    fun solvePart2(): String {
        return unScramble(start, input)
    }
}
