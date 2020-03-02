package aoc2015

import MutableMultiMap

class Day19(input: List<String>) {

    private val molecule = parseMolecule(input.last())
    private val replacements = parseReplacements(input)

    private fun parseReplacements(input: List<String>) = input.dropLast(2)
            .map { line ->
                line.split(" => ").let { it.first() to it.last() }
            }.let { replacements ->
                MutableMultiMap<String, String>().apply { replacements.forEach { add(it.first, it.second) } }
            }

    private fun parseMolecule(molecule: String): List<String> {
        val atoms = mutableListOf<String>()
        var curr = ""
        var i = 0
        do {
            curr += molecule[i]
            if (i + 1 == molecule.length || molecule[i + 1].isUpperCase()) {
                atoms.add(curr)
                curr = ""
            }
        } while (++i < molecule.length)
        return atoms
    }

    private fun calibrate(): Int {
        val distinct = mutableSetOf<String>()
        for (i in molecule.indices) {
            replacements[molecule[i]]?.let { substituteList ->
                for (substitute in substituteList) {
                    val current = molecule.mapIndexed { index, s ->
                        if (index == i) {
                            substitute
                        } else {
                            s
                        }
                    }.joinToString("")
                    distinct.add(current)
                }
            }
        }
        return distinct.size
    }

    private fun fabricate(): Int {
        // There is no need to actually create the molecule, all that's needed is to know how many combinations
        // are required to create the molecule. We can safely assume that it is possible to find a way to
        // generate the molecule somehow.
        //
        // If carefully looking at the input it's possible to see that there are a couple of different kinds
        // of combinations:
        // 1. X ==> **
        // 2. X ==> *Rn*Ar
        // 3. X ==> *Rn*Y*Ar
        // 4. X ==> *Rn*Y*Y*Ar
        // (note: this only work with the actual puzzle input and not the test examples since the examples
        // have different kinds of rules like X ==> *)

        // Starting with the target molecule and running the correct combinations backward would finally
        // end up with a molecule containing only 'e'.
        //
        // Combination 1. reduces two atoms into one per iteration, so amount of required steps
        // are molecule.length - 1
        //
        // Combination 2. also reduces two atoms into one with the addition that it also removes both
        // Rn and Ar. So amount of steps are molecule.length - count(Rn, Ar) - 1
        //
        // Combination 3. and 4. are similar to combination 2 but with the addition that it also removes the
        // Y and one additional atom. So amount of steps are molecule.length - count(Rn, Ar) - 2 * count(Y) - 1

        // The final generic formula can be used on all types of molecules and calculates the amount of steps
        // needed to create that molecule without exactly knowing which exact steps are needed.
        return molecule.size - molecule.count { it in listOf("Rn", "Ar") } - 2 * molecule.count { it == "Y" } - 1
    }

    fun solvePart1(): Int {
        return calibrate()
    }

    fun solvePart2(): Int {
        return fabricate()
    }
}