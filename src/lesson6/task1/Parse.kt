@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson4.task1.roman
import lesson2.task2.daysInMonth
import java.util.*
import kotlin.NoSuchElementException

fun remover(str: String, list: List<String>): String {
    var sum = str
    for (elem in list) sum = sum.replace(elem, "")
    return sum
}

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return "" // Помогает избежать написания дополнительного catch (IndexOutOfBounds)
    val months = mapOf("января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4, "мая" to 5, "июня" to 6,
            "июля" to 7, "августа" to 8, "сентября" to 9, "октября" to 10, "ноября" to 11, "декабря" to 12)
    val day: Int
    val month: Int
    val year: Int
    try {
        day = parts[0].toInt()
        month = months[parts[1]] ?: return ""
        year = parts[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    return when {
        day > daysInMonth(month, year) -> ""
        year < 0 -> "" // Надеюсь, расчет ведется для нашей эры.
        else -> String.format("%02d.%02d.%d", day, month, year)
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val months = mapOf(1 to "января", 2 to "февраля", 3 to "марта", 4 to "апреля", 5 to "мая", 6 to "июня",
            7 to "июля", 8 to "августа", 9 to "сентября", 10 to "октября", 11 to "ноября", 12 to "декабря")
    val day: Int
    val month: String
    val year: Int
    try {
        day = parts[0].toInt()
        month = months[parts[1].toInt()] ?: return ""
        year = parts[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    return when {
        day > daysInMonth(parts[1].toInt(), year) -> ""
        year < 0 -> ""
        else -> String.format("%d %s %d", day, month, year)
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val ph = remover(phone, listOf(" ", "-"))
    var check = 2
    for (i in 0 until ph.length) {
        if (i == 0 && ph[i] == '+') continue
        if (ph[i] == '(' && check == 2) {
            check--
            continue
        }
        if (ph[i] == ')' && check == 1) {
            check--
            continue
        }
        if (ph[i] !in '0'..'9' || check % 2 != 0) return ""
    }
    return if (ph.isEmpty()) "" else remover(ph, listOf("(", ")"))
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = jumps.split(" ")
    println(parts)
    var sum = -1
    try {
        for (p in parts)
            if (p != "" && p != "-" && p != "%")
                if (sum < p.toInt()) sum = p.toInt()
    } catch (e: NumberFormatException) {
        return -1
    }
    return sum
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val parts = jumps.split(" ")
    var sum = -1
    try {
        for (i in 1 until parts.size step 2) {
            if ("+" in parts[i] && sum < parts[i - 1].toInt()) sum = parts[i - 1].toInt()
        }
        val s = jumps.replace(" ", "").replace("%", "").replace("+", "").replace("-", "")
        if (s.any { it !in '0'..'9' }) return -1
    } catch (e: NumberFormatException) {
        return -1
    }
    return sum
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (expression.isEmpty()) throw IllegalArgumentException()
    val parts = expression.split(" ")
    var sum: Int
    try {
        if (parts[0].first() !in '0'..'9') throw IllegalArgumentException()
        sum = parts[0].toInt()
        if (parts.size % 2 != 1) throw IllegalArgumentException()
        for (i in 1 until parts.size - 1 step 2) {
            if (parts[i + 1].first() !in '0'..'9') throw IllegalArgumentException()
            when {
                parts[i] == "+" -> sum += parts[i + 1].toInt()
                parts[i] == "-" -> sum -= parts[i + 1].toInt()
                else -> throw IllegalArgumentException()
            }
        }
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException()
    } catch (e: NoSuchElementException) {
        throw IllegalArgumentException()
    }
    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.split(" ")
    var index = 0
    for (i in 1 until parts.size) {
        if (parts[i - 1].toLowerCase() == parts[i].toLowerCase()) return index
        index += parts[i - 1].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val parts = description.split("; ")
    var slv: Double = -0.1
    var sum = ""
    try {
        for (p in parts) {
            val str = p.split(" ")
            if (str.size != 2) return ""
            if (str[1].toDouble() > slv) {
                slv = str[1].toDouble()
                sum = str[0]
            }
        }
    } catch (e: NumberFormatException) {
        return ""
    }
    return sum
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */

/**
 *M* - удалять по символу и прибавлять по 1000 +++
 *CM* - удалить и добавить 900                 \
 *D* - удалить и добавить 500                   \
 *CD* - удалить и добавить 400                  /
 *C* - удалять по символу и добавлять по 100   /
 *XC*  - удалить и добавить 90                \
 *L* - удалить и добавить 50                   \
 *XL* - удалить и добавить 40                  /
 *X* - удалять по символу и добавлять по 10   /
 *IX* - удалить и добавить 9                 \
 *V* - удалить и добавить 5                   \
 *IV* - удалить и добавить 4                  /
 *I* - удалять по символу и добавлять по 1   /
 **/
fun fromRoman(roman: String): Int {
    if (roman.isEmpty()) return -1
    if (remover(roman, listOf("M", "D", "C", "L", "X", "V", "I")).isNotEmpty()) return -1
    val nums = mapOf(2 to "MDC", 1 to "CLX", 0 to "XVI")
    var sum = 0
    var input = roman
    while (sum < input.length && input[sum] == 'M') sum++
    if (sum < input.length) input = input.substring(sum, input.length) else return sum * 1000
    sum *= 1000
    var c = 0
    for (i in 2 downTo 0) {
        val a = nums[i]!!
        while ((input.length > c) && (input[c] == a[1] || input[c] == a[2])) {
            when {
                input.length > c + 1 && input[c] == a[2] && input[c + 1] == a[0] -> {
                    c += 2
                    sum += 9 * Math.pow(10.0, i + 0.0).toInt()
                }
                input[c] == a[1] -> {
                    c++
                    sum += 5 * Math.pow(10.0, i + 0.0).toInt()
                }
                input.length > c + 1 && input[c] == a[2] && input[c + 1] == a[1] -> {
                    c += 2
                    sum += 4 * Math.pow(10.0, i + 0.0).toInt()
                }
                input[c] == a[2] -> {
                    c++
                    sum += Math.pow(10.0, i + 0.0).toInt()
                }
                else -> return -1
            }

        }
    }
    return sum
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
/*    if (remover(commands, listOf("[", "]", ">", "<", "+", "-", " ")).isNotEmpty())
        throw IllegalArgumentException()
    var num = cells / 2
    var check = 0
    var str = commands
    for (elem in commands.toList()) {
        if (elem == '[') num++
        if (elem == ']') num--
    }
    if (num != cells / 2) throw IllegalArgumentException()
    val sum = mutableListOf<Int>()
    for (i in 0 until cells) sum.add(i, 0)
    while (check < limit && str.isNotEmpty()) {
        check++
        when (str[0]) {
            '<' -> num--
            '>' -> num++
            '+' -> sum[num]++
            '-' -> sum[num]--
            ' ' -> num = num
            else -> {
                list = solver()

            }
        }
        str = if (str.length != 1) str.substring(1, str.length) else ""
    }
    return sum
}

fun solver(n: Int, s: List<Int>, d: String): List<Int> {
    var str = d
    var num = n
    var sum = s
    var i = 0
    when (str[i]) {
        '<' -> num--
        '>' -> num++
        '+' -> sum[num]++
        '-' -> sum[num]--
        ' ' -> num = num
        '[' -> {

        }
        else -> if (num == 0) i = 0 else
    }
}
    */
