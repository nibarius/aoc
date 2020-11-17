package aoc2016

class Day10(input: List<String>) {
    interface Instruction
    private data class GetInstruction(val value: Int, val to: Int) : Instruction
    private data class GiveInstruction(val who: Int,
                                       val lowTo: Int, val lowIsBot: Boolean,
                                       val highTo: Int, val highIsBot: Boolean) : Instruction

    private data class Output(val name: Int, var holds: Int = 0)
    private data class Bot(val name: Int, val holds: MutableList<Int> = mutableListOf()) {
        fun popSmall(): Int {
            val min = holds.minOrNull()!!
            holds.removeIf { it == min }
            return min
        }

        fun popLarge(): Int {
            val max = holds.maxOrNull()!!
            holds.removeIf { it == max }
            return max
        }

        fun isReady() = holds.size == 2
    }

    private val instructions = parseInput(input)
    private val bots = Array(210) { i -> Bot(i) }
    private val outputs = Array(21) { i -> Output(i) }
    private val botQueue = mutableListOf<Int>()

    private fun parseInput(input: List<String>): List<Instruction> {
        val ret = mutableListOf<Instruction>()
        input.forEach {
            val parts = it.split(" ")
            when (parts[0]) {
                "bot" -> {
                    ret.add(GiveInstruction(parts[1].toInt(),
                            parts[6].toInt(), parts[5] == "bot",
                            parts.last().toInt(), parts[parts.size - 2] == "bot"))
                }
                "value" -> {
                    ret.add(GetInstruction(parts[1].toInt(), parts.last().toInt()))
                }
            }
        }
        return ret
    }

    private fun initializeBots(): Array<Bot> {
        instructions.filterIsInstance<GetInstruction>().forEach {
            bots[it.to].holds.add(it.value)
            if (bots[it.to].isReady()) {
                botQueue.add(it.to)
            }
        }
        return bots
    }

    private fun executeInstructions(wantedLow: Int, wantedHigh: Int): Int {
        while (botQueue.size > 0) {
            val botToProcess = botQueue.removeAt(0)
            val low = bots[botToProcess].popSmall()
            val high = bots[botToProcess].popLarge()
            if (high == wantedHigh && low == wantedLow) {
                // return when the bot to search for is found
                return botToProcess
            }
            val inst = instructions.filterIsInstance<GiveInstruction>().find {
                it.who == botToProcess
            } as GiveInstruction
            if (inst.highIsBot) {
                bots[inst.highTo].holds.add(high)
                if (bots[inst.highTo].isReady()) {
                    botQueue.add(inst.highTo)
                }
            } else {
                outputs[inst.highTo].holds = high
            }

            if (inst.lowIsBot) {
                bots[inst.lowTo].holds.add(low)
                if (bots[inst.lowTo].isReady()) {
                    botQueue.add(inst.lowTo)
                }
            } else {
                outputs[inst.lowTo].holds = low
            }
        }
        return -1
    }

    fun solvePart1(wantedLow: Int, wantedHigh: Int): Int {
        initializeBots()
        return executeInstructions(wantedLow, wantedHigh)
    }

    fun solvePart2(): Int {
        initializeBots()
        executeInstructions(-1, -1) // Not interested in finding a certain bot
        return outputs[0].holds * outputs[1].holds * outputs[2].holds
    }
}
