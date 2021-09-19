package binarysearchtree

class BinaryPrinter<K:Comparable<K>,V>(){
    var count = 10
    fun printBinUtil(node: BinNode<K,V>?, space:Int = 0)
    {
        if (node == null){
            return
        }
    
        var sp = space
        sp += count

        printBinUtil(node.rightNode, sp)

        print("\n")
        for (i in count..sp)
            print(" ")
        print("${node.key}")  
    
        printBinUtil(node.leftNode, sp)
    }

    fun printBinary(node: BinNode<K,V>?)
    {
        printBinUtil(node, 0)
    }
}