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
 * Calculates the union of two sorted IntRanges, returns a list of either one or two IntRanges depending
 * on if the ranges are overlapping each other or not.
 */
fun IntRange.union(other: IntRange): List<IntRange> {
    return when {
        first > other.first -> error("IntRanges not sorted")
        // adjacent, join to one range
        last + 1 == other.first -> listOf(first..other.last)
        // overlap, join to one range
        last in other || other.last in this -> listOf(
            start..maxOf(last, other.last)
        )

        else -> listOf(this, other) // no overlap
    }
}

/**
 * Calculates the union of all the IntRanges in the given list. Returns a minimal list
 * containing only the remaining IntRanges.
 */
fun List<IntRange>.unionAll(): List<IntRange> {
    val sorted = sortedBy { it.first }

    // The union of the first two entries will either result in one or two ranges.
    // If it's two the start of the third entry will be guaranteed to be after the
    // first range have ended. This is because the list is sorted and the first and
    // second range does not overlap. This in turn that we can keep doing unions with
    // the next element and the last returned range and that way get a minimal list
    // of ranges.
    return buildList {
        add(sorted.first())
        for (i in 1 until sorted.size) {
            val tmp = last().union(sorted[i])
            removeLast()
            addAll(tmp)
        }
    }
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

/**
 * Calculates the union of two sorted IntRanges, returns a list of either one or two IntRanges depending
 * on if the ranges are overlapping each other or not.
 */
fun LongRange.union(other: LongRange): List<LongRange> {
    return when {
        first > other.first -> error("LongRanges not sorted")
        last + 1 == other.first -> listOf(first..other.last)
        last in other || other.last in this -> listOf(
            start..maxOf(last, other.last)
        )

        else -> listOf(this, other) // no overlap
    }
}

/**
 * Calculates the union of all the LongRanges in the given list. Returns a minimal list
 * containing only the remaining LongRanges.
 */
@JvmName("unionAllLong")
fun List<LongRange>.unionAll(): List<LongRange> {
    val sorted = sortedBy { it.first }
    return buildList {
        add(sorted.first())
        for (i in 1 until sorted.size) {
            val tmp = last().union(sorted[i])
            removeLast()
            addAll(tmp)
        }
    }
}
