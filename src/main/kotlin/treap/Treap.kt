package treap

import TreeMap
import TreapNode



class Treap<K: Comparable<K>, V> : TreeMap<K,V>, Iterable<TreapNode<K,V>> {
    var root: TreapNode<K,V>? = null

    private fun merge(L: TreapNode<K,V>?, R: TreapNode<K,V>?): TreapNode<K,V>?{
        if(L == null) return R
        if(R == null) return L

        if(R.priority < L.priority){
            val newRightNode = merge(L.rightNode,R)
            val returnNode = TreapNode<K,V>(L.key,L.value,L.priority)
            L.leftNode?.parent = returnNode
            newRightNode?.parent = returnNode
            returnNode.leftNode = L.leftNode
            returnNode.rightNode = newRightNode

            return returnNode
        }
        else{
            val newLeftNode = merge(L,R.leftNode)
            val returnNode = TreapNode<K,V>(R.key,R.value,R.priority)
            newLeftNode?.parent = returnNode
            R.rightNode?.parent = returnNode
            returnNode.leftNode = newLeftNode
            returnNode.rightNode = R.rightNode
            
            return returnNode
        }
    }

    private fun split(node:TreapNode<K,V>?, newValue: K ): Pair<TreapNode<K,V>?,TreapNode<K,V>?>{
        if(node == null){ return Pair(null,null)}
        
        if(node.key <= newValue){
            val splitRight = split(node.rightNode, newValue)
            splitRight.first?.parent = node
            node.rightNode = splitRight.first
            return Pair(node,splitRight.second)
        }
        else{
            
            val splitLeft = split(node.leftNode,newValue)
            splitLeft.second?.parent = node
            node.leftNode = splitLeft.second
            return Pair(splitLeft.first,node)
        }
    }
    
    override fun insert( key:K, value:V, pr:Int?){

        if(pr == null){
                throw(Exception("Parametr \"PR\" was missed "))
        }
       if(root == null){
            root = TreapNode<K,V>(key,value,priority = pr)
            return 
        }
        
        val addTreap = split(root,key)
        val resultTreap = merge(merge(addTreap.first, TreapNode(key,value,pr)),addTreap.second)
        root = resultTreap
        root?.leftNode = resultTreap!!.leftNode
        root?.rightNode = resultTreap.rightNode
        root?.parent = resultTreap.parent
        
    }

    override fun remove(key:K): Boolean{
        val deleteNode = find(key) ?: return false
        val parentNode = deleteNode.parent
        val mergedNode = merge(deleteNode.leftNode, deleteNode.rightNode)
        when{
            deleteNode.parent == null ->{
                mergedNode?.parent = null
                root = mergedNode
                return true
            }
            deleteNode == parentNode!!.leftNode ->{
                mergedNode?.parent = parentNode
                parentNode.leftNode = mergedNode
                return true
            }
            else ->{
                mergedNode?.parent = parentNode
                parentNode.rightNode = mergedNode
                return true
            }
        }

    }

   
    override fun find(key:K) : TreapNode<K,V>? {
        var current = root

        while (current != null ) {
            if (key == current.key){
                return current
            }

            if (key < current.key){
                current = current.leftNode
            }
            else{
                current = current.rightNode
            }
        }
        return null
        
    }


    fun putAll(keys:MutableList<K>,values:MutableList<V>,prioritys:MutableList<Int>){
        if (keys.size != values.size || keys.size != prioritys.size ) throw(Exception("Different sizes"))
        for(i in 0 until keys.size){
            this.insert(keys[i],values[i],prioritys[i])
        }
    }

    fun maxNode(node: TreapNode<K, V>?): TreapNode<K, V>? {
        if (node?.rightNode == null){
            return node
        }
        else{
            return maxNode(node.rightNode)
        }
    }

    fun  minNode(node:TreapNode<K,V>?) : TreapNode<K,V>?
    {   
        if(node?.leftNode == null){
            return node 
        }
        else{
            return minNode(node.leftNode)
        }
        
    }

    private fun nextSmaller(node: TreapNode<K,V>?):TreapNode<K,V>?{
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

    override fun iterator(): Iterator<TreapNode<K,V>>{
        return (object: Iterator<TreapNode<K,V>>{
            var node =  maxNode(root)
            var next = maxNode(root)
            var last = minNode(root)

            override fun hasNext(): Boolean{
                return node != null && node!!.key >= last!!.key 
            }
            
            override fun next(): TreapNode<K,V>{
                next = node
                node = nextSmaller(node)

                if (next == null){
                    throw(NoSuchElementException())
                }


                return next as TreapNode<K, V>
            }
        })
    }
}




