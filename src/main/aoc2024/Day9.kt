package aoc2024

class Day9(input: List<String>) {
    private val diskMap = input.first()

    private fun generateDisk(): IntArray {
        val ret = mutableListOf<Int>()
        var currentId = 0
        var isFile = true
        for (i in diskMap.indices) {
            val size = diskMap[i].digitToInt()
            repeat(size) {
                ret.add(if (isFile) currentId else -1)
            }
            if (isFile) currentId++
            isFile = !isFile
        }
        return ret.toIntArray()
    }

    private fun IntArray.previousNonEmptyFrom(from: Int) = find(from, -1) { it >= 0 }
    private fun IntArray.nextEmptyFrom(from: Int) = find(from, 1) { it < 0 }

    /**
     * Search through the array in the given direction (1 forward, -1 backwards)
     * until the first element that matches the given condition is found. Return
     * -1 if there is no match.
     */
    private fun IntArray.find(from: Int, dir: Int, isMatch: (Int) -> Boolean): Int {
        var i = from
        while (i in indices) {
            if (isMatch(this[i])) return i
            i += dir
        }
        return -1
    }

    /**
     * Defragment the disk in-place by moving items from the end to the beginning.
     * Keep the indexes of the last move to not have to start searching from the
     * beginning again every time.
     */
    private fun defragmentDisk(disk: IntArray) {
        var backwardIndex = disk.size - 1
        var forwardIndex = disk.nextEmptyFrom(0)
        while (forwardIndex in 0..backwardIndex) {
            disk[forwardIndex] = disk[backwardIndex]
            disk[backwardIndex] = -1
            forwardIndex = disk.nextEmptyFrom(forwardIndex)
            backwardIndex = disk.previousNonEmptyFrom(backwardIndex)
        }
    }

    private fun checksum(defragged: IntArray): Long {
        return defragged.takeWhile { it >= 0 }.withIndex().sumOf { (i, v) -> i * v.toLong() }
    }

    fun solvePart1(): Long {
        val d = generateDisk()
        defragmentDisk(d)
        return checksum(d)
    }


    fun solvePart2(): Long {
        val (f, e) = generateDisk2()
        defragmentDisk2(f, e)
        return checksum2(f)
    }

    private class File(var size: Int, var position: Int)

    private fun generateDisk2(): List<MutableList<File>> {
        val filesAndSpaces = listOf<MutableList<File>>(mutableListOf(), mutableListOf())
        var currentPos = 0
        for (i in diskMap.indices) {
            val size = diskMap[i].digitToInt()
            val section = File(size, currentPos)
            filesAndSpaces[i % 2].add(section)
            currentPos += size
        }
        return filesAndSpaces
    }

    private fun List<File>.largeEnoughToFit(file: File): File? {
        for (element in this) {
            if (element.position >= file.position) return null
            if (element.size >= file.size) return element
        }
        return null
    }

    private fun defragmentDisk2(files: List<File>, empty: List<File>) {
        files.asReversed().forEach { file ->
            val moveTo = empty.largeEnoughToFit(file)
            if (moveTo != null) {
                file.position = moveTo.position
                moveTo.position += file.size
                moveTo.size -= file.size
            }
        }
    }

    private fun checksum2(files: List<File>): Long {
        return files.withIndex().sumOf { (fileId, file) ->
            (0..<file.size).sumOf { block ->
                fileId * (file.position + block).toLong()
            }
        }
    }
}