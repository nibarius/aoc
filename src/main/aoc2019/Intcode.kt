package aoc2019

class Intcode(private val initialMemory: List<Long>, val input: MutableList<Long> = mutableListOf()) {
    private var instructionPointer = 0L
    private var relativeBase = 0L
    private val memory = mutableMapOf<Long, Long>().apply { initMemory(this) }

    private fun initMemory(mem: MutableMap<Long, Long>) {
        initialMemory.withIndex().forEach{ mem[it.index.toLong()] = it.value }
    }

    val output = mutableListOf<Long>()
    var computerState = ComputerState.NotStarted
        private set

    // Create a copy of the current computer at the current state
    fun copy(): Intcode {
        val new = Intcode(initialMemory, input.toMutableList())
        new.instructionPointer = instructionPointer
        new.relativeBase = relativeBase
        new.memory.clear()
        memory.forEach { (key, value) -> new.memory[key] = value }
        new.output.addAll(output)
        new.computerState = computerState
        return new
    }

    // compacted memory dump without real addresses beyond the initial memory, useful for simple debugging
    fun dumpMemory() = memory.values.toList()

    enum class ComputerState {
        NotStarted,
        Running,
        WaitingForInput,
        Terminated
    }

    sealed class Instruction(val numParams: Int) {
        class Add : Instruction(3)
        class Multiply : Instruction(3)
        class Input : Instruction(1)
        class Output : Instruction(1)
        class JumpIfTrue : Instruction(2)
        class JumpIfFalse : Instruction(2)
        class LessThan : Instruction(3)
        class Equals : Instruction(3)
        class AdjustRelativeBase : Instruction(1)
        class Terminate : Instruction(0)

        fun length() = numParams + 1

        companion object {
            fun fromOpcode(opcode: Long): Instruction = when (val c = opcode.toString().takeLast(2).toInt()) {
                1 -> Add()
                2 -> Multiply()
                3 -> Input()
                4 -> Output()
                5 -> JumpIfTrue()
                6 -> JumpIfFalse()
                7 -> LessThan()
                8 -> Equals()
                9 -> AdjustRelativeBase()
                99 -> Terminate()
                else -> throw RuntimeException("Invalid opcode encountered: $c")
            }
        }
    }

    data class Parameter(val mode: Mode, val value: Long) {
        enum class Mode {
            Position,
            Immediate,
            Relative,
        }

        companion object {
            fun make(rawMode: Int, value: Long): Parameter {
                val mode = when (rawMode) {
                    0 -> Mode.Position
                    1 -> Mode.Immediate
                    2 -> Mode.Relative
                    else -> throw java.lang.RuntimeException("Unknown parameter mode: $rawMode")
                }
                return Parameter(mode, value)
            }
        }
    }

    // overload get to be able to read data using a Parameter as array index. Example: val x = memory[parameter]
    private operator fun MutableMap<Long, Long>.get(parameter: Parameter): Long {
        return when (parameter.mode) {
            Parameter.Mode.Position -> this[parameter.value] ?: 0
            Parameter.Mode.Immediate -> parameter.value
            Parameter.Mode.Relative -> this[parameter.value + relativeBase] ?:0
        }
    }

    // overload set to be able to write data using a Parameter as array index. Example: memory[parameter] = 1
    private operator fun MutableMap<Long, Long>.set(parameter: Parameter, value: Long) {
        when (parameter.mode) {
            Parameter.Mode.Position -> this[parameter.value] = value
            Parameter.Mode.Immediate -> throw RuntimeException("Immediate mode not supported for assignment")
            Parameter.Mode.Relative -> this[parameter.value + relativeBase] = value
        }
    }

    private fun getCurrentInstructionAndParameters(): Pair<Instruction, List<Parameter>> {
        val address = instructionPointer
        val instruction = Instruction.fromOpcode(memory[address]!!)

        val modes = memory[address].toString().dropLast(2)
                .padStart(instruction.numParams, '0')
                .reversed()
                .map { it.toString().toInt() }

        val params = (0 until instruction.numParams)
                .map { Parameter.make(modes[it], memory[address + 1 + it]!!) }

        return Pair(instruction, params)
    }

    fun run() {
        computerState = ComputerState.Running
        while (true) {
            val (instruction, params) = getCurrentInstructionAndParameters()
            when (instruction) {
                is Instruction.Terminate -> {
                    computerState = ComputerState.Terminated
                    return
                }
                is Instruction.Add -> memory[params[2]] = memory[params[0]] + memory[params[1]]
                is Instruction.Multiply -> memory[params[2]] = memory[params[0]] * memory[params[1]]
                is Instruction.Input -> {
                    if (input.isEmpty()) {
                        computerState = ComputerState.WaitingForInput
                        return
                    } else {
                        memory[params[0]] = input.removeAt(0)
                    }
                }
                is Instruction.Output -> output.add(memory[params[0]])
                is Instruction.JumpIfTrue -> if (memory[params[0]] != 0L) {
                    instructionPointer = memory[params[1]] - instruction.length()
                }
                is Instruction.JumpIfFalse -> if (memory[params[0]] == 0L) {
                    instructionPointer = memory[params[1]] - instruction.length()
                }
                is Instruction.LessThan -> memory[params[2]] = if (memory[params[0]] < memory[params[1]]) 1 else 0
                is Instruction.Equals -> memory[params[2]] = if (memory[params[0]] == memory[params[1]]) 1 else 0
                is Instruction.AdjustRelativeBase -> relativeBase += memory[params[0]]
            }
            instructionPointer += instruction.length()
        }
    }

    // Reinitialize the computer and optionally override parts of the memory with the given values
    // memoryOverride is a list of <address, value> pairs.
    fun reinitialize(memoryOverride: List<Pair<Long, Long>> = listOf()) {
        memory.clear()
        initMemory(memory)
        input.clear()
        output.clear()
        computerState = ComputerState.NotStarted
        memoryOverride.forEach { (address, value) -> memory[address] = value }
        instructionPointer = 0
    }
}