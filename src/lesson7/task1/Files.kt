@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File

fun letter(input: String): String = buildString {
    for (elem in input)
        if (elem.isLetter()) append(elem) else append(" ")
}

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val sum = mutableMapOf<String, Int>()
    for (s in substrings) {
        var count = 0
        val str = buildString {
            for (c in File(inputName).readText()) {
                this.append(c.toLowerCase())
                if (s.toLowerCase() in this) {
                    count++
                    this.delete(0, this.length + 1 - s.length)
                }
            }
        }
        sum[s] = count
    }
    return sum
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        val rep = mapOf('ы' to 'и', 'Ы' to 'И', 'Я' to 'А', 'я' to 'а', 'ю' to 'у', 'Ю' to 'У')
        for (line in File(inputName).readLines()) {
            var pre = '%' // Особого смысла в выборе символа нет - он просто должен отличаться от ж, ч, щ, ш
            val slv = buildString {
                for (char in line) {
                    if (pre.toLowerCase() in "жчшщ" && char in rep.keys) append(rep[char])
                    else append(char)
                    pre = char
                }
            }
            it.write(slv)
            it.newLine()
            pre = '%'
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        var max = 0
        for (line in File(inputName).readLines()) max = maxOf(max, line.trim().length)
        for (line in File(inputName).readLines()) {
            for (i in 1..(max - line.trim().length) / 2) it.write(" ")
            it.write(line.trim())
            it.newLine()
        }
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        var max = 0
        for (line in File(inputName).readLines()) max = maxOf(max, line.trim().length)
        for (line in File(inputName).readLines()) {
            val words = line.trim().split(' ').filter { it.isNotEmpty() }
            if (words.isEmpty()) {
                it.newLine()
                continue
            }
            if (words.size == 1) {
                it.write(words[0])
                it.newLine()
                continue
            }
            var amount = 0
            for (word in words) amount += word.length
            var odd = (max - amount) % (words.size - 1)
            for (i in 0..words.size - 2) {
                it.write(words[i])
                for (j in 1..(max - amount) / (words.size - 1)) it.write(" ")
                if (odd > 0) {
                    it.write(" ")
                    odd--
                }
            }
            it.write(words.last())
            it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val sum = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) {
        val words = letter(line).toLowerCase().split(" ")
        for (word in words.filter { it.isNotEmpty() }) sum[word] = (sum[word] ?: 0) + 1
    }
    return sum.toList().sortedBy { (_, v) -> v }.reversed().take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) { // -----------------------------------
    File(outputName).bufferedWriter().use {
        for (char in File(inputName).readText()) {
            when {
                (char.toLowerCase() in dictionary.keys.map { it.toLowerCase() }) ->
                    if (char.isUpperCase())
                        it.write((dictionary[char] ?: dictionary[char.toLowerCase()])!!.toLowerCase().capitalize())
                    else it.write((dictionary[char] ?: dictionary[char.toUpperCase()])!!.toLowerCase())
                else -> it.append(char) // Чем вообще write отличается от append?
            }
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        var max = 0
        val sum = buildString {
            for (line in File(inputName).readLines()) {
                if ((line.toLowerCase().length == line.toLowerCase().toSet().size) && (line.length >= max)) {
                    if (line.length > max) {
                        this.delete(0, this.length)
                        this.append(line)
                        max = line.length
                    } else this.append(", $line")
                }
            }
        }
        it.write(sum)
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {  // ----------------------------------------------------------------
    File(outputName).bufferedWriter().use {
        it.write("<html>")
        it.write("<body>")
        it.write("<p>")
        var icheck = 0
        var bcheck = 0
        var scheck = 0
        var pre = '7'
        for (line in File(inputName).readLines()) {
            if (line.isEmpty() && pre != '7') {
                it.write("</p>")
                it.write("<p>")
            }
            for (i in 0 until line.length)
                when {
                    pre == '~' && line[i] == '~' ->
                        if (scheck == 0) {
                            it.write("<s>")
                            pre = '1'
                            scheck = 1
                        } else {
                            it.write("</s>")
                            pre = '1'
                            scheck = 0
                        }
                    pre == '~' -> {
                        it.write("~${line[i]}")
                        pre = line[i]
                    }
                    pre == '*' && line[i] == '*' -> {
                        if (bcheck == 0) {
                            it.write("<b>")
                            pre = '1'
                            bcheck = 1
                        } else {
                            it.write("</b>")
                            pre = '1'
                            bcheck = 0
                        }
                    }
                    pre == '*' -> {
                        if (line[i] !in "*~") {
                            if (icheck == 0) {
                                it.write("<i>${line[i]}")
                                pre = line[i]
                                icheck = 1
                            } else {
                                it.write("</i>${line[i]}")
                                pre = line[i]
                                icheck = 0
                            }
                        } else {
                            if (icheck == 0) {
                                it.write("<i>")
                                pre = line[i]
                                icheck = 1
                            } else {
                                it.write("</i>")
                                pre = line[i]
                                icheck = 0
                            }
                        }
                    }
                    i == line.length - 1 && line[i] == '*' -> {
                        icheck = if (icheck == 0) {
                            it.write("<i>")
                            1
                        } else {
                            it.write("</i>")
                            0
                        }
                    }
                    i == line.length - 1 && line[i] == '~' -> it.write("~")
                    line[i] in "*~" -> pre = line[i]
                    else -> {
                        it.write(line[i].toString())
                        pre = line[i]
                    }

                }
        }
        it.write("</p>")
        it.write("</body>")
        it.write("</html>")

    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    File(outputName).bufferedWriter().use {
        val sum = "${lhv * rhv}"
        for (i in 0..sum.length - "$lhv".length) it.write(" ")
        it.write("$lhv")
        it.newLine()
        it.write("*")
        for (i in 1..sum.length - "$rhv".length) it.write(" ")
        it.write("$rhv")
        it.newLine()
        for (i in 0..sum.length) it.write("-")
        it.newLine()
        for (i in 0 until "$rhv".length) {
            val slv = (lhv * ((rhv / Math.pow(10.0, i + 0.0).toInt()) % 10)).toString()
            if (i != 0) it.write("+") else it.write(" ")
            for (j in 1..sum.length - slv.length - i) it.write(" ")
            it.write(slv)
            it.newLine()
        }
        for (i in 0..sum.length) it.write("-")
        it.newLine()
        it.write(" $sum")
    }
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) { // -----------------------------------------------------------
    File(outputName).bufferedWriter().use {
        it.write(" $lhv | $rhv")
        it.newLine()
        val sum = lhv / rhv
        println(sum)
        val s = sum.toString()[0]

        println(s) // Вот это печатает цифру 9
        println(s.toInt()) // А вот ЭТО уже 57
        /** Что-то сломалось **/
        println(sum / Math.pow(10.0, "$sum".length - 1.0).toInt()) // И опять 9

        it.write("-${"$sum"[0].toInt() * rhv}")
        for (i in 1..("$lhv".length + 3 - "-${"$sum"[0].toInt() * rhv}".length)) it.write(" ")
        it.write("$sum")
        it.newLine()
        for (i in 1 until sum.toString().length) {
            val slv = sum.toString()[i].toInt() * rhv
            for (j in 1..i) it.write(" ")
            it.write("-$slv")
            it.newLine()
            for (j in 1..i) it.write(" ")
            for (j in 1.."-$slv".length) it.write("_")
            it.newLine()


        }
    }
}

