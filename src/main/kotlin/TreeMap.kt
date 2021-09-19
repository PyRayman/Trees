

interface TreeMap<K: Comparable<K>,V>{
    fun find(key:K) : Node<K,V>?
    fun insert(key:K, value:V, pr:Int? = null )
    fun remove(key:K) : Boolean
}

