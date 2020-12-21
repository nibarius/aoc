package aoc2020

class Day21(input: List<String>) {

    private val products = input.map { line ->
        val ingredients = line.substringBefore(" (contains ").split(" ")
        val allergens = line.substringAfter("(contains ").dropLast(1).split(", ")
        ingredients to allergens
    }

    private fun examineIngredients(): Map<String, Set<String>> {
        val ingredientCanBe = products.flatMap { it.first }.toSet().associateWith { mutableSetOf<String>() }
        products.forEach { product ->
            val allOther = products - product
            val possibleAllergens = product.second
            product.first.forEach { ingredient ->
                for (allergen in possibleAllergens) {
                    val productsWithSameAllergens = allOther.filter { it.second.contains(allergen) }
                    if (productsWithSameAllergens.all { otherProduct -> ingredient in otherProduct.first }) {
                        // if this ingredient contains this allergen it must be listed in
                        // all products that lists the allergen
                        ingredientCanBe[ingredient]!!.add(allergen)
                    }
                }
            }
        }
        return ingredientCanBe
    }

    fun solvePart1(): Int {
        val safe = examineIngredients().filterValues { it.isEmpty() }.keys
        return products.flatMap { it.first }.count { it in safe }
    }

    fun solve(dangerous: Map<String, Set<String>>): MutableMap<String, String> {
        val left: MutableMap<String, MutableSet<String>> = dangerous.map { it.key to it.value.toMutableSet() }.toMap().toMutableMap()
        val known = mutableMapOf<String, String>()
        while (left.isNotEmpty()) {
            val current = left.minByOrNull { it.value.size }!!
            val ingredient = current.key
            val allergen = current.value.single()
            known[ingredient] = allergen
            left.remove(ingredient)
            left.forEach { (_, value) -> value.remove(allergen) }
        }
        return known
    }

    fun solvePart2(): String {
        val dangerousIngredients = examineIngredients().filterValues { it.isNotEmpty() }
        val solved = solve(dangerousIngredients)
        return solved.toList().sortedBy { it.second }.joinToString(",") { it.first }
    }
}