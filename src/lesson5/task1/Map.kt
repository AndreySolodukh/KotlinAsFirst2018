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
    val slv = mutableMapOf<String, Pair<Double, Int>>()
    val sum = mutableMapOf<String, Double>()
    for ((s, _) in stockPrices) slv[s] = 0.0 to 0
    for ((s, d) in stockPrices) slv[s] = slv[s]!!.first + d to slv[s]!!.second + 1
    for ((s, d) in slv) sum[s] = d.first / d.second
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
    val sum = friends.toMutableMap()
    for (elem in used) {
        if (elem !in sum) sum[elem] = setOf()
        for ((str, set) in sum)
            if (str != elem && elem in sum[str]!!)
                sum[str] = sum[elem]!! + set - str
    }
    return sum
}
// var presum = mutableMapOf(" " to setOf(""))
/*
while (presum != sum) {
    presum = sum
    for ((c, d) in sum)
        for ((a, b) in friends)
            if (a in d) sum[c] = d.union(b)
    //for ((a, b) in friends)
    //  for ((c, d) in sum)
    //    if (a in d) sum[c] = sum[c]!!.union(b)
}
for (u in used) {
    val x = sum[u]
    sum[u] = if (x != null) x - u else setOf()
}
return sum
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
fun canBuildFrom(chars: List<Char>, word: String): Boolean =
        word.toLowerCase().all { it in chars.map { it.toLowerCase() } }


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
    val sum = mutableSetOf<Set<Char>>()
    for (elem in words) sum.add(elem.toLowerCase().toSet())
    return (sum.size != words.size)
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
    val map = mutableMapOf<Int, Int>()
    for (i in list.size - 1 downTo 0) map[list[i]] = i
    for (i in 0..number / 2)
        if (i in list && number - i in list && i != number - i)
            return map[i]!! to map[number - i]!!
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
1) Ввести критерий ценности предмета - отношение цены к весу.
2) Отсортировать мапу по ценности предметов (по убыванию).
3) Начать добавлять предметы в sum по специальному алгоритму.
3.1) Придумать специальный алгоритм.
4) Если разность вместительности рюкзака и общего веса предметов в sum больше веса любого из оставшихся
предметов - повторить алгоритм.
5) Вернуть sum.
        Примечание:
    а) Нет смысла вводить переменные типа minweight - можно просто удалить все неподходящие элементы из
        wert (ценность предмета)
    б) Исходя из первого: цикл можно ограничить наличием элементов в wert.
    в) Не пытаться понять принцип действия метода динамического программирования после 3 часов ночи.
*/

fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    if (treasures.isEmpty()) return setOf()
    val sum = mutableSetOf<String>()
    val n = treasures.keys.toList()
    val weva = treasures.values.toList() // weva[i].first - вес для n[i], weva[i].second - цена для n[i]
    val m = mutableMapOf<Pair<Int, Int>, Int>()
    for (i in 1..capacity) m[0 to i] = 0
    for (i in 1..n.size)
        for (j in 0..capacity)
            if (weva[i - 1].first > j)
                m[i to j] = m[i - 1 to j] ?: 0
            else {
                m[i to j] = maxOf((m[i - 1 to j] ?: 0), (m[i - 1 to j - weva[i - 1].first] ?: 0) + weva[i - 1].second)
                if ((m[i - 1 to j] ?: 0) < (m[i - 1 to j - weva[i - 1].first] ?: 0) + weva[i - 1].second)
                    sum.add(n[i - 1])
            }
    return sum
}

/** Шаблон для 0-1 ранца: **/
/*
capacity - вместительность
n - количество предметов (treasures.size)
m - массив ( ( индекс(надеюсь) -> вес ) -> {!!!} )
w / w[i] - массив с весами предметов
v / v[i] - массив с ценностями предметов
{!!!} - пока это только лучшая стоимость. Нужно сделать из этого лучший набор предметов.
m: mapOf<Pair<Int, Int>, Int>
for (j in 0..capacity) m[0, j] = 0
for (i in 1..n)
    for (j in 0..capacity)
        if (w[i] > j) m[i, j] = m[i-1, j]
            else m[i, j] = max( m[i-1, j], m[i-1, j-w[i]] + v[i])
 */

