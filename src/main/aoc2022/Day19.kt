package aoc2022

import Search
import Search.maximizeValueDfs

private fun Map<Day19.Type, Int>.ore() = getOrDefault(Day19.Type.ORE, 0)
private fun Map<Day19.Type, Int>.clay() = getOrDefault(Day19.Type.CLAY, 0)
private fun Map<Day19.Type, Int>.obsidian() = getOrDefault(Day19.Type.OBSIDIAN, 0)
private fun Map<Day19.Type, Int>.geode() = getOrDefault(Day19.Type.GEODE, 0)
private fun Map<Day19.Type, Int>.getResource(type: Day19.Type) = getOrDefault(type, 0)

class Day19(input: List<String>) {

    private data class Blueprint(
        val id: Int,
        val oreRobotOreCost: Int,
        val clayRobotOreCost: Int,
        val obsidianRobotOreCost: Int,
        val obsidianRobotClayCost: Int,
        val geodeRobotOreCost: Int,
        val geodeRobotObsidianCost: Int
    ) {
        /**
         * Maximum amount of ore that can be consumed at once with this blueprint.
         */
        fun maxOreCost(): Int {
            return listOf(oreRobotOreCost, clayRobotOreCost, oreRobotOreCost, geodeRobotOreCost).max()
        }

        /**
         * The
         */
        fun buildCost(target: Type, timeLeft: Int): Inventory {
            return when (target) {
                Type.ORE -> Inventory(
                    mapOf(Type.ORE to -oreRobotOreCost),
                    mapOf(Type.ORE to 1)
                )

                Type.CLAY -> Inventory(
                    mapOf(Type.ORE to -clayRobotOreCost),
                    mapOf(Type.CLAY to 1)
                )

                Type.OBSIDIAN -> Inventory(
                    mapOf(
                        Type.ORE to -obsidianRobotOreCost,
                        Type.CLAY to -obsidianRobotClayCost
                    ), mapOf(Type.OBSIDIAN to 1)
                )

                Type.GEODE -> Inventory(
                    mapOf(
                        Type.ORE to -geodeRobotOreCost,
                        Type.OBSIDIAN to -geodeRobotObsidianCost,
                        Type.GEODE to timeLeft - 1
                    ), mapOf()
                )
            }
        }
    }

    private data class Inventory(val resources: Map<Type, Int>, val bots: Map<Type, Int>) {

        /**
         * Returns a new Inventory that holds the state when all the bots produce their resources.
         */
        fun produce(): Inventory {
            return this + Inventory(bots, mapOf())
        }

        fun isEnoughResourcesFor(other: Inventory): Boolean {
            // cost is negative resources so when summing it needs to be non-negative to be able to afford.
            return other.resources.all { it.value + resources.getResource(it.key) >= 0 }
        }

        operator fun plus(other: Inventory): Inventory {
            val newResources = resources.toMutableMap()
            val newBots = bots.toMutableMap()
            other.resources.forEach { newResources[it.key] = newResources.getResource(it.key) + it.value }
            other.bots.forEach { newBots[it.key] = newBots.getResource(it.key) + it.value }
            return Inventory(newResources, newBots)
        }
    }

    private data class State(val timeLeft: Int, val inventory: Inventory, val wantToBuild: Type?) {
        fun enoughResourcesToBuild(blueprint: Blueprint, bot: Type) =
            inventory.isEnoughResourcesFor(blueprint.buildCost(bot, timeLeft))

        // Optimizations to reduce number of states:
        // - Don't build more bots than the amount of resources that can be consumed during one turn
        // - Don't build other bots if it's possible to build a geode bot
        // - Don't build an ore bot if it's possible to build an obsidian bot
        // - Don't try to build a bot if there are no bots that produce the required resources
        fun canBuildBot(bp: Blueprint, type: Type) = when (type) {
            Type.ORE -> inventory.bots.ore() < bp.maxOreCost() &&
                    !enoughResourcesToBuild(bp, Type.GEODE) &&
                    !enoughResourcesToBuild(bp, Type.OBSIDIAN)

            Type.CLAY -> inventory.bots.clay() < bp.obsidianRobotClayCost &&
                    !enoughResourcesToBuild(bp, Type.GEODE)

            Type.OBSIDIAN -> inventory.bots.clay() > 0 &&
                    inventory.bots.obsidian() < bp.geodeRobotObsidianCost &&
                    !enoughResourcesToBuild(bp, Type.GEODE)

            Type.GEODE -> inventory.bots.obsidian() > 0
        }
    }

