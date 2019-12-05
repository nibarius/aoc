package aoc2019

class Intcode(private val initialMemory: List<Int>, val input: MutableList<Int> = mutableListOf()) {
    private var instructionPointer = 0
    private val memory = initialMemory.toMutableList()
    fun dumpMemory() = memory.toList()
    val output = mutableListOf<Int>()

    sealed class Instruction(val numParams: Int) {
        class Add : Instruction(3)
        class Multiply : Instruction(3)
        class Input : Instruction(1)
        class Output : Instruction(1)
        class JumpIfTrue : Instruction(2)
        class JumpIfFalse : Instruction(2)
        class LessThan : Instruction(3)
        class Equals : Instruction(3)
        class Terminate : Instruction(0)

        fun length() = numParams + 1

        companion object {
            fun fromOpcode(opcode: Int): Instruction = when (val c = opcode.toString().takeLast(2).toInt()) {
                1 -> Add()
                2 -> Multiply()
                3 -> Input()
                4 -> Output()
                5 -> JumpIfTrue()
                6 -> JumpIfFalse()
                7 -> LessThan()
                8 -> Equals()
                99 -> Terminate()
                else -> throw RuntimeException("Invalid opcode encountered: $c")
            }
        }
    }

    data class Parameter(val mode: Mode, val value: Int) {
        enum class Mode {
            Position,
            Immediate,
        }

        companion object {
            fun make(rawMode: Int, value: Int): Parameter {
                val mode = when (rawMode) {
                    0 -> Mode.Position
                    1 -> Mode.Immediate
                    else -> throw java.lang.RuntimeException("Unknown parameter mode: $rawMode")
                }
                return Parameter(mode, value)
            }
        }
    }

    // overload get to be able to read data using a Parameter as array index. Example: val x = memory[parameter]
    private operator fun MutableList<Int>.get(parameter: Parameter): Int {
        return when (parameter.mode) {
            Parameter.Mode.Position -> this[parameter.value]
            Parameter.Mode.Immediate -> parameter.value
        }
    }

    // overload set to be able to write data using a Parameter as array index. Example: memory[parameter] = 1
    private operator fun MutableList<Int>.set(parameter: Parameter, value: Int) {
        when (parameter.mode) {
            Parameter.Mode.Position -> this[parameter.value] = value
            Parameter.Mode.Immediate -> throw RuntimeException("Immediate mode not supported for assignment")
        }
    }

    private fun getCurrentInstructionAndParameters(): Pair<Instruction, List<Parameter>> {
        val address = instructionPointer
        val instruction = Instruction.fromOpcode(memory[address])

        val modes = memory[address].toString().dropLast(2)
                .padStart(3, '0')
                .reversed()
                .map { it.toString().toInt() }

        val params = (0 until instruction.numParams)
                .map { Parameter.make(modes[it], memory[address + 1 + it]) }

        return Pair(instruction, params)
    }

    fun run() {
        while (instructionPointer < memory.size) {
            val (instruction, params) = getCurrentInstructionAndParameters()
            when (instruction) {
                is Instruction.Terminate -> return
                is Instruction.Add -> memory[params[2]] = memory[params[0]] + memory[params[1]]
                is Instruction.Multiply -> memory[params[2]] = memory[params[0]] * memory[params[1]]
                is Instruction.Input -> memory[params[0]] = input.removeAt(0)
                is Instruction.Output -> output.add(memory[params[0]])
                is Instruction.JumpIfTrue -> if (memory[params[0]] != 0) {
                    instructionPointer = memory[params[1]] - instruction.length()
                }
                is Instruction.JumpIfFalse -> if (memory[params[0]] == 0) {
                    instructionPointer = memory[params[1]] - instruction.length()
                }
                is Instruction.LessThan -> memory[params[2]] = if (memory[params[0]] < memory[params[1]]) 1 else 0
                is Instruction.Equals -> memory[params[2]] = if (memory[params[0]] == memory[params[1]]) 1 else 0
            }
            instructionPointer += instruction.length()
        }
    }

    // Reinitialize the computer and optionally override parts of the memory with the given values
    // memoryOverride is a list of <address, value> pairs.
    fun reinitialize(memoryOverride: List<Pair<Int, Int>> = listOf()) {
        memory.clear()
        memory.addAll(initialMemory)
        input.clear()
        output.clear()
        memoryOverride.forEach {(address, value) ->
            memory[address] = value
        }
        instructionPointer = 0
    }
}