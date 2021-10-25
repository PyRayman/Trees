import Node

data class BinNode<K: Comparable<K>,V>(var key:K, var value:V, var parent: BinNode<K,V>? = null):Node<K,V>{
    var leftNode: BinNode<K,V>? = null

    var rightNode: BinNode<K,V>? = null

    override fun toString(): String {
        return "BinNode<key =$key ,value= $value>"
    }

}