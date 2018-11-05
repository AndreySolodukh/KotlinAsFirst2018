@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val sum = mapA.toMutableMap()
    for ((key, entry) in mapB)
        if ((sum[key] != entry) && (sum[key] != null)) sum[key] = "${sum[key]}, $entry" else sum[key] = entry
    return sum
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val sum = mutableMapOf<Int, MutableList<String>>()
    for ((s, i) in grades) if (sum[i] == null) sum[i] = mutableListOf(s) else sum[i]!!.add(s)
    for ((i) in sum) sum[i]!!.sort()
    return sum
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((x, y) in a) if (b[x] != y) return false
    return true
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val sum = mutableMapOf<String, Double>()
    val slv = mutableMapOf<String, Int>()
    for ((s, d) in stockPrices) {
        sum[s] = (sum[s] ?: 0.0) + d
        slv[s] = (slv[s] ?: 0) + 1
    }
    for ((s) in sum) sum[s] = sum[s]!! / slv[s]!!
    return sum

}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var sum: Double? = null
    var slv: String? = null
    for ((name, pair) in stuff) {
        if (pair.first == kind && (sum == null || sum > pair.second)) {
            sum = pair.second
            slv = name
        }
    }
    return slv
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val used = us(friends)
    val sum = mutableMapOf<String, MutableSet<String>>()
    for ((n, s) in friends) {
        used.remove(n)
        sum[n] = s.toMutableSet()
        while (sum[n]!!.any { it in used })
            TODO()
    }
    TODO()
}

/*
Пока не очень понятно, как создать функцию, которая переберет все возможные рукопожатия
и не будет зациклена на самой себе или иметь вид:
for (...)
    for (...)
        for (...)
            ...
Вариант 1 - ограничить количество шагов в цикле количеством элементов в friends
Вариант 2 - скопировать исходный массив в mutable и удалять из него ключи
Вариант 3 -

 */


fun us(x: Map<String, Set<String>>): MutableSet<String> {
    val u = mutableSetOf<String>()
    for ((a, b) in x) {
        u.add(a)
        for (c in b) u.add(c)
    }
    return u
}



/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for ((x, y) in b)
        if (a[x] == y) a.remove(x)
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val sum = mutableSetOf<String>()
    for (x in a) if (x in b) sum.add(x)
    return sum.toList()
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    if (word.isEmpty()) return true
    val slv = mutableSetOf<Char>()
    for (i in 0 until word.length) slv.add(word[i].toLowerCase())
    return slv.union(chars.toString().toLowerCase().toSet()) == chars.toString().toLowerCase().toSet()
}


/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */

fun extractRepeats(list: List<String>): Map<String, Int> {
    val sum = mutableMapOf<String, Int>()
    for (e in list) sum[e] = (sum[e] ?: 0) + 1
    sum.values.removeIf { it == 1 }
    return sum
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    for (i in 0 until words.size)
        for (j in i + 1 until words.size)
            if (words[i].toList().sorted() == words[j].toList().sorted()) return true
    return false
// Пока идей по повышению эффективности нет.
}


/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    if ((number % 2 == 0) && (number / 2 in list)) {
        val l = list.toMutableList()
        val x = list.indexOf(number / 2)
        l.removeAt(x)
        if (number / 2 in l) return x to l.indexOf(number / 2) + 1
    }
    for (i in 0..number / 2)
        if (i in list && number - i in list && i != number - i)
            return minOf(list.indexOf(i), list.indexOf(number - i)) to maxOf(list.indexOf(i), list.indexOf(number - i))
    return -1 to -1
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
/*
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val wert = mutableMapOf<String, Double>()
    var inv = capacity
    val sum = mutableSetOf<String>()
    for ((s, pair) in treasures) wert[s] = (pair.second + 0.0) / pair.first
    for (i in wert.size - 1 downTo 0) {
        if (i > 0 && treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.first <= inv)
            for (j in i - 1 downTo 0) {
                var v = 0
                var weight = 0
                if (treasures[wert.toList().sortedBy { (_, v) -> v }[j].first]!!.first + weight <=
                        treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.first) {
                    weight += treasures[wert.toList().sortedBy { (_, v) -> v }[j].first]!!.first
                    v += treasures[wert.toList().sortedBy { (_, v) -> v }[j].first]!!.second
                }
                if (j == 0 && v <= treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.second) { // "<=" ???
                    sum.add(wert.toList().sortedBy { (_, v) -> v }[i].first)
                    inv -= treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.first
                }
            }
        if (i == 0 && treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.first <= inv)
            sum.add(wert.toList().sortedBy { (_, v) -> v }[i].first)
    }
    return sum.toList().sortedBy { v -> v }.toSet()
}
*/

fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val wert = mutableMapOf<String, Double>()
    var inv = capacity
    val sum = mutableSetOf<String>()
    val used = mutableSetOf<String>()
    for ((s, pair) in treasures) wert[s] = (pair.second + 0.0) / pair.first
    for (i in wert.size - 1 downTo 0) {
        if (i > 0 && treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.first <= inv) {
            var v = 0
            var weight = 0
            val sum2 = mutableSetOf<String>()
            for (j in i - 1 downTo 0) {
                if (treasures[wert.toList().sortedBy { (_, v) -> v }[j].first]!!.first + weight <=
                        treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.first &&
                        wert.toList().sortedBy { (_, v) -> v }[i].first !in used) {
                    weight += treasures[wert.toList().sortedBy { (_, v) -> v }[j].first]!!.first
                    v += treasures[wert.toList().sortedBy { (_, v) -> v }[j].first]!!.second
                    sum2.add(wert.toList().sortedBy { (_, v) -> v }[j].first)
                }
                if (j == 0 && v <= treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.second) { // "<=" ???
                    sum.add(wert.toList().sortedBy { (_, v) -> v }[i].first)
                    used.add(wert.toList().sortedBy { (_, v) -> v }[i].first)
                    inv -= treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.first
                } else {
                    sum.addAll(sum2)
                    used.addAll(sum2)
                    inv -= weight
                }
            }
        }
        if (i == 0 && treasures[wert.toList().sortedBy { (_, v) -> v }[i].first]!!.first <= inv)
            sum.add(wert.toList().sortedBy { (_, v) -> v }[i].first)
    }
    return sum.toList().sortedBy { v -> v }.toSet()
}