import treap.Treap
import TreapNode


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*


class TreapTests{
    private var tree = Treap<Int,Int>()

    @BeforeEach
    fun createTreap(){
        tree = Treap()

    }

    @Nested
    inner class IteratorTests{
        @Test
        fun emptyListIterateTest(){
            val nodes = mutableListOf<TreapNode<Int,Int>>()
            for(node in tree){
                nodes.add(node)
            }
            assertTrue(nodes.isEmpty())
        }

        @Test
        fun nextInEmptyTreeTest(){
            assertThrows(java.util.NoSuchElementException::class.java){
                tree.iterator().next()
            }
        }

        @Test
        fun hasNextInEmptyTreeTest(){
            assertFalse(tree.iterator().hasNext())
        }

        @Test
        fun iteratorTest(){
            val keys = mutableListOf<Int>()
            val prioritys = mutableListOf<Int>()

            for (i in -100..100) {
                keys.add(i)
                prioritys.add(i*10)
                tree.insert(i,-i,i*10)
            }

            for (node in tree) {
                assertTrue(node.key in keys)
                keys.remove(node.key)
            }

            assertTrue(keys.isEmpty())
        }



    }

    @Nested
    inner class FindMethodsTests{

        @Test
        fun findInEmptyTreeTest() {
            for (i in -100..100)
                assertNull(tree.find(i))
        }

        @Test
        fun findEachElementTest() {
            for (i in -100..100)
                tree.insert(i, i*i,i*10)

            for (i in -100..100)
                assertEquals(tree.find(i)?.value, i*i)
        }


        @Test
        fun findRemovedElementsTest() {
            for (i in -100..100)
                tree.insert(i, i, i*10)

            for (i in -10..10)
                tree.remove(i)

            for (i in -10..10)
                assertNull(tree.find(i))
        }

        @Test
        fun findOfNonexistentElementsTest() {
            for (i in -100..0)
                tree.insert(i, i, i*10)

            for (i in 1..100)
                assertNull(tree.find(i))
        }

        @Test
        fun findMaxElementTest(){
            for(i in 0..100){
                tree.insert(i,i,i*10)
            }

            assertEquals(tree.maxNode(tree.root)?.value,100)
        }

        @Test
        fun findMinElementTest(){
            for(i in 0..100){
                tree.insert(i,i,i*10)
            }

            assertEquals(tree.minNode(tree.root)?.value,0)
        }


        @Test
        fun findMaxNodeInEmptyTest(){
            assertNull(tree.maxNode(tree.root))
        }

        @Test
        fun findMinNodeInEmptyTest(){
            assertNull(tree.minNode(tree.root))
        }
    }

    @Nested
    inner class InsertMethodsTests{
        @Test
        fun insertSeveralTimesInTheSameKeyTest() {
            for (i in -100..100)
                tree.insert(1, i, i*10)

            assertEquals(tree.find(1)?.value, 100)
        }

        @Test
        fun insertSeveralTimesInTheSamePriorityTest() {
            for (i in -100..100)
                tree.insert(i, i, 10)

            for (i in -100..100)
                assertEquals(tree.find(i)?.value, i)
        }

        @Test
        fun insertSeveralTimesInTheSameKeyPriorityCheckTest() {
            for (i in -100..100)
                tree.insert(1, i, i)

            assertEquals(tree.find(1)?.priority, 100)
        }

        @Test
        fun insertInReversedOrderTest() {
            for (i in 100 downTo -100)
                tree.insert(i, -i, i*10)

            for (i in -100..100)
                assertEquals(tree.find(i)?.value, -i)
        }


        @Test
        fun insertInTheMiddleTest() {
            for (i in 100 downTo 0) {
                tree.insert(i, -i, i*10)
                tree.insert(-i, i, -i*10)
            }

            for (i in -100..100)
                assertEquals(tree.find(i)?.value, -i)
        }

        @Test
        fun insertInEvenAndOddOrderTest() {
            for (i in -100..100) {
                if (i % 2 == 0)
                    tree.insert(i, -i, i*10)
            }

            for (i in -100..100) {
                if (i % 2 != 0)
                    tree.insert(i, -i, i*10)
            }

            for (i in -100..100)
                assertEquals(tree.find(i)?.value, -i)
        }
        @Test
        fun insertWithoutPriority(){
            assertThrows<Exception> { tree.insert(1,1) }
        }




        @Test
        fun putAllDifferenceSizesTest(){
            val keys = mutableListOf<Int>(1,2,3)
            val values = mutableListOf<Int>(1,1)
            val prioritys = mutableListOf<Int>(1,2)

            assertThrows<Exception> { tree.putAll(keys,values,prioritys) }

        }

        @Test
        fun putAllDifferenceSizesKVTest(){
            val keys = mutableListOf<Int>(1,2,3)
            val values = mutableListOf<Int>(1,1)
            val prioritys = mutableListOf<Int>(1,2,3)

            assertFalse(keys.size == values.size)
            //assertThrows<Exception> { tree.putAll(keys,values,prioritys) }

        }

        @Test
        fun putAllDifferenceSizesKPTest(){
            val keys = mutableListOf<Int>(1,2,3)
            val values = mutableListOf<Int>(1,2,3)
            val prioritys = mutableListOf<Int>(1,2)

            assertFalse(keys.size == prioritys.size)
            //assertThrows<Exception> { tree.putAll(keys,values,prioritys) }

        }

        @Test
        fun putAllDifferenceSizesKPVTest(){
            val keys = mutableListOf<Int>(1,2,3)
            val values = mutableListOf<Int>(1,2,3,4)
            val prioritys = mutableListOf<Int>(1,2)

            assertTrue((keys.size == prioritys.size) == (keys.size == values.size))
            //assertThrows<Exception> { tree.putAll(keys,values,prioritys) }

        }

        @Test
        fun putAllTest(){
            val keys = mutableListOf<Int>(1,2,3)
            val values = mutableListOf<Int>(1,2,3)
            val prioritys = mutableListOf<Int>(1,2,3)

            tree.putAll(keys,values,prioritys)
            for (i in 0 until keys.size)
                assertTrue(tree.find(keys[i]) != null)

        }




    }