/*
if (treasures.isEmpty()) return setOf()
val w = treasures.map { it.value.first to it.key }
val v = treasures.map { it.value.second to it.key }
val m = mutableMapOf<Pair<Int, Int>, Pair<Int, Set<String>>>() // Set потом станет списком предметов. Наверное.
for (j in 0..capacity) m[-1 to j] = 0 to setOf()
for (i in 0 until treasures.size) // Или w.size. Или treasures.size. Отличаться они не должны.
    for (j in 0..capacity)
        if (w[i].first > j)
            m[i to j] = m[i - 1 to j]!!.first to setOf(v[i].second)
            // Старый Список + Новый Список. Не знаю, почему.
        else {
            if (m[i - 1 to j]!!.first > m[i - 1 to j - w[i].first]!!.first + v[i].first)
                m[i to j] = m[i - 1 to j]!!.first to m[i - 1 to j]!!.second
            else m[i to j] = (m[i - 1 to j - w[i].first]!!.first + v[i].first) to
                    (m[i - 1 to j - w[i].first]!!.second + v[i].second)

        }
return m[w.size - 1 to capacity]!!.second
*/

/*
val wert = mutableMapOf<String, Double>()
for ((str, pie) in treasures) wert[str] = (pie.second + 0.0) / pie.first
var capa = capacity
val sum = mutableSetOf<String>()
while (wert.isNotEmpty()) {
    for ((elem, _) in wert)
        if (treasures[elem]!!.second > capa) wert.remove(elem)

}
}
*/
/*
{
    val m = mutableMapOf<Int, Pair<Int, Set<String>>>(0 to (0 to setOf()))
    for (i in 1..capacity) m[i] = TODO()
    TODO()
}

fun slv(tres: Map<String, Pair<Int, Int>>, capa: Int): Set<String> {
    val m = mutableMapOf<Int, Pair<Int, Set<String>>>(0 to (0 to setOf()))
    for (i in 1..capa) TODO()
    TODO()
}
*/

/*
// ^^Пункт 1^^
val zhi = mutableMapOf<String, Double>()
val sum = mutableSetOf<String>()
var capa = capacity
for ((str, pie) in treasures) zhi[str] = (pie.second + 0.0) / pie.first
// По сути, сама ценность предмета (как численное значение) не требуется - она
// будет использоваться только для сортировки => возможно, будет доработка.
// ^^Пункт 2^^
val use = mutableListOf<String>() // Нужен именно list.
for (i in 0 until zhi.size) use.add(i, zhi.toList().sortedBy { (_, v) -> v }[i].first)
// ^^Пункты 3 и 4^^
var minweight = treasures[use[0]]!!.first
for (i in 1 until use.size) minweight = minOf(minweight, treasures[use[i]]!!.first)
while (minweight <= capa) { // пока есть хоть один подходящий предмет...
    val x = use.size // (use будет меняться в цикле)
    for (i in 0 until x) {
        if (use.size >= i + 1)
            if (capa >= treasures[use[i]]!!.first) {
                // если мы не удалили [много] из use и предмет влезает в рюкзак...
                var weight = treasures[use[i]]!!.first
                var cost = treasures[use[i]]!!.second
                for (j in i + 1 until use.size)
                // если всеми остальными предметами нельзя возместить ныне рассматриваемый...
                    if (weight - treasures[use[j]]!!.first >= 0) {
                        weight -= treasures[use[j]]!!.first
                        cost -= treasures[use[j]]!!.second
                    } else continue
                if (cost > 0) {
                    // ...то мы добавляем его в sum, убираем из use и вычитаем его вес из рюкзака.
                    sum += use[i]
                    capa -= treasures[use[i]]!!.first
                    use.removeAt(i)
                }
            }
    }
    if (use.size != 0) {
        minweight = treasures[use[0]]!!.first
        for (i in 1 until use.size) minweight = minOf(minweight, treasures[use[i]]!!.first)
        // новый минимальный вес предмета - для while.
    } else minweight = capa + 1
}
// ^^Пункт 5^^
return sum
}
*/

/**\ ~ Running out of ideas... ~ \**/

