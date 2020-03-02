class MutableMultiMap<K, V>(private val backing: MutableMap<K, MutableList<V>> = mutableMapOf()) {
    operator fun get(key: K): MutableList<V>? = backing[key]
    fun add(key: K, value: V) {
        if (!backing.containsKey(key)) backing[key] = mutableListOf()
        backing[key]!!.add(value)
    }
}