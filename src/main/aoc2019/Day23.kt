package aoc2019

class Day23(input: List<String>) {
    val parsedInput = input.map { it.toLong() }

    data class Packet(val address: Long, val x: Long, val y: Long) {
        // True if this is a magic packet that holds the answer to the puzzle
        var isMagic = false
    }

    abstract class NetworkEquipment {
        abstract fun tick(): Packet? // Run one instruction and return a Packet if one was sent
        abstract fun receive(packet: Packet)
        open fun isIdling() = true // By default network equipment don't block the network from becoming idle
    }

    class Computer(private val address: Long, nic: List<Long>) : NetworkEquipment() {
        val intcode = Intcode(nic).apply { input.add(address) }

        override fun isIdling() = intcode.input.isEmpty() && lastIoWasSend

        override fun receive(packet: Packet) {
            intcode.input.add(packet.x)
            intcode.input.add(packet.y)
        }

        override fun tick(): Packet? {
            handleInput()
            intcode.runOneInstruction()
            return handleOutput()
        }

        private var lastIoWasSend = false

        // Add -1 to input if about to read input without any input available
        private fun handleInput() = with(intcode) {
            if (nextInstructionIsInput() && input.isEmpty()) {
                input.add(-1L)
                lastIoWasSend = true
            }
        }

        private val outputQueue = mutableListOf<Long>()

        // If there is output, accumulate it until the intcode have given the complete output
        // then return it so it can be sent.
        private fun handleOutput(): Packet? = with(intcode) {
            if (output.isNotEmpty()) {
                lastIoWasSend = false
                outputQueue.add(output.removeAt(0))
                if (outputQueue.size == 3) {
                    return Packet(outputQueue[0], outputQueue[1], outputQueue[2])
                            .also { outputQueue.clear() }
                }
            }
            return null
        }
    }

    class Nat(private val network: Map<Long, NetworkEquipment>, private val part: Int) : NetworkEquipment() {
        private var lastPacket: Packet? = null
        private val lastTwo = LongArray(2) { -1L * it }
        private var i = 0
        private fun twiceInARow() = lastTwo[0] == lastTwo[1]

        override fun receive(packet: Packet) {
            lastPacket = packet
            lastTwo[i++ % 2] = packet.y
        }

        override fun tick() = if (part == 1) {
            lastPacket?.apply { isMagic = true }
        } else {
            lastPacket
                    ?.takeIf { network.values.all { it.isIdling() } }
                    ?.let { Packet(0, it.x, it.y).apply { isMagic = twiceInARow() } }
        }
    }

    // Setup the network and boot all computers. When the Nat returns a magic package on tick() the y value
    // of the package is the solution to the puzzle
    private fun setupNetwork(part: Int): Long {
        val network = mutableMapOf<Long, NetworkEquipment>()
        network[255] = Nat(network, part)
        repeat(50) {
            val address = it.toLong()
            network[address] = Computer(address, parsedInput)
        }

        while (true) {
            network.values.forEach {
                val packet = it.tick()
                if (packet != null) {
                    if (packet.isMagic) {
                        return packet.y
                    }
                    network[packet.address]!!.receive(packet)
                }
            }
        }
    }

    fun solvePart1(): Long {
        return setupNetwork(1)
    }

    fun solvePart2(): Long {
        return setupNetwork(2)
    }
}