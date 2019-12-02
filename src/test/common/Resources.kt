import java.io.File

internal object Resources
fun resourceAsString(fileName: String, delimiter: String = ""): String {
    return File(Resources.javaClass.classLoader.getResource(fileName).toURI())
            .readLines()
            .reduce { a, b -> "$a$delimiter$b" }
}

fun resourceAsList(fileName: String): List<String> =
        File(Resources.javaClass.classLoader.getResource(fileName).toURI())
                .readLines()

fun resourceAsList(fileName: String, delimiter: String): List<String> =
        File(Resources.javaClass.classLoader.getResource(fileName).toURI())
                .readLines()
                .first()
                .split(delimiter)