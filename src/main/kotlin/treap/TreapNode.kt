import Node

data class TreapNode<K: Comparable<K>,V>(var key:K, var value:V, var priority:Int, var parent: TreapNode<K,V>? = null ):Node<K,V>{
    var leftNode: TreapNode<K,V>? = null
    var rightNode: TreapNode<K,V>? = null

    override fun toString(): String {
        return "{ K=$key, VL=$value, PR=$priority }"
    }
}