    enum class Type {
        ORE,
        CLAY,
        OBSIDIAN,
        GEODE
    }

    private class Graph(val blueprint: Blueprint) : Search.WeightedGraph<State> {
        override fun neighbours(id: State): List<State> {
            return if (id.timeLeft == 0) {
                listOf()
            } else if (id.wantToBuild == null) {
                // Happens only on the first iteration when start node is passed with null as build target
                // Initially it's only possible to build ore and clay bots.
                listOf(State(id.timeLeft, id.inventory, Type.ORE), State(id.timeLeft, id.inventory, Type.CLAY))
            } else if (!id.enoughResourcesToBuild(blueprint, id.wantToBuild)) {
                // Can't afford to build the bot yet. Wait a turn to accumulate more resources
                listOf(State(id.timeLeft - 1, id.inventory.produce(), id.wantToBuild))
            } else {
                // Build the bot and queue up variants for all possible next bots
                val nextInventory = id.inventory.produce() + blueprint.buildCost(id.wantToBuild, id.timeLeft)
                return Type.entries
                    .mapNotNull { botToBuild ->
                        State(id.timeLeft - 1, nextInventory, botToBuild)
                            .takeIf { it.canBuildBot(blueprint, botToBuild) }
                    }
            }
        }

        // We want to maximize the number of geodes opened
        override fun cost(from: State, to: State): Float {
            return to.inventory.resources.geode().toFloat()
        }
    }

    private val blueprints = input.map { line ->
        val regex =
            "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.".toRegex()
        val (id, o, c, oo, oc, go, gob) = regex.find(line)!!.destructured
        Blueprint(id.toInt(), o.toInt(), c.toInt(), oo.toInt(), oc.toInt(), go.toInt(), gob.toInt())
    }

    private val startInventory = Inventory(
        resources = mapOf(Type.ORE to 0, Type.CLAY to 0, Type.OBSIDIAN to 0, Type.GEODE to 0),
        bots = mapOf(Type.ORE to 1, Type.CLAY to 0, Type.OBSIDIAN to 0)
    )

    // All bots are built on ore + one more material that's used exclusively for that bot
    // Assume infinite ore and that multiple bots can be built on each turn.
    // Best case: Build one new bot of every kind that's possible on every turn
    private fun bestCaseEstimate(
        blueprint: Blueprint,
        from: State
    ): Float {
        val timeLeft = from.timeLeft
        var clay = from.inventory.resources.clay()
        var clayBots = from.inventory.bots.clay()
        var obsidian = from.inventory.resources.obsidian()
        var obsidianBots = from.inventory.bots.obsidian()
        var geodeBots = 0
        var geodesToAdd = 0
        repeat(timeLeft) {
            geodesToAdd += geodeBots
            if (obsidian >= blueprint.geodeRobotObsidianCost) {
                geodeBots += 1
                obsidian -= blueprint.geodeRobotObsidianCost
            }
            obsidian += obsidianBots

            if (clay >= blueprint.obsidianRobotClayCost) {
                obsidianBots += 1
                clay -= blueprint.obsidianRobotClayCost
            }
            clay += clayBots

            clayBots++
        }
        return from.inventory.resources.geode() + geodesToAdd.toFloat()
    }

    private fun mostGeodesOpened(time: Int, blueprint: Blueprint): Int {
        val start = State(time, startInventory, null)
        val g = Graph(blueprint)
        val res = maximizeValueDfs(g, start, { it.timeLeft == 0 }, { state -> bestCaseEstimate(blueprint, state) })
        return res.second.cost[res.first]!!.toInt()
    }

    fun solvePart1(): Int {
        return blueprints.sumOf { mostGeodesOpened(24, it) * it.id }
    }

    fun solvePart2(): Int {
        return blueprints
            .take(3)
            .map { mostGeodesOpened(32, it) }
            .reduce(Int::times)
    }
}