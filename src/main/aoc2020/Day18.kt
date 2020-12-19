package aoc2020

class Day18(input: List<String>) {
    val problems = input.map { line -> line.mapNotNull { ch -> ch.takeUnless { it == ' ' } } }

    private fun handleNumber(left: MutableList<Long?>, operator: MutableList<(Long, Long) -> Long>, number: Long) {
        if (left.last() == null) {
            left[left.size - 1] = number
        } else {
            val op = operator.removeLast()
            left[left.size - 1] = op.invoke(left.last()!!, number)
        }
    }

    private fun calculate(problem: List<Char>): Long {

        val operator = mutableListOf<(Long, Long) -> Long>()
        val left = mutableListOf<Long?>(null)

        for (token in problem) {
            when (token) {
                '*' -> operator.add(Long::times)
                '+' -> operator.add(Long::plus)
                '(' -> left.add(null)
                ')' -> {
                    val number = left.removeLast()!!
                    if (operator.isEmpty()) {
                        require(left.last() == null)
                        left[left.size - 1] = number
                    } else {
                        handleNumber(left, operator, number)
                    }
                }
                else -> {
                    val number = token.toString().toLong()
                    handleNumber(left, operator, number)
                }
            }
        }
        return left.single()!!
    }

    fun solvePart1(): Long {
        return problems.sumOf { calculate(it) }
    }

    sealed class Expression {
        data class Operator(val op: Char) : Expression()
        data class Literal(val value: Long) : Expression()
        data class Complex(val list: MutableList<Expression>) : Expression()
    }

    private fun parseInput(problem: List<Char>): Expression {
        val ret = mutableListOf<Expression>()
        val expressionInProgress = mutableListOf<Expression.Complex>()
        for (token in problem) {
            when (token) {
                '(' -> {
                    expressionInProgress.add(Expression.Complex(mutableListOf()))
                }
                ')' -> {
                    val finishedExpression = expressionInProgress.removeLast()
                    if (expressionInProgress.isNotEmpty()) {
                        expressionInProgress.last().list.add(finishedExpression)
                    } else {
                        ret.add(finishedExpression)
                    }
                }
                else -> {
                    val toAdd = if(token in listOf('+', '*')) Expression.Operator(token) else Expression.Literal(token.toString().toLong())
                    if (expressionInProgress.isNotEmpty()) {
                        expressionInProgress.last().list.add(toAdd)
                    } else {
                        ret.add(toAdd)
                    }
                }
            }
        }
        return Expression.Complex(ret)
    }

    private fun calculate2(expression: Expression): Expression {
        //2 * 3 + (4 * 5)
        if (expression is Expression.Literal) {
            return expression // case for (4)
        }

        val remaining = expression
        while (remaining is Expression.Complex && remaining.list.size > 1) {
            // calculate all parenthesis first
            remaining.list.mapIndexed { index, expr -> remaining.list[index] = calculate2(expr)}

            val plus = remaining.list.withIndex().firstOrNull { it.value == Expression.Operator('+') }
            if (plus != null) {
                val sum = (remaining.list[plus.index - 1] as Expression.Literal).value + (remaining.list[plus.index + 1] as Expression.Literal).value
                remaining.list.removeAt(plus.index)
                remaining.list.removeAt(plus.index)
                remaining.list[plus.index - 1] = Expression.Literal(sum)
                continue
            } else {
                // there is no plus remaining
                val prod = (remaining.list[0] as Expression.Literal).value * (remaining.list[2]  as Expression.Literal).value
                remaining.list.removeAt(1)
                remaining.list.removeAt(1)
                remaining.list[0] = Expression.Literal(prod)
                continue
            }
        }
        if (remaining is Expression.Complex) {
            return remaining.list.first() // when a complex has been reduced to just one literal
        }
        return remaining
    }

    fun solvePart2(): Long {
        val x  = problems.map { parseInput(it) }.map { calculate2(it) }.sumOf { (it as Expression.Literal).value }
        return x
    }
}