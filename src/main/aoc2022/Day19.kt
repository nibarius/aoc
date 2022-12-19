package aoc2022

import Search
import kotlin.math.max

class Day19(input: List<String>) {

    companion object {
        const val numberOfMinutes = 26
    }

    private data class Blueprint(
        val id: Int, val ore: Int, val clay: Int, val obsidianOre: Int, val obsidianClay: Int,
        val geodeOre: Int, val geodeObsidian: Int
    ) {

        fun fastestWayToObsidianRobot(): Set<State> {
            val g = Graph(this)
            val start = State(1, 0, 0, 0, 0, 1, 0, 0, 0)
            val res = Search.djikstra2(g, start, { s -> s.obsidian == 1 })
            val withRobot = res.cost.filterKeys { it.obsidianRobots == 1 }
            val time = withRobot.minBy { it.key.time }.key.time
            val candidates = withRobot.filter { it.key.time == time }.keys
            return candidates
        }

        fun fastestWayToGeodeRobot(start: State): Set<State> {
            val g = Graph(this)
            // when we have harvested 1 geode it's the turn after we got the robot
            val res = Search.djikstra2(g, start, { s -> s.geode == 1 })
            val withRobot = res.cost.filterKeys { it.geodeRobots == 1 }
            if (withRobot.isEmpty()) {
                return setOf()
            }
            val time = withRobot.minBy { it.key.time }.key.time
            val candidates = withRobot.filter { it.key.time == time }.keys
            return candidates
        }

        fun mostGeodesOpened(start: State): Int {
            val g = Graph(this)
            val res = Search.djikstra2(g, start, { s -> s.time == numberOfMinutes })
            val goal = res.cost.filterKeys { it.time == numberOfMinutes - 1 }
            if (goal.isEmpty()) {
                return 0
            }
            val max = goal.maxBy { it.key.geode }.key.geode
            return max
        }
    }

    data class State(
        val time: Int, val ore: Int, val clay: Int, val obsidian: Int, val geode: Int,
        val oreRobots: Int, val clayRobots: Int, val obsidianRobots: Int, val geodeRobots: Int
    ) {
        fun hasLowerTimeAndSameRobots(other: State): Boolean {
            return time < other.time && oreRobots == other.oreRobots &&
                    clayRobots == other.clayRobots && obsidianRobots == other.obsidianRobots &&
                    geodeRobots == other.geodeRobots
        }
    }

    private class Graph(val blueprint: Blueprint) : Search.WeightedGraph<State> {
        val seen = mutableSetOf<State>()

        override fun neighbours(id: State): List<State> {
            if (id.time == numberOfMinutes) {
                return listOf()
            }
            val ret = mutableListOf<State>()
            val nextTime = id.time + 1
            val nextOre = id.ore + id.oreRobots
            val nextClay = id.clay + id.clayRobots
            val nextObsidian = id.obsidian + id.obsidianRobots
            val nextGeode = id.geode + id.geodeRobots
            if (id.ore >= blueprint.ore) {
                val tmp = State(
                    nextTime, nextOre - blueprint.ore, nextClay, nextObsidian, nextGeode,
                    id.oreRobots + 1, id.clayRobots, id.obsidianRobots, id.geodeRobots
                )
                if (seen.none { it.hasLowerTimeAndSameRobots(tmp) }) {
                    ret.add(tmp)
                }
            }
            if (id.ore >= blueprint.clay) {
                val tmp = State(
                    nextTime, nextOre - blueprint.clay, nextClay, nextObsidian, nextGeode,
                    id.oreRobots, id.clayRobots + 1, id.obsidianRobots, id.geodeRobots
                )

                if (seen.none { it.hasLowerTimeAndSameRobots(tmp) }) {
                    ret.add(tmp)
                }
            }
            if (id.ore >= blueprint.obsidianOre && id.clay >= blueprint.obsidianClay) {
                val tmp =
                    State(
                        nextTime, nextOre - blueprint.obsidianOre, nextClay - blueprint.obsidianClay,
                        nextObsidian, nextGeode, id.oreRobots, id.clayRobots, id.obsidianRobots + 1, id.geodeRobots
                    )
                if (seen.none { it.hasLowerTimeAndSameRobots(tmp) }) {
                    ret.add(tmp)
                }
            }
            if (id.ore >= blueprint.geodeOre && id.obsidian >= blueprint.geodeObsidian) {
                val tmp = State(
                    nextTime, nextOre - blueprint.geodeOre, nextClay, nextObsidian - blueprint.geodeObsidian,
                    nextGeode, id.oreRobots, id.clayRobots, id.obsidianRobots, id.geodeRobots + 1
                )
                if (seen.none { it.hasLowerTimeAndSameRobots(tmp) }) {
                    ret.add(tmp)
                }
            }
            // Todo: find right amount
            //if (id.ore < max(blueprint.ore,blueprint.clay) + max(blueprint.obsidianOre, blueprint.geodeOre)) {
            if (id.ore < 8) {
                // don't accumulate more ore that to be able to build a obsidian or geode robot at any time
                ret.add(
                    State(
                        nextTime, nextOre, nextClay, nextObsidian, nextGeode,
                        id.oreRobots, id.clayRobots, id.obsidianRobots, id.geodeRobots
                    )
                )
            }

            seen.addAll(ret)
            return ret
        }

        override fun cost(from: State, to: State): Float {
            return 1f
            val oreDiff = (to.ore - from.ore)
            val clayDiff = (to.clay - from.clay)
            val obsidianDiff = (to.obsidian - from.obsidian)
            val oreCost = if (from.ore > max(blueprint.ore, blueprint.clay)) 10 else 1
            return oreCost.toFloat() // it's bad to accumulate ore, should this be in neibhgours instead? don't accumulate if you can build robot
        }

    }

    private val blueprints = input.map { line ->
        val regex =
            "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.".toRegex()
        val (id, o, c, oo, oc, go, gob) = regex.find(line)!!.destructured
        Blueprint(id.toInt(), o.toInt(), c.toInt(), oo.toInt(), oc.toInt(), go.toInt(), gob.toInt())
    }

    private fun calculateOneBlueprint(bp: Blueprint): Int {
        val a = bp.fastestWayToObsidianRobot()
        val bnew = a.flatMap { bp.fastestWayToGeodeRobot(it) }
        if (bnew.isEmpty()) {
            return 0
        }
        val min = bnew.minBy { it.time }.time
        val candidates = bnew.filter { it.time == min }
        val ret = candidates.maxOf { bp.mostGeodesOpened(it) }
        return ret
    }

    /**
     * Current approach: First search for the quickest way of making an obsidian robot as obsidian is required to make
     * a geode robot. Then from all possibilities that results in an obsidian robot in minimum time, try making a
     * geode robot as quick as possible. Then finally simulate all possible outcomes from there to find the right
     * amount of geode.
     *
     * Thoughts:
     *  - Could there be a case when waiting 1-2 minutes with creating the first obsidian robot makes it faster to
     *  create the first geode robot?
     *  - Once a Geode robot has been made, there's no point in simulating to the end, it could be possible to only
     *  see if it's possible to make another robot before time runs out.
     *  - Could there be some case where it's not good to build a Geode robot as soon as possible?
     *  - Is it somehow possible to know if one state is better than another by just comparing them?
     *
     */

    // passes test example in 6 seconds, fails on real input in 1 minute
    fun solvePart1(): Int { // 1075 too low, // 1562 too low
        return blueprints.sumOf { calculateOneBlueprint(it) * it.id }
        //val tmp = State(19,2,17,3,0,1,4,2,1)
        val a = blueprints[1].fastestWayToObsidianRobot()
        val bnew = a.flatMap { blueprints[1].fastestWayToGeodeRobot(it) }
        val min = bnew.minBy { it.time }.time
        val candidates = bnew.filter { it.time == min }
        val ret = candidates.maxOf { blueprints[1].mostGeodesOpened(it) }
        return 1
        /*val b = blueprints[1].fastestWayToGeodeRobot(a)
        val c = b.map { blueprints[1].mostGeodesOpened(it) }
        //return blueprints[0].mostGeodesOpened() //todo: blueprint 2 gives right answer, but blueprint 1 is wrong
        //return blueprints.sumOf { it.mostGeodesOpened() * it.id }
        return c.max()*/
    }

    fun solvePart2(): Int {
        return -1
    }
}