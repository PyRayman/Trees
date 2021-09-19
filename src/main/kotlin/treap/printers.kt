package treap


class TreapPrinter<K:Comparable<K>,V>(){
    var count = 10
    fun printTreapUtil(node: TreapNode<K,V>?, space:Int = 0)
    {
        if (node == null){
            return
        }
    
        var sp = space
        sp += count

        printTreapUtil(node.rightNode, sp)

        print("\n")
        for (i in count..sp)
            print(" ")
        print("${node.key}|${node.priority}")
    
        printTreapUtil(node.leftNode, sp)
    }

    fun printTreap(node: TreapNode<K,V>?)
    {
        printTreapUtil(node, 0)
    }
}

