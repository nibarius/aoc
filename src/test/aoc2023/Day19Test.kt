package test.aoc2023

import aoc2023.Day19
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day19Test {
    private val exampleInput = """
        px{a<2006:qkq,m>2090:A,rfg}
        pv{a>1716:R,A}
        lnx{m>1548:A,A}
        rfg{s<537:gd,x>2440:R,A}
        qs{s>3448:A,lnx}
        qkq{x<1416:A,crn}
        crn{x>2662:A,R}
        in{s<1351:px,qqz}
        qqz{s>2770:qs,m<1801:hdj,R}
        gd{a>3333:R,R}
        hdj{m>838:A,pv}

        {x=787,m=2655,a=1222,s=2876}
        {x=1679,m=44,a=2067,s=496}
        {x=2036,m=264,a=79,s=2244}
        {x=2461,m=1339,a=466,s=291}
        {x=2127,m=1623,a=2188,s=1013}
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day19 = Day19(exampleInput)
        assertEquals(19114, day19.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day19 = Day19(resourceSplitOnBlankLines("2023/day19.txt"))
        assertEquals(353553, day19.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day19 = Day19(exampleInput)
        assertEquals(167409079868000, day19.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day19 = Day19(resourceSplitOnBlankLines("2023/day19.txt"))
        assertEquals(124615747767410, day19.solvePart2())
    }
}