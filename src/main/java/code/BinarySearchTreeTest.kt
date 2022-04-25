package code

import org.junit.Test
import org.junit.jupiter.api.Tag

class BinarySearchTreeTest : AbstractBinarySearchTreeTest() {

    override fun create(): CheckableSortedSet<Int> =
            BinarySearchTree(true)

    @org.junit.jupiter.api.Test
    @Tag("Example")
    fun initTestJava() {
        doInitTest()
    }

    @Test
    @Tag("Example")
    fun addTestJava() {
        doAddTest()
    }

    @Test
    @Tag("Example")
    fun firstAndLastTestJava() {
        doFirstAndLastTest()
    }

    @Test
    @Tag("5")
    fun removeTestJava() {
        doRemoveTest()
    }

    @Test
    @Tag("5")
    fun iteratorTestJava() {
        doIteratorTest()
        //дополнительный тест
        doAdditionalIteratorTest()
    }

    @Test
    @Tag("8")
    fun iteratorRemoveTestJava() {
        doIteratorRemoveTest()
        //дополнительный тест
        doAdditionalIteratorRemoveTest()
    }

    @Test
    @Tag("5")
    fun subSetTestJava() {
        doSubSetTest()
    }

    @Test
    @Tag("8")
    fun subSetRelationTestJava() {
        doSubSetRelationTest()
    }

    @Test
    @Tag("7")
    fun subSetFirstAndLastTestJava() {
        doSubSetFirstAndLastTest()
    }

    @Test
    @Tag("4")
    fun headSetTestJava() {
        doHeadSetTest()
    }

    @Test
    @Tag("7")
    fun headSetRelationTestJava() {
        doHeadSetRelationTest()
    }

    @Test
    @Tag("4")
    fun tailSetTestJava() {
        doTailSetTest()
    }

    @Test
    @Tag("7")
    fun tailSetRelationTestJava() {
        doTailSetRelationTest()
    }

}