package aoc2022

class Day7(input: List<String>) {

    private val fileSizes = parseInput(input)

    /**
     * Returns list of sizes of files / directories in the file system. The file names are not needed
     * for anything so those are not included.
     */
    private fun parseInput(input: List<String>): List<Int> {
        var currentDir = "/"
        // Map of full directory path to size of files in the directory (sub directories not included)
        val directories = mutableMapOf<String, Int>()
        var i = 0

        while (i < input.size) {
            val parts = input[i].split(" ")
            when (parts[1]) {
                "cd" -> {
                    when (parts[2]) {
                        "/" -> currentDir = "/"
                        ".." -> currentDir = currentDir.substringBeforeLast("/")
                        else -> {
                            if (!currentDir.endsWith("/")) {
                                currentDir += "/"
                            }
                            currentDir += parts[2]
                        }
                    }
                }

                "ls" -> {
                    val content = input.subList(i + 1, input.size).takeWhile { !it.startsWith("$") }
                    directories[currentDir] = content.filterNot { it.startsWith("dir") }
                        .sumOf { it.split(" ").first().toInt() }
                    i += content.size //advance past all entries
                }

                else -> error("Unknown command")
            }
            i++ //advance past the command
        }

        // directories only includes the size of the files, not of the directories so need to add subdirectory sizes
        return directories.map { (path, _) ->
            // all directories with a shared initial full path is located in the directory identified by the
            // shared path. Their combined size is the size of the base path directory.
            directories.filter { it.key.startsWith(path) }.values.sum()
        }
    }

    fun solvePart1(): Int {
        return fileSizes.sumOf { if (it <= 100000) it else 0 }
    }

    fun solvePart2(): Int {
        val toFree = fileSizes.max() - 40000000
        return fileSizes.filter { it >= toFree }.min()
    }
}