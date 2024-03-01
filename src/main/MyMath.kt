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

    /**
     * Calculates the least common multiple of a collection of ints.
     */
    fun lcm(numbers: Collection<Int>): Long {
        return numbers.fold(1.toBigInteger()) { acc, i -> lcm(acc, i.toBigInteger()) }.toLong()
    }

    // Based on https://www.baeldung.com/java-least-common-multiple#lcm-biginteger
    private fun lcm(n1: BigInteger, n2: BigInteger): BigInteger {
        return (n1 * n2).abs() / n1.gcd(n2)
    }

    /**
     * Gaussian elimination for solving linear equations. The input should be n+1 * n large, where there
     * are as many equations as there are unknowns. The last column holds the right hand side of the
     * equations which should be a constant value. The input gets modified in place when this method is called.
     *
     * Only equations with integer solutions are supported. Only integer divisions are performed during the
     * solving, so there are no rounding errors by this algorithm.
     *
     * The equation system:
     * x + 2y + 3z = 7
     * 2x - 3y - 5z = 9
     * -6x - 8y + z = -22
     *
     * Would have the input:
     * listOf(
     * [ 1,  2,  3,   7],
     * [ 2, -3, -5,   9],
     * [-6, -8,  1, -22])
     *
     * This equation system have the solution x = -1, y = 3, z = -4 and this method would return
     * listOf(-1L, 3L, -4L) in this case.
     *
     * For more details on how Gaussian elimination works, related terms and this algorithm, see:
     * - https://www.dummies.com/article/academics-the-arts/math/pre-calculus/how-to-use-gaussian-elimination-to-solve-systems-of-equations-167828/
     * - https://en.wikipedia.org/wiki/Row_echelon_form
     * - https://www.reddit.com/r/adventofcode/comments/1b1l6iw/2024_day_24_part_2_kotlin_gaussjordan_elimination/
     *
     * Result is undefined (unknown) for unsolvable equations.
     *
     * @param a The linear equation system to be solved (modified in-place)
     * @return A list of longs with the solution of the equation, each entry holds the value of the
     *         corresponding parameter.
     */
    fun gaussianElimination(a: List<Array<BigInteger>>): List<Long> {
        // Forward elimination, processing columns from left to right.
        // This will leave a matrix in row echelon form.
        // In row echelon form the diagonal is non-zero and everything below/left is zeroes
        for (pivot in a.indices) {
            // Make leading coefficient of each row positive to make subsequent calculations easier.
            for (row in a.drop(pivot)) {
                if (row[pivot] < BigInteger.ZERO) {
                    // Flip signs of each coefficient.
                    for (j in row.indices) {
                        row[j] = row[j].negate()
                    }
                }
            }

            while (true) {
                // Reduce by GCD each time to work with as small numbers as possible.
                for (row in a.drop(pivot)) {
                    var factor = BigInteger.ZERO
                    for (next in row.drop(pivot)) {
                        if (next != BigInteger.ZERO) {
                            factor = if (factor == BigInteger.ZERO) {
                                next.abs()
                            } else {
                                factor.gcd(next.abs())
                            }
                        }
                    }
                    if (factor > BigInteger.ONE) {
                        for (j in row.indices.drop(pivot)) {
                            row[j] /= factor // Dividing by gcd is an integer safe division
                        }
                    }
                }

                val column = a.map { row -> row[pivot] }

                // If only one non-zero coefficient remaining in the column then we're done.
                if (column.drop(pivot).count { it > BigInteger.ZERO } == 1) {
                    // Move this row into the pivot location if not already there
                    val index = column.indexOfLast { it > BigInteger.ZERO }

                    if (index != pivot) {
                        for (col in a[0].indices) { // Swap row 'index' and 'pivot'
                            val temp = a[pivot][col]
                            a[pivot][col] = a[index][col]
                            a[index][col] = temp
                        }
                    }
                    break;
                }

                // Find the row with the lowest non-zero leading coefficient.
                val min = column.drop(pivot).filter { it > BigInteger.ZERO }.min()
                val index = column.indexOfLast { it == min }

                // Subtract as many multiples of this minimum row from each other row as possible
                // to shrink the coefficients of our column towards zero.
                for (row in pivot..<a.size) {
                    if (row != index && column[row] != BigInteger.ZERO) {
                        // "factor" is the largest integer multiple we can use without going over min,
                        // so integer division is desired here.
                        val factor = column[row] / min
                        for (col in pivot..<a[row].size) {
                            a[row][col] -= factor * a[index][col]
                        }
                    }
                }
            }
        }

        // Back substitution, processing columns from right to left. This will leave the matrix in
        // reduced row echelon form with the solved unknowns in the last column.
        for (pivot in (a.indices).reversed()) {
            if (a[pivot][a.size].mod(a[pivot][pivot]) != BigInteger.ZERO) {
                // Only integer solutions are supported, if decimal solutions are required
                // the above division needs to be done with a non-integer datatype.
                error("Gaussian elimination results in a non-integer division which is not supported")
            }
            // Only the pivot and the result are non-zero. Divide both by the pivot to get the pivot to 1.
            a[pivot][a.size] /= a[pivot][pivot]
            a[pivot][pivot] = BigInteger.ONE

            // Zero out everything in the column above the pivot
            for (row in 0..<pivot) {
                // . . . | .
                // 0 1 x | .  <== "row"-row
                // 0 0 1 | .  <== "pivot"-row
                // Multiply pivot-row with 'x' then subtract that from row-row to zero out 'x'
                // Since all but pivot and result is 0 those are the one two that gets updated.
                a[row][a.size] -= a[pivot][a.size] * a[row][pivot]
                a[row][pivot] = BigInteger.ZERO
            }
        }

        return a.map { it.last().toLong() }
    }
}