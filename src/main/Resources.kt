import java.io.File

internal object Resources

/**
 * Read a file and return the content as a string where each line is
 * separated with the given delimiter.
 */
fun resourceAsString(fileName: String, delimiter: String = ""): String {
    return File(Resources.javaClass.classLoader.getResource(fileName)!!.toURI())
            .readLines()
            .reduce { a, b -> "$a$delimiter$b" }
}

/**
 * Read the given file and return the content as a list of strings, one for each line.
 * Note: the last trailing blank line is ignored.
 */
fun resourceAsList(fileName: String): List<String> =
        File(Resources.javaClass.classLoader.getResource(fileName)!!.toURI())
                .readLines()

/**
 * Reads the first line of text in the given file and split it on the given delimiter.
 */
fun resourceAsList(fileName: String, delimiter: String): List<String> =
        File(Resources.javaClass.classLoader.getResource(fileName)!!.toURI())
                .readLines()
                .first()
                .split(delimiter)

/**
 * Reads the given file and first split it on empty lines (two consecutive newlines).
 * Any single newlines in each string is represented as \n regardless of System line endings.
 */
fun resourceSplitOnBlankLines(fileName: String): List<String> {
    return resourceAsList(fileName)
            .joinToString("\n")
            .split("\n\n")
}
