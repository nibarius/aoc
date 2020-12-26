import java.math.BigInteger
import java.security.MessageDigest

inline fun <reified T : Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}

inline fun <reified T : Enum<T>> T.prev(): T {
    val values = enumValues<T>()
    val prevOrdinal = when (ordinal) {
        0 -> values.size - 1
        else -> ordinal - 1
    }
    return values[prevOrdinal]
}

inline fun <reified T> MutableMap<T, Int>.increase(what: T) {
    if (this.containsKey(what)) {
        this[what] = this[what]!! + 1
    } else {
        this[what] = 1
    }
}

inline fun <reified T> MutableMap<T, Int>.decrease(what: T) {
    if (this.containsKey(what)) {
        this[what] = this[what]!! - 1
    } else {
        this[what] = -1
    }
}

fun <T> Map<Pos, T>.xRange() = keys.minByOrNull { it.x }!!.x..keys.maxByOrNull { it.x }!!.x
fun <T> Map<Pos, T>.yRange() = keys.minByOrNull { it.y }!!.y..keys.maxByOrNull { it.y }!!.y

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}
