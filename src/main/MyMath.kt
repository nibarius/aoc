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
     * Swap the two rows in the matrix.
     */
    private fun List<Array<BigInteger>>.swapRows(a: Int, b: Int) {
        for (col in this[0].indices) {
            val temp = this[b][col]
            this[b][col] = this[a][col]
            this[a][col] = temp
        }
    }

    /**
     * Reduces the part of the matrix by the greatest common denominator for all entries in each row,
     * starting from the given row and repeated for the remaining rows.
     */
    private fun List<Array<BigInteger>>.reduceByGCD(currentRow: Int, pivot: Int) {
        for (row in this.drop(currentRow)) {
            var factor = BigInteger.ZERO
            // Find GCD of all elements in the row
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
    }

    /**
     * Converts the matrix to be in REF (row echelon form).
     *
     * A matrix is in row echelon form if:
     * 1. Any rows containing only zeroes are at the bottom of the matrix
     * 2. The first non-zero entry (pivot) in a row is always to the right
     *    of the pivot in the row above it (staircase pattern).
     * 3. All entries below a pivot are zero.
     *
     * The pivot may be larger than 1 if it's not possible to create a REF
     * with all pivots 1 using only integer division.
     *
     * Example REFs
     * [1 2 3 4]     [1 2 3 4]     [1 2 3 4 5]
     * [0 1 5 6]     [0 1 5 6]     [0 2 1 0 7]
     * [0 0 1 7]     [0 0 1 7]     [0 0 0 1 8]
     *               [0 0 0 0]
     */
    private fun List<Array<BigInteger>>.toREF() {
        val a = this
        // Forward elimination, processing columns from left to right.
        // This will leave a matrix in row echelon form.
        // In row echelon form the diagonal is non-zero and everything below/left is zeroes
        var currentRow = -1
        for (pivot in 0..a[0].size - 2) {
            currentRow++
            // Make leading coefficient of each row positive to make subsequent calculations easier.
            for (row in a.drop(currentRow)) {
                if (row[pivot] < BigInteger.ZERO) {
                    // Flip signs of each coefficient.
                    for (j in row.indices) {
                        row[j] = row[j].negate()
                    }
                }
            }

            while (true) {
                // Reduce by GCD each time to work with as small numbers as possible.
                reduceByGCD(currentRow, pivot)

                val column = a.map { row -> row[pivot] }

                // If only one non-zero coefficient remaining in the column then we're done.
                if (column.drop(currentRow).count { it > BigInteger.ZERO } == 1) {
                    // Move this row into the currentRow location if not already there
                    val index = column.indexOfLast { it > BigInteger.ZERO }

                    if (index != currentRow) {
                        a.swapRows(index, currentRow)
                    }
                    break
                } else if (column.drop(currentRow).all { it == BigInteger.ZERO }) {
                    // There is only zeroes left, meaning there is no pivot variable,
                    // only a free variable. Skip this column and proceed to the next on the same row
                    // this will result in a solution with infinite number of answers.
                    currentRow-- // decrement since next iteration will start by incrementing
                    break
                }

                // Find the row with the lowest non-zero leading coefficient.
                val min = column.drop(currentRow).filter { it > BigInteger.ZERO }.min()
                val index = column.indexOfLast { it == min }

                // Subtract as many multiples of this minimum row from each other row as possible
                // to shrink the coefficients of our column towards zero.
                for (row in currentRow..<a.size) {
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
     * An error is raised for equations with non-integer solutions.
     *
     * @param a The linear equation system to be solved (modified in-place)
     * @return A list of longs with the solution of the equation, each entry holds the value of the
     *         corresponding parameter.
     */
    fun gaussianElimination(a: List<Array<BigInteger>>): List<Long> {
        a.toRREF()
        if (!a.allPivotsAreOne()) {
            error("This equation system have a non-integer solution which is not supported.")
        }
        return a.map { it.last().toLong() }
    }

    /**
     * Returns true if all pivots in the given equation system are one.
     * If there are no integer solutions for the equation system toRREF will
     * leave some pivots with values larger than one.
     * Assumes that the equation system this is called on is in staircase form
     * with any zero rows at the end.
     */
    private fun List<Array<BigInteger>>.allPivotsAreOne(): Boolean {
        return this.all { row ->
            val pivot = row.dropLast(1).firstOrNull() { it != BigInteger.ZERO }
            pivot == null || pivot == BigInteger.ONE // Either a zero row or pivot is one
        }
    }

    /**
     * Converts the equation system to reduced row echelon form.
     *
     * A matrix is in reduced row echelon form if:
     * 1. Any rows of all zeroes are at the bottom of the matrix.
     * 2. The first non-zero entry in any non-zero row must be 1 (the pivot)
     * 3. Each column with a pivot must have only zeroes above and below the pivot
     * 4. Each leading 1 is to the right of the leading 1 in the row above it
     *
     * Example:
     * [1, 0, 0, 0, 1, 24]
     * [0, 1, 0, 0, -1, 10]
     * [0, 0, 1, 0, 2, 27]
     * [0, 0, 0, 1, 0, 3]
     * [0, 0, 0, 0, 0, 0]
     *
     * Columns that does not have any pivot are free variables. When there are free variables it
     * means that there are infinite number of solutions to the equation system.
     *
     * If it's not possible to transform the matrix into RREF with only integer divisions
     * the array will not be in RREF but instead have pivots that are not 1 and columns
     * that are not cleared out above these pivots.
     *
     * Example:
     * [1, 0, 0, 0, 0, 1, 9]
     * [0, 1, 0, 1, 0, 0, 20]
     * [0, 0, 1, -1, 0, 0, -1]
     * [0, 0, 0, 2, 1, -1, 29]
     */
    fun List<Array<BigInteger>>.toRREF(): List<Array<BigInteger>> {
        val a = this
        a.toREF() // First take the equation system to REF.
        // Back substitution, processing columns from right to left. This will leave the matrix in
        // reduced row echelon form with the solved unknowns in the last column.
        for (row in (a.indices).reversed()) {
            // Result column is the last column in the system
            val resultColumn = a[row].size - 1
            // Pivot column is the first column with a 1 value on the current row
            val pivot = a[row].indexOfFirst { it >= BigInteger.ONE }
            if (pivot == -1) {
                // the whole row is just zeroes meaning this equation brings no value, move it to the end.
                (row until a.size).zipWithNext().forEach { (current, next) ->
                    a.swapRows(current, next)
                }
                continue
            }

            // The pivot, free variables and the result are non-zero. All need to be divided
            // by the pivot to get the pivot to 1. Rows above must then be zeroed out.
            // Do this column by column ending with the pivot column so the last thing that
            // is done is to update the pivot. Order is important since we are repeatedly
            // multiplying or dividing with the pivot.

            // if all columns are dividable by the pivot, do that division to get
            // the pivot to 1. Otherwise, we have to live with a non-one pivot since
            // only integer divisions are supported
            if (a[row].all { it.mod(a[row][pivot]) == BigInteger.ZERO }) {
                for (column in resultColumn downTo pivot) {
                    a[row][column] /= a[row][pivot]
                }
            }

            // Zero out the column above the pivot
            for (rowAbove in 0..<row) {
                // Only do it when it's possible to do with integer division though
                if (a[rowAbove][pivot].mod(a[row][pivot]) == BigInteger.ZERO) {
                    for (column in resultColumn downTo pivot) {
                        // . . . | .
                        // 0 1 x | .  <== "rowAbove"-row
                        // 0 0 1 | .  <== "pivot"-row
                        // Multiply pivot-row with 'x' then subtract that from rowAbove-row to zero out 'x'
                        a[rowAbove][column] -= a[row][column] * a[rowAbove][pivot]
                    }
                }
            }
        }
        // We are now in RREF (Reduced row echelon form), or the closest we get if integer divisions is not possible
        return this
    }
}
