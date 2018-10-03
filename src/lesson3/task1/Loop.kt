@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    if (n == 0) return 1
    var workableN = n
    var answer = 0
    while (workableN > 0) {
        answer += 1
        workableN /= 10
    }
    return answer
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n in 1..2) return 1
    var answer = 1
    var helper = 1
    for (i in 3..n) {
        answer += helper
        helper = answer - helper
    }
    return answer
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var k = kotlin.math.max(m, n)
    while ((k % m != 0) or (k % n != 0)) k += 1
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var answer = 2
    while (n % answer != 0) answer += 1
    return answer
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var answer = n - 1
    while (n % answer != 0) answer -= 1
    return answer
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    for (i in 2..kotlin.math.min(m, n)) if ((m % i == 0) and (n % i == 0)) return false
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var i = 0
    while (i <= kotlin.math.sqrt(kotlin.math.max(m, n) + 0.0)) {
        if ((i * i >= kotlin.math.min(m, n)) and (i * i <= kotlin.math.max(m, n))) return true
        i += 1
    }
    return false
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var answer = 0
    var workableX = x
    while (workableX != 1) {
        if (workableX % 2 == 0) workableX /= 2 else workableX = workableX * 3 + 1
        answer += 1
    }
    return answer
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double { // ???  -  не работает с x = 100 * PI
    var answer = x
    var numerator = x * x * x
    var denominator = 6.0
    var solver = 3.0
    var count = 1
    while (eps <= kotlin.math.abs(numerator / denominator + 0.0)) {
        if (count % 2 != 0) answer -= (numerator / denominator) else answer += (numerator / denominator)
        solver += 2
        denominator *= ((solver - 1) * solver)
        numerator *= x * x
        count += 1
    }
    return answer
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double { // ???  -  не работает с x = 100 * PI
    var answer = 1.0
    var numerator = x * x + 0.0
    var denominator = 2.0
    var solver = 2.0
    var count = 1
    while (eps <= kotlin.math.abs(numerator / denominator + 0.0)) {
        if (count % 2 != 0) answer -= (numerator / denominator) else answer += (numerator / denominator)
        solver += 2
        denominator *= ((solver - 1) * solver)
        numerator *= x * x
        count += 1
    }
    return answer
}


/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    if (n < 10) return n
    var workableN = n
    var length = 0
    var answer = 0
    while (workableN > 0) {
        workableN /= 10
        length += 1
    }
    workableN = n
    for (i in 1..length) {
        answer = answer * 10 + workableN % 10
        workableN /= 10
    }
    return answer
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    if (n < 10) return true
    var workableN = n
    var helper = 0
    while (workableN > 0) {
        workableN /= 10
        helper += 1
    }
    workableN = n
    val length = helper
    helper = 0
    for (i in 1..(length / 2)) {
        helper = helper * 10 + workableN % 10
        workableN /= 10
    }
    workableN = n
    if (length % 2 == 0) for (i in 1..(length / 2)) workableN /= 10 else for (i in 1..(length / 2+ 1)) workableN /= 10
    return if (workableN == helper) true else false
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    if (n < 10) return false
    val justAnumber = n % 10
    var workableN = n
    while (workableN > 0) {
        if (justAnumber != workableN % 10) return true
        workableN /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var workableN = n
    var answer = -1
    var length = 0
    var input = 42
    var square = 1
    while (answer == -1) {
        input = square * square
        while (input > 0) {
            input /= 10
            length += 1
        }
        input = square * square
        workableN -= length
        if (workableN <= 0) {
            for (i in 1..-workableN) input /= 10
            answer = input % 10
        }
        length = 0
        square += 1
    }
    return answer
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    if (n in 1..2) return 1
    var workableN = n - 2    // Первые 2 элемента проверены в предыдущей строке
    var length = 0
    var input = 1
    var workableInput = 65536
    var fib = 1
    var answer = -1
    while (answer == -1) {
        input += fib
        fib = input - fib
        workableInput = input
        while (workableInput > 0) {
            length += 1
            workableInput /= 10
        }
        workableInput = input
        workableN -= length
        if (workableN <= 0) {
            for (i in 1..-workableN) workableInput /= 10
            answer = workableInput % 10
        }
        length = 0
    }
    return answer
}
