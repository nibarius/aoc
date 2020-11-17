package aoc2018

import kotlin.math.min

class Day24(input: List<String>) {

    data class Group(val team: String, val id: Int, val units: Int, val hp: Int, val attack: Int, val attackType: String, val initiative: Int,
                     val weaknesses: Set<String>, val immunities: Set<String>) {
        var liveUnits = units
        var boost = 0
        val isAlive: Boolean
            get() = liveUnits > 0
        val effectivePower: Int
            get() = liveUnits * (attack + if (team == "Immune System") boost else 0)

        fun damageToTake(damage: Int, type: String) = when {
            immunities.contains(type) -> 0
            weaknesses.contains(type) -> 2 * damage
            else -> damage
        }

        fun takeDamage(damage: Int, type: String): Int {
            val killedUnits = min(liveUnits, damageToTake(damage, type) / hp)
            liveUnits -= killedUnits
            return killedUnits
        }
    }

    private val groups = parseInput(input)

    private fun parseInput(input: List<String>): List<Group> {
        var i = 0
        var id = 1
        val groups = mutableListOf<Group>()
        while (i < input.size) {
            val team = input[i++].dropLast(1)
            while (i < input.size && input[i].isNotEmpty()) {
                groups.add(parseGroup(team, id++, input[i++]))
            }
            i++ // skip blank line between the two teams
            id = 1
        }
        return groups
    }

    // 18 units each with 729 hit points (weak to fire; immune to cold, slashing)
    // with an attack that does 8 radiation damage at initiative 10
    fun parseGroup(team: String, id: Int, rawGroup: String): Group {
        val parts = rawGroup.split(" ")
        val units = parts.first().toInt()
        val hp = parts[4].toInt()
        val attackPart = rawGroup.substringAfter("attack that does ").split(" ")
        val attack = attackPart.first().toInt()
        val attackType = attackPart[1]
        val initiative = attackPart.last().toInt()
        val immunities = mutableSetOf<String>()
        val weaknesses = mutableSetOf<String>()
        val statusPart = rawGroup.substringAfter("(", "").substringBefore(")")
        if (statusPart.isNotEmpty()) {
            statusPart.split("; ").forEach {
                when {
                    it.startsWith("weak to") -> {
                        it.substringAfter("weak to ").split(", ").forEach { weak -> weaknesses.add(weak) }
                    }
                    it.startsWith("immune to") -> {
                        it.substringAfter("immune to ").split(", ").forEach { weak -> immunities.add(weak) }
                    }
                }
            }
        }
        return Group(team, id, units, hp, attack, attackType, initiative, weaknesses, immunities)
    }


    private fun selectTargets(): Map<Group, Group> {
        val selectedTargets = mutableMapOf<Group, Group>()
        val toSelect = groups.filter { it.isAlive }
                .sortedWith(compareBy({ -it.effectivePower }, { -it.initiative })).toMutableList()
        val availableTargets = groups.filter { it.isAlive }.toMutableList()
        while (toSelect.isNotEmpty()) {
            val attacker = toSelect.removeAt(0)
            val target = availableTargets.asSequence()
                    .filter { attacker.team != it.team }
                    .filter { it.damageToTake(attacker.effectivePower, attacker.attackType) > 0 }
                    .sortedWith(compareBy(
                            { -it.damageToTake(attacker.effectivePower, attacker.attackType) },
                            { -it.effectivePower },
                            { -it.initiative }
                    ))
                    .firstOrNull()
            if (target != null) {
                selectedTargets[attacker] = target
                availableTargets.remove(target)
            }
        }
        return selectedTargets
    }

    private fun attackTargets(targets: Map<Group, Group>): Int {
        val toAttack = groups.filter { it.isAlive }.sortedBy { -it.initiative }.toMutableList()
        var totalKills = 0
        while (toAttack.isNotEmpty()) {
            val attacker = toAttack.removeAt(0)
            if (attacker.isAlive && targets.containsKey(attacker)) {
                totalKills += targets.getValue(attacker).takeDamage(attacker.effectivePower, attacker.attackType)
            }
        }
        return totalKills
    }

    @Suppress("unused")
    private fun printTeamInfo() {
        println("Immune System:")
        groups.filter { it.team == "Immune System" && it.isAlive }.sortedWith(compareBy { it.id }).forEach {
            println("Group ${it.id} contains ${it.liveUnits} units")
        }
        println("Infection:")
        groups.filter { it.team == "Infection" && it.isAlive }.sortedWith(compareBy { it.id }).forEach {
            println("Group ${it.id} contains ${it.liveUnits} units")
        }
        println()
    }

    private fun fight() {
        while (groups.groupBy { it.team }.values.all { teamGroups -> teamGroups.any { it.isAlive } }) {
            // While all (both) teams have any group that is still alive, continue fighting
            val targets = selectTargets()
            val killed = attackTargets(targets)
            if (killed == 0) {
                // None of the teams manages to kill anyone so abort and call it a draw.
                return
            }
        }
    }

    private fun reset(boost: Int) {
        groups.forEach {
            it.liveUnits = it.units
            it.boost = boost
        }
    }

    private fun fightWithBoost(boost: Int): Boolean {
        reset(boost)
        fight()
        return groups.filter { it.isAlive }.all { it.team == "Immune System" }
    }

    fun solvePart1(): Int {
        fight()
        return groups.filter { it.isAlive }.sumBy { it.liveUnits }
    }

    fun solvePart2(): Int {
        // Fight with increasing boost until immune system wins.
        generateSequence(1) { it + 1 }.map { fightWithBoost(it) }.first { it }
        return groups.filter { it.isAlive }.sumBy { it.liveUnits }
    }
}