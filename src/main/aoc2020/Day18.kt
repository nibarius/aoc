package aoc2020

import java.lang.Integer.max

class Day18(input: List<String>) {
    val problems = input
            .map { line -> line.mapNotNull { ch -> ch.takeUnless { it == ' ' } } }
            .map { parseInput(it) }

    sealed class Expression {
        data class Operator(private val op: Char) : Expression() {  // *, +
            fun calculate(lValue: Expression, rValue: Expression): Long {
                lValue as Literal; rValue as Literal
                return when (op) {
                    '+' -> lValue.value + rValue.value
                    '*' -> lValue.value * rValue.value
                    else -> error("unknown operator")
                }
            }
        }

        data class Literal(val value: Long) : Expression() // numbers
        data class Complex(val list: MutableList<Expression>) : Expression() // The root problem / inside a parenthesis
    }

    private fun parseInput(problem: List<Char>): Expression {
        // List of complex expressions, where the first entry is the root expression and
        // the rest each yet unfinished parenthesis
        val expression = mutableListOf(Expression.Complex(mutableListOf()))
        for (token in problem) {
            when (token) {
                '(' -> { // Start a new complex expression
                    expression.add(Expression.Complex(mutableListOf()))
                }
                ')' -> { // Finalize the latest complex expression
                    val finishedExpression = expression.removeLast()
                    expression.last().list.add(finishedExpression)
                }
                else -> { // Add the operator or literal to the current expression
                    val toAdd = if (token in listOf('+', '*')) {
                        Expression.Operator(token)
                    } else {
                        Expression.Literal(token.toString().toLong())
                    }
                    expression.last().list.add(toAdd)
                }
            }
        }
        return expression.first()
    }

    /**
     * Calculate the given expression. Complex expressions is reduced to a literal expression
     * while all other expressions are returned back immediately.
     */
    private fun calculate(expression: Expression, indexOfNextToCalculate: (Expression.Complex) -> Int): Expression {
        if (expression !is Expression.Complex) {
            return expression
        }
        // calculate all parenthesis first
        expression.list.forEachIndexed { index, expr -> expression.list[index] = calculate(expr, indexOfNextToCalculate) }

        // Reduce the complex expression one operator at a time until there is only one literal remaining.
        while (expression.list.size > 1) {
            calculateAtIndex(indexOfNextToCalculate(expression), expression)
        }
        return expression.list.first()
    }

    /**
     * Calculates the result of the two literals and the in between operator at the given position in the given
     * expression. The literals and operator is removed and replaced in the given expression.
     */
    private fun calculateAtIndex(index: Int, expression: Expression.Complex) {
        val operator = expression.list[index + 1] as Expression.Operator
        val res = operator.calculate(expression.list[index], expression.list[index + 2])
        expression.list.removeAt(index)
        expression.list.removeAt(index)
        expression.list[index] = Expression.Literal(res)
    }

    /**
     * As long as there are any pluses left calculate those first, otherwise calculate the left most pair
     */
    private fun plusFirstStrategy(expression: Expression.Complex): Int {
        return max(expression.list.indexOf(Expression.Operator('+')) - 1, 0)
    }

    private fun solve(strategy: (Expression.Complex) -> Int): Long {
        return problems.map { calculate(it, strategy) }.sumOf { (it as Expression.Literal).value }
    }

    fun solvePart1(): Long {
        return solve { 0 } // always choose the leftmost pair to calculate first
    }

    fun solvePart2(): Long {
        return solve(this::plusFirstStrategy)
    }
}