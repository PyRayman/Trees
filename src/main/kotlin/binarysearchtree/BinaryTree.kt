package binarysearchtree

import TreeMap
import Node

data class BinNode<K: Comparable<K>,V>(var key:K, var value:V, var parent: BinNode<K,V>? = null):Node<K,V>{
    var leftNode: BinNode<K,V>? = null

    var rightNode: BinNode<K,V>? = null

    override fun toString(): String {
        return "BinNode<key =$key ,value= $value>"
    }

}

class BinaryTree<K: Comparable<K>, V> :TreeMap<K,V>, Iterable<Pair<K,V>>{
    var root: BinNode<K,V>? = null

    override fun find(key:K) : BinNode<K,V>? {
        var current = root

        while (current != null ) {
            if (key == current.key)
                return current

            if (key < current.key)
                current = current.leftNode
            else
                current = current.rightNode
        }
        return null
    }



    override fun insert( key:K, value:V, pr:Int?){
        if(root == null){
            root = BinNode(key,value)
            return
        }
        var fatherNode:BinNode<K,V>? = null 
        var currentNode:BinNode<K,V>? = root
        while(currentNode != null){
            fatherNode = currentNode
            if(key < currentNode.key){        
                currentNode = currentNode.leftNode
            }
            else if(key > currentNode.key){
                currentNode = currentNode.rightNode
            }
            else if(key == currentNode.key){ 
                currentNode.value = value  
                return
            } 
        }

        if(fatherNode == null){
            root = BinNode(key,value)
            return 
        }

        if(fatherNode.key > key){
            fatherNode.leftNode = BinNode(key,value,fatherNode)
        }
        else if(fatherNode.key < key){
            fatherNode.rightNode = BinNode(key,value,fatherNode)
        }

        
    }

    fun putAll(keys:MutableList<K>,values:MutableList<V>){
        if (keys.size != values.size ) throw(Exception("Different sizes"))
        for(i in 0 until keys.size){
            this.insert(keys[i],values[i])
        }
    }

    private fun removeNoChild(node:BinNode<K,V>, parent:BinNode<K,V>?){
        if (node.parent == null) {
            root = null
            return
        }
        if(node == parent?.leftNode){
            parent.leftNode = null
        }
        else if(node == parent?.rightNode){
            parent.rightNode = null
        }
    }

    private fun removeOneChild( node:BinNode<K,V>,parent:BinNode<K,V>?){
        if(node.leftNode == null){
            if(parent?.leftNode == node){
                parent.leftNode = node.rightNode 
            }
            else{
                parent?.rightNode = node.rightNode
            }
            node.rightNode?.parent = parent
        }
        else{
            if(parent?.leftNode == node){
                parent.leftNode = node.leftNode
            }
            else{
                parent?.rightNode = node.leftNode
            }
            node.leftNode?.parent = parent
        }
    }

    private fun removeBothChild(node:BinNode<K,V>){
        val successor: BinNode<K,V> = minNode(node.rightNode)!!
        node.key = successor.key

        if(successor.parent?.leftNode == successor){
            successor.parent?.leftNode = successor.rightNode 
            
            if(successor.rightNode != null){
                successor.rightNode!!.parent = successor.parent
            }
        }
        else{
            successor.parent?.rightNode = successor.rightNode

            if(successor.rightNode != null ){
                successor.rightNode!!.parent = successor.parent
            }
        }

    }
    override fun remove(key:K) : Boolean {
        val node = find(key) ?: return false
        val parent:BinNode<K,V>? = node.parent

        if(node.rightNode == null && node.leftNode == null){
            removeNoChild(node, parent)
            return true
        }
        else if(node.rightNode == null || node.leftNode == null){
            removeOneChild( node, parent)
            return true
        }
        else{
            removeBothChild(node)
            return true
        }

    }

    fun maxNode(node: BinNode<K, V>?): BinNode<K, V>? {
        if (node?.rightNode == null){
            return node
        }
        else{
            return maxNode(node.rightNode)
        }
    }

    fun  minNode(node:BinNode<K,V>?) : BinNode<K,V>?
    {   
        if(node?.leftNode == null){
            return node
        }
        else{
            return minNode(node.leftNode)
        }
        
    }

    private fun nextSmaller(node: BinNode<K,V>?):BinNode<K,V>?{
        var smaller = node ?: return null

        if( smaller.leftNode != null ){
            return maxNode(smaller.leftNode!!)
        }
        else if( smaller == smaller.parent?.leftNode){
            while(smaller == smaller.parent?.leftNode){
                smaller = smaller.parent!!
            }
        }
        return smaller.parent
    }

   

    override fun iterator(): Iterator<Pair<K,V>>{
        return (object: Iterator<Pair<K,V>>{
            var node =  maxNode(root)
            var next = maxNode(root)
            var last = minNode(root)

            override fun hasNext(): Boolean{
                return node != null && node!!.key >= last!!.key 
            }
            
            override fun next(): Pair<K,V>{
                next = node
                node = nextSmaller(node)
                return Pair(next!!.key, next!!.value)
            }
        })
    }
}


fun main(){
    val keys: MutableList<Int> = mutableListOf(18,11,27,7,14)
    val values: MutableList<Int> = mutableListOf(18,12,3,4,5)
    var tree: BinaryTree<Int, Int> = BinaryTree<Int,Int>()
    var secondTree = BinaryTree<Int,Int>()
    secondTree.putAll(keys,values)
    tree.putAll(keys,values)

    print(tree.root.hashCode())


}