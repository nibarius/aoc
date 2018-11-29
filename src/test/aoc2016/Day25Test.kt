package test.aoc2016

import aoc2016.Assembunny
import aoc2016.Day25
import org.junit.Assert
import org.junit.Test
import resourceAsList

class Day25Test {

    // Optimization 1: replace the initial constant multiplication and addition with one addition
    private val beforeOptimization1 = listOf("cpy a d",
            "cpy 7 c",
            "cpy 365 b",
            "inc d",
            "dec b",
            "jnz b -2",
            "dec c",
            "jnz c -5")

    private val afterOptimization1 = listOf("cpy a d",
            "add 2555 d") // d += 365 * 7


    @Test
    fun testOptimization1() {
        val bunny = Assembunny(beforeOptimization1.toMutableList())
        val bunny2 = Assembunny(afterOptimization1.toMutableList())
        val a = 10
        bunny.registers["a"] = a
        bunny2.registers["a"] = a
        bunny.execute()
        bunny2.execute()
        Assert.assertEquals(bunny2.registers, bunny.registers)
        //println(bunny.registers)
    }

    // Optimization 2: Introduce a divide by two operator (with strange remainder logic)
    private val beforeOptimization2 = listOf(
            "cpy 0 a",
            "cpy 2 c     <-- Label C:                      c = 2",
            "jnz b 2     <-- Label D:    // Jump to E      while b > 0:",
            "jnz 1 6                     // Jump to F",
            "dec b       <-- Label E:                       b--",
            "dec c                                          c--",
            "jnz c -4                    // Jump to D       if c % 2 == 0",
            "inc a                                          a++",
            "jnz 1 -7                    // Jump to C      ==> a = b / 2")

    private val afterOptimization2 = listOf(
            "div2s b c",
            "cpy b a",
            "cpy 0 b")

    @Test
    fun testOptimization2() {
        for (b in 1..20) {
            val bunny = Assembunny(beforeOptimization2.toMutableList())
            val bunny2 = Assembunny(afterOptimization2.toMutableList())
            bunny.registers["b"] = b
            bunny2.registers["b"] = b
            bunny.execute()
            bunny2.execute()
            Assert.assertEquals(bunny.registers, bunny2.registers)
            println("dividing ${if (b % 2 == 0) "even" else "odd "} gives ${bunny.registers}")
        }
    }

    // Optimization 3: Introduce a sub instruction to reduce looping
    private val beforeOptimization3 = listOf(
            "cpy 2 b                     // a = divres, b = 2, c = rem",
            "jnz c 2     <-- Label C:    // Jump to D                // From here until Just before jump to label C",
            "jnz 1 4                     // Jump to E                //",
            "dec b       <-- Label D:                                // b = 2 - rem",
            "dec c                                                   // c = 0",
            "jnz 1 -4                    // Jump to C")

    private val afterOptimization3 = listOf(
            "sub 2 c",
            "cpy c b", // b = 2 - c
            "cpy 0 c") // c = 0

    @Test
    fun testOptimization3() { //Todo something feels wrong, I'm expecting B to be either 1 or 0
        for (c in 1..2) {
            val bunny = Assembunny(beforeOptimization3.toMutableList())
            val bunny2 = Assembunny(afterOptimization3.toMutableList())
            bunny.registers["c"] = c
            bunny2.registers["c"] = c
            bunny.execute()
            bunny2.execute()
            Assert.assertEquals(bunny.registers, bunny2.registers)
            println("C is $c, results in: ${bunny.registers}")
        }
    }


    // Optimization 23: Combine optimization 2 (division with strange reminder) and 3 (fix reminder) to
    // a division with a normal reminder
    private val beforeOptimization23 = listOf(
            "div2s b c                   // if b is odd c ==> 1, if b is even c ==> 2",
            "cpy b a                     // a = div result, b = 2, c = div reminder",
            "sub 2 c",
            "cpy c b                     // if div reminder is 1 ==> b = 1, if div reminder is 2 ==> b = 0",
            "cpy 0 c"
    )

    private val afterOptimization23 = listOf(
            "div2 b c                    // if b is odd c ==> 1, if b is even c ==> 0",
            "cpy b a",
            "cpy c b",
            "cpy 0 c                     // a = div result, b = reminder, c = 0"
    )

    @Test
    fun testOptimization23() {
        for (b in 1..20) {
            val bunny = Assembunny(beforeOptimization23.toMutableList())
            val bunny2 = Assembunny(afterOptimization23.toMutableList())
            bunny.registers["b"] = b
            bunny2.registers["b"] = b
            bunny.execute()
            bunny2.execute()
            Assert.assertEquals(bunny.registers, bunny2.registers)
        }
    }

    // Optimization 4: Optimize code after 23 by not moving things between registers when it's not needed
    private val beforeOptimization4 = listOf(
            "cpy a d",
            "add 2555 d                  // d += 365 * 7 <==> d = input + 2555",
            "cpy d a     <-- Label A:",
            "jnz 0 0     <-- Label B:",
            "cpy a b",
            "div2 b c",
            "cpy b a",
            "cpy c b",
            "cpy 0 c",
            "jnz 0 0"
    )

    private val afterOptimization4 = listOf(
            "cpy a d",
            "add 2555 d                  // d += 365 * 7 <==> d = input + 2555",
            "cpy d a     <-- Label A:",
            "div2 a b     <-- Label B:"
    )

    @Test
    fun testOptimization4() {
        val bunny = Assembunny(beforeOptimization4.toMutableList())
        val bunny2 = Assembunny(afterOptimization4.toMutableList())
        val a = 10
        bunny.registers["a"] = a
        bunny2.registers["a"] = a
        bunny.execute()
        bunny2.execute()
        Assert.assertEquals(bunny2.registers, bunny.registers)
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(175, Day25(resourceAsList("2016/day25_optimized.txt").toMutableList()).solvePart1())
    }

}