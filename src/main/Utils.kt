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

/**
 * Calculates the union of two IntRanges, returns a list of either one or two IntRanges depending
 * on if the ranges are overlapping each other or not.
 */
fun IntRange.union(other: IntRange): List<IntRange> {
    return when {
        // The two IntRanges are adjacent, join them into one
        this.last == other.first - 1 -> listOf(this.first..other.last)
        this.first == other.last + 1 -> listOf(other.first..this.last)

        // There is no overlap at all
        this.last < other.first || this.first > other.last -> listOf(this, other)

        // There is a complete overlap, return the larger one
        first in other && last in other -> listOf(other)
        other.first in this && other.last in this -> listOf(this)

        // There is a partial overlap, combine to one range
        this.last in other -> listOf(first..other.last)
        this.first in other -> listOf(other.first..last)
        else -> error("Impossible state")
    }
}

/**
 * Calculates the union of all the IntRanges in the given list. Returns a minimal list
 * containing only the remaining IntRanges.
 */
fun List<IntRange>.unionALl(): List<IntRange> {
    // Sort the list of int ranges based on their start.
    val sorted = sortedBy { it.first }

    // The union of the first two entries will either result in one or two ranges.
    // If it's two the start of the third entry will be guaranteed to be after the
    // first range have ended. This is because the list is sorted and the first and
    // second range does not overlap. This in turn that we can keep doing unions with
    // the next element and the last returned range and that way get a minimal list
    // of ranges.
    val ret = mutableListOf(sorted.first())
    for (i in 1 until sorted.size) {
        val tmp = ret.last().union(sorted[i])
        ret.removeLast()
        ret.addAll(tmp)
    }
    return ret
}

/**
 * Calculates the intersection of two IntRanges, returns null if they don't intersect.
 */
fun IntRange.intersect(other: IntRange): IntRange? {
    val r = kotlin.math.max(first, other.first)..kotlin.math.min(last, other.last)
    return if (r.first > r.last) null else r
}

/**
 * Returns the size of the IntRange. Values less than 1 is returned for ranges that
 * ends before they start.
 */
fun IntRange.size() = last - first + 1