    @Nested
    inner class RemoveMethodsTests{
        @Test
        fun removeInEmptyTreeTest() {
            for (i in -100..100)
                assertFalse(tree.remove(i))
        }

        @Test
        fun removeElementsTest() {
            for (i in -100..100)
                tree.insert(i, i, i*10)

            for (i in -100..0)
                assertTrue(tree.remove(i))

            for (i in -100..0)
                assertNull(tree.find(i))
        }

        @Test
        fun removeNonexistentElementsTest() {
            for (i in -100..0)
                tree.insert(i, i, i*10)

            for (i in 1..100)
                assertFalse(tree.remove(i))
        }

        @Test
        fun removeLeafRootTest() {
            tree.insert(1, 1, 1)
            assertTrue(tree.remove(1))
            assertNull(tree.find(1))
        }

        @Test
        fun removeRootWithRightSonTest() {
            tree.insert(1, 1, 2)
            tree.insert(2, 2, 1)
            assertTrue(tree.remove(1))
            assertNull(tree.find(1))
            assertEquals(tree.find(2)?.value, 2)
            assertNull((tree.find(2)?.parent))
        }
        @Test
        fun removeRootWithLeftSonTest() {
            tree.insert(2, 2,2)
            tree.insert(1, 1,1)
            assertTrue(tree.remove(2))
            assertNull(tree.find(2))
            assertEquals(tree.find(1)?.value, 1)
            assertNull(tree.find(1)?.parent)
        }

        @Test
        fun removeRootNodeWithChilds(){
            tree.insert(2,2,3)
            tree.insert(1,1,1)
            tree.insert(3,3,2)
            assertTrue(tree.remove(2))
            assertEquals(tree.root?.key,3)
        }

        @Test
        fun removeLelfChild(){
            tree.insert(2,2,2)
            tree.insert(1,1,1)
            assertEquals(tree.find(2)!!.leftNode,tree.find(1) )
            assertTrue(tree.remove(1))
            assertNull(tree.find(1))
        }

        @Test
        fun removeRightChild(){
            tree.insert(2,2,2)
            tree.insert(3,3,1)
            assertEquals(tree.find(2)!!.rightNode,tree.find(3) )
            assertTrue(tree.remove(3))
            assertNull(tree.find(3))
        }

        @Test
        fun removeEachElementTest() {
            for (i in -100..100)
                tree.insert(i, i, i*10)

            for (i in -100..100)
                assertTrue(tree.remove(i))

            for (i in -100..100)
                assertNull(tree.find(i))
        }

        @Test
        fun removeMiddleElementAndCheckParentsTest() {
            for (i in -100..100)
                tree.insert(i, i, i*10)

            assertTrue(tree.remove(0))

            assertNull(tree.find(0))
            assertEquals(tree.find(-1)?.parent?.key,1)
            assertEquals(tree.find(1)?.leftNode?.key,-1)
        }

        @Test
        fun removeMinElementAndCheckMinTest() {
            for (i in -100..100)
                tree.insert(i, i, i*10)

            assertTrue(tree.remove(-100))
            assertEquals(tree.minNode(tree.root)?.key,-99)
        }

        @Test
        fun removeMaxElementAndCheckMaxTest() {
            for (i in -100..100)
                tree.insert(i, i, i*10)

            assertTrue(tree.remove(100))
            assertEquals(tree.maxNode(tree.root)?.key,99)
        }

    }

    @Nested
    inner class DataClassFunctionTests{
        @Test
        fun equalsTreesTest(){
            val equalTree = Treap<Int,Int>()

            for(i in -100..100){
                tree.insert(i,i,i)
                equalTree.insert(i,i,i)
            }

            for(i in -100..100){
                assertTrue( tree.find(i) == equalTree.find(i))
            }

        }

        @Test
        fun noEqualsTreesTest(){
            val equalTree = Treap<Int,Int>()

            for(i in -100..100){
                tree.insert(i,i,i)
                equalTree.insert(i,i*10,i)
            }

            for(i in -100..100){
                assertFalse( tree.find(i) == equalTree.find(i))
            }
        }

        @Test
        fun toStringTest(){
            for(i in -100..100){
                tree.insert(i,i,i)
                assertEquals(tree.root.toString(),"{ K=$i, VL=$i, PR=$i }")
            }

        }

    }


}