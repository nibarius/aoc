import java.math.BigInteger

object MyMath {
    // Based on
    // https://en.wikipedia.org/wiki/Modular_multiplicative_inverse#Applications
    // https://sv.wikipedia.org/wiki/Kinesiska_restklassatsen
    // https://rosettacode.org/wiki/Chinese_remainder_theorem
    // Use BigInteger to be able to calculate big numbers and to get modInverse for free.
    private fun chineseRemainder(pairs: Collection<Pair<BigInteger, BigInteger>>): BigInteger {
        val prod = pairs.fold(1.toBigInteger()) { acc, i -> acc * i.first }
        var sum = 0.toBigInteger()
        for ((ni, ai) in pairs) {
            val p = prod / ni
            sum += ai * p.modInverse(ni) * p
        }
        return sum % prod
    }

    /**
     * The chinese remainder theorem can be used to find the solution to for example the system:
     * x === 0 (mod 17)    (x mod 17 = 0)
     * x === 11 (mod 13)   (x mod 13 = 11)
     * x === 16 (mod 19)   (x mod 19 = 16)
     * chineseRemainder([(17, 0), (13, 11), (19, 16)]) == 3417 ==> x = 3417
     *
     * @param pairs a collection of pairs where first is the modulus and second is the remainder.
     */
    fun chineseRemainder(pairs: Collection<Pair<Int, Int>>): Long {
        return chineseRemainder(pairs.map { it.first.toBigInteger() to it.second.toBigInteger() }).toLong()
    }
}