package aoc2015

import java.util.*
import kotlin.math.max

class Day22(input: List<String>) {

    private sealed class Spell(val mana: Int, val damage: Int = 0, val heal: Int = 0,
                               val armor: Int = 0, val recharge: Int = 0, val duration: Int) {
        class MagicMissile : Spell(mana = 53, damage = 4, duration = 1)
        class Drain : Spell(mana = 73, damage = 2, heal = 2, duration = 1)
        class Shield : Spell(mana = 113, armor = 7, duration = 6)
        class Poison : Spell(mana = 173, damage = 3, duration = 6)
        class Recharge : Spell(mana = 229, recharge = 101, duration = 5)

        companion object {
            val spells = listOf(MagicMissile(), Drain(), Shield(), Poison(), Recharge())
        }
    }

    private data class State(val manaSpent: Int, var hp: Int, var mana: Int, var armor: Int,
                             var bossHp: Int, val activeSpells: MutableMap<Spell, Int>)

    private val bossAttack = input[1].substringAfter(": ").toInt()
    private val initialBossHp = input[0].substringAfter(": ").toInt()

    /**
     * Apply the effects of all active spells and reduce their duration,
     * remove spells that have expired.
     *  @return true if this killed the boss
     */
    private fun applySpellEffects(state: State): Boolean {
        with(state) {
            activeSpells.keys.forEach { spell ->
                bossHp -= spell.damage
                hp += spell.heal
                armor = max(armor, spell.armor)
                mana += spell.recharge
                activeSpells[spell] = activeSpells[spell]!! - 1 // Reduce duration by one
            }
            activeSpells.keys.removeIf { activeSpells[it] == 0 } // Remove expired effects
            if (activeSpells.none { it.key is Spell.Shield }) armor = 0 // Reset shield effect if it expired
            return bossHp <= 0
        }
    }

    // Inline extension function to make code more readable. The boolean is true if the boss died.
    private inline fun Boolean.andIfBossDied(onBossDied: () -> Unit) {
        if (this) onBossDied()
    }

    // Fight until the boss dies in a BFS manner, but add and remove to the queue in the middle
    // of the loop to get a more natural flow (the loop starts at the start of the player turn
    // and ends at the end of the boss turn).
    private fun fight(hard: Boolean = false): Int {
        val toCheck = PriorityQueue<State>(compareBy { state -> state.manaSpent })
        var current = State(0, 50, 500, 0, initialBossHp, mutableMapOf())
        do {
            // Start of player turn
            if (hard) current.hp--

            // If the player has already died don't apply any effects (this may increase the player hp)
            // or queue any new player actions. Skip directly ahead to when the next player is popped
            // from the queue.
            if (current.hp > 0) {
                applySpellEffects(current).andIfBossDied { return current.manaSpent }

                // Queue up all possible player actions for future checking
                Spell.spells
                        .filter { it.mana < current.mana } // Only spells that the player can cast
                        .filterNot { current.activeSpells.contains(it) } // Only spells that are not active
                        .forEach { spell ->
                            toCheck.add(current.copy(
                                    manaSpent = current.manaSpent + spell.mana,
                                    mana = current.mana - spell.mana,
                                    activeSpells = current.activeSpells
                                            .toMutableMap()
                                            .apply { this[spell] = spell.duration }
                            ))
                        }
            }

            // Take the most promising queued possibilities (least mana spent) and see how that goes
            current = toCheck.remove()

            //Boss turn,
            applySpellEffects(current).andIfBossDied { return current.manaSpent }

            //boss attacks
            current.hp -= bossAttack - current.armor
        } while (true)
    }

    fun solvePart1(): Int {
        return fight()
    }

    fun solvePart2(): Int {
        return fight(hard = true)
    }
}


