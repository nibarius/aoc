package aoc2020

class Day4(input: List<String>) {

    private data class Field(private val key: String, private val value: String) {
        private fun String.splitAt(index: Int) = take(index) to substring(index)
        fun isMandatory() = key != "cid"
        fun isValid() = when (key) {
            "byr" -> value.toInt() in 1920..2002
            "iyr" -> value.toInt() in 2010..2020
            "eyr" -> value.toInt() in 2020..2030
            "ecl" -> value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            "hcl" -> "^#[0-9a-f]{6}$".toRegex().matches(value)
            "pid" -> "^[0-9]{9}$".toRegex().matches(value)
            "hgt" -> {
                val (number, unit) = value.splitAt(value.length - 2)
                when (unit) {
                    "cm" -> number.toInt() in 150..193
                    "in" -> number.toInt() in 59..76
                    else -> false
                }
            }
            else -> true
        }
    }

    private data class Passport(private val fields: Set<Field>) {
        val hasAllMandatoryFields = fields.count { it.isMandatory() } == 7
        val isValid = hasAllMandatoryFields && fields.all { it.isValid() }
    }

    private val passports = parseInput(input)

    private fun parseInput(input: List<String>): List<Passport> {
        val fields = mutableSetOf<Field>()
        val ret = mutableListOf<Passport>()
        for (line in input) {
            if (line.isEmpty()) {
                ret.add(Passport(fields.toSet()))
                fields.clear()
                continue
            }
            line.split(" ").forEach { pair ->
                pair.split(":").let { (key, value) ->
                    fields.add(Field(key, value))
                }
            }
        }
        ret.add(Passport(fields.toSet()))
        return ret
    }

    fun solvePart1(): Int {
        return passports.count { it.hasAllMandatoryFields }
    }

    fun solvePart2(): Int {
        return passports.count { it.isValid }
    }
}