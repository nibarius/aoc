package aoc2024

class Day22(input: List<String>) {

    private val initialSecrets = input.map { it.toLong() }

    private fun mix(secret: Long, value: Long) = secret xor value
    private fun prune(secret: Long) = secret % 16777216
    private fun Long.doStep(calculation: (Long) -> Long) = prune(mix(this, calculation(this)))

    private fun nextSecret(secret: Long): Long {
        return secret
            .doStep { it * 64 }
            .doStep { it / 32 }
            .doStep { it * 2048 }
    }

    fun solvePart1(): Long {
        return initialSecrets.sumOf { initial ->
            generateSequence(initial) { nextSecret(it) }.take(2001).last()
        }
    }

    private data class PriceChangeSequence(val a: Byte, val b: Byte, val c: Byte, val d: Byte) {
        companion object {
            private fun diff(a: Byte, b: Byte) = (b - a).toByte()

            fun generate(a: Byte, b: Byte, c: Byte, d: Byte, e: Byte): PriceChangeSequence {
                return PriceChangeSequence(diff(a, b), diff(b, c), diff(c, d), diff(d, e))
            }
        }
    }

    fun solvePart2(): Int {
        val bananas = mutableMapOf<PriceChangeSequence, Int>()
        val monkeyPrices = initialSecrets.map { initial ->
            generateSequence(initial) { nextSecret(it) }
                .map { (it % 10).toByte() }
                .take(2001)
                .toList()
        }
        monkeyPrices.forEach { priceList ->
            val seen = mutableSetOf<PriceChangeSequence>()
            priceList.windowed(5).forEach { (a, b, c, d, e) ->
                val key = PriceChangeSequence.generate(a, b, c, d, e)
                if (key !in seen) {
                    // Each monkey buys only the first time they see a given key
                    bananas[key] = bananas.getOrDefault(key, 0) + e % 10
                    seen.add(key)
                }
            }
        }
        return bananas.values.max()
    }
}