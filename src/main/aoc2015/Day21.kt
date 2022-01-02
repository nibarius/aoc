package aoc2015

import com.marcinmoskala.math.combinations
import kotlin.math.max

class Day21(input: List<String>) {

    data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)
    data class Character(val name: String, val baseDamage: Int = 0, val baseArmor: Int = 0,
                         val equipment: List<Item> = listOf(), var hp: Int = 100) {
        val damage: Int
            get() = baseDamage + equipment.sumOf { it.damage }
        val armor: Int
            get() = baseArmor + equipment.sumOf { it.armor }
        val equipmentCost: Int
            get() = equipment.sumOf { it.cost }
    }

    private val boss = Character("Boss",
            hp = input[0].substringAfter(": ").toInt(),
            baseDamage = input[1].substringAfter(": ").toInt(),
            baseArmor = input[2].substringAfter(": ").toInt())

    private val weapons = """
        Weapons:    Cost  Damage  Armor
        Dagger        8     4       0
        Shortsword   10     5       0
        Warhammer    25     6       0
        Longsword    40     7       0
        Greataxe     74     8       0
    """.trimIndent().parseItems()

    private val armor = """
        Armor:      Cost  Damage  Armor
        No_armor      0     0       0
        Leather      13     0       1
        Chainmail    31     0       2
        Splintmail   53     0       3
        Bandedmail   75     0       4
        Platemail   102     0       5
    """.trimIndent().parseItems()

    private val rings = """
        Rings:      Cost  Damage  Armor
        No_ring_1     0     0       0
        No_ring_2     0     0       0
        Damage_+1    25     1       0
        Damage_+2    50     2       0
        Damage_+3   100     3       0
        Defense_+1   20     0       1
        Defense_+2   40     0       2
        Defense_+3   80     0       3
    """.trimIndent().parseItems()

    private fun String.parseItems(): List<Item> {
        return split("\n").drop(1).map { line ->
            val parts = line.split("\\s+".toRegex())
            Item(parts[0], parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
        }
    }

    private fun allItemCombinations(): List<List<Item>> {
        val ret = mutableListOf<List<Item>>()
        for (weapon in weapons) {
            for (a in armor) {
                val ringCombinations = rings.toSet().combinations(2)
                ringCombinations.forEach {
                    val items = mutableListOf(weapon, a).apply { addAll(it) }
                    ret.add(items)
                }
            }
        }
        return ret
    }

    /**
     * A fight until death with the boss. If the player wins it still have hp
     * remaining after the fight.
     */
    private fun fightBoss(player: Character): Boolean {
        val fighters = listOf(player, this.boss.copy())
        var round = 0
        while (true) {
            val attacker = fighters[round % 2]
            val defender = fighters[(round + 1) % 2]
            val damageMade = max(1, attacker.damage - defender.armor)
            defender.hp -= damageMade
            if (defender.hp <= 0) {
                return attacker == player
            }
            round++
        }
    }

    /**
     * @param sortOrder 1 for smallest first, -1 for largest first
     * @param hpComparison for how what is an acceptable player hp after the fight
     */
    private fun testAllItems(sortOrder: Int, hpComparison: (Int) -> Boolean): Int {
        val combinations = allItemCombinations().sortedBy { it.sumOf { item -> sortOrder * item.cost } }
        return combinations
                .map { Character("Player", equipment = it) }
                .first {
                    fightBoss(it)
                    hpComparison(it.hp)
                }.equipmentCost
    }

    fun solvePart1(): Int {
        return testAllItems(1) { hp -> hp > 0}
    }

    fun solvePart2(): Int {
        return testAllItems(-1) { hp -> hp <= 0}
    }
}