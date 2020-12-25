package aoc2020

class Day25(input: List<String>) {
    private val cardPublicKey = input[0].toLong()
    private val doorPublicKey = input[1].toLong()

    private fun solve(): Long {
        var calculatedCardKey = 1L
        var encryptionKey = 1L
        // Loop size is unknown, but when we have the correct loop size
        // transforming 7 results in the card's public key and transforming
        // the door's public key results in the encryption key. So just keep
        // looping until we've been able to generate the card's public key.
        while (true) {
            calculatedCardKey = (calculatedCardKey * 7) % 20201227L
            encryptionKey = (encryptionKey * doorPublicKey) % 20201227L
            if (calculatedCardKey == cardPublicKey) {
                return encryptionKey
            }
        }
    }

    fun solvePart1(): Long {
        return solve()
    }
}