
# Деревья

Реализация бинарного и декартового деревьев.
Операции поиска, удаления, вставки вершины присутсвуют в каждом дереве. А также имеется возможность итерирования по вершинам(от меньшего ключа к большему).


## Бинарное дерево
Ключ каждого родителя больше ключа левого сына и меньше ключа правого.


```kotlin
fun main(){
    val keys: MutableList<Int> = mutableListOf(18,11,27,7,14)
    val values: MutableList<Int> = mutableListOf(18,12,3,4,5)
    var tree: BinaryTree<Int, Int> = BinaryTree<Int,Int>()
    tree.putAll(keys,values)
}
```
## Декартово дерево
это структура данных, объединяющая в себе бинарное дерево поиска и бинарную кучу.

```kotlin
fun main(){
    val keys: MutableList<Int> = mutableListOf(18,11,7,1,9,14,16,15,17,27)
    val values: MutableList<Int> = mutableListOf(1,2,3,4,5,6,7,8,9,10)
    val prioritys: MutableList<Int> = mutableListOf(98,80,37,33,3,78,54,29,11,70)
    var tree:Treap<Int,Int> = Treap<Int,Int>()
    tree.putAll(keys,values,prioritys)

}
```

## Сборка проекта

Сборка осуществляется с помощью системы [Gradle](https://gradle.org/)

Сборка без тестов:

```bash
   ./gradlew assemble
```

Сборка с тестами:
```bash
    ./gradlew test
```

Файлы с исходным кодом будут находиться в папке `src/main/kotlin`

Файлы с тестами будут находиться в папке `src/test/kotlin`
## Тестирование

**Тесты:** [JUnit 5](https://junit.org/junit5/)

**Отчет:** [JaCoCo](https://www.eclemma.org/jacoco/)

Jacoco генерирует отчет о покрытии кода тестами.\
Отчет о покрытии хранится в файле `${buildDir}/jacocoHtml/index.html`\
Отчет о прохождении тестов хранится в файле `${buildDir}/reports/tests/test/index.html`

