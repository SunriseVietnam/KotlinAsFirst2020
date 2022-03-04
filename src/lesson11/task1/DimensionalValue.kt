@file:Suppress("UNUSED_P()ARAMETER")

package lesson11.task1

import java.lang.IllegalArgumentException

/**
 * Класс "Величина с размерностью".
 *
 * Предназначен для представления величин вроде "6 метров" или "3 килограмма"
 * Общая сложность задания - средняя, общая ценность в баллах -- 18
 * Величины с размерностью можно складывать, вычитать, делить, менять им знак.
 * Их также можно умножать и делить на число.
 *
 * В конструктор передаётся вещественное значение и строковая размерность.
 * Строковая размерность может:
 * - либо строго соответствовать одной из abbreviation класса Dimension (m, g)
 * - либо соответствовать одной из приставок, к которой приписана сама размерность (Km, Kg, mm, mg)
 * - во всех остальных случаях следует бросить IllegalArgumentException
 */
class DimensionalValue(value: Double, dimension: String) : Comparable<DimensionalValue> {
    /**
     * Величина с БАЗОВОЙ размерностью (например для 1.0Kg следует вернуть результат в граммах -- 1000.0)
     */
    val value: Double

    /**
     * БАЗОВАЯ размерность (опять-таки для 1.0Kg следует вернуть GRAM)
     */
    val dimension: Dimension

    init {
        this.dimension = Dimension.map[dimension.last().toString()]
            ?: throw IllegalArgumentException("Incorrect dimension $dimension")

        val prefix = dimension.replaceFirst(this.dimension.abbreviation, "")
        this.value = value * (DimensionPrefix.map[prefix]
            ?: throw IllegalArgumentException("Incorrect dimension prefix $prefix"))
    }

    /**
     * Конструктор из строки. Формат строки: значение пробел размерность (1 Kg, 3 mm, 100 g и так далее).
     */
//    constructor(s: String) : this(s.split(" ")[0].toDouble(), s.split(" ")[1]) {
//        if (s.matches(Regex("""\d+ \w+"""))) {
//            val _s = s
//        } else throw IllegalArgumentException("Incorrect input string")
//    }

    constructor(s: String) : this(
        if (s.matches(Regex("""-?\d+ \w+""")))
            s.split(" ")[0].toDouble()
        else throw IllegalArgumentException("Incorrect input string"),
        s.split(" ")[1]
    )

    /**
     * Сложение с другой величиной. Если базовая размерность разная, бросить IllegalArgumentException
     * (нельзя складывать метры и килограммы)
     */
    operator fun plus(other: DimensionalValue): DimensionalValue {
        if (dimension != other.dimension) throw IllegalArgumentException()
        return DimensionalValue(value + other.value, dimension.abbreviation)
    }

    /**
     * Смена знака величины
     */
    operator fun unaryMinus(): DimensionalValue = DimensionalValue(-value, dimension.abbreviation)

    /**
     * Вычитание другой величины. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun minus(other: DimensionalValue): DimensionalValue {
        if (dimension != other.dimension) throw IllegalArgumentException()
        return DimensionalValue(value - other.value, dimension.abbreviation)
    }

    /**
     * Умножение на число
     */
    operator fun times(other: Double): DimensionalValue = DimensionalValue(value * other, dimension.abbreviation)

    /**
     * Деление на число
     */
    operator fun div(other: Double): DimensionalValue = DimensionalValue(value / other, dimension.abbreviation)

    /**
     * Деление на другую величину. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun div(other: DimensionalValue): Double {
        if (dimension != other.dimension) throw IllegalArgumentException()
        return value / other.value
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        (other is DimensionalValue) && (dimension == other.dimension) && (value == other.value)

    /**
     * Сравнение на больше/меньше. Если базовая размерность разная, бросить IllegalArgumentException
     */
    override fun compareTo(other: DimensionalValue): Int {
        if (dimension != other.dimension) throw IllegalArgumentException()
        return value.compareTo(other.value)
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + dimension.hashCode()
        return result
    }
}

/**
 * Размерность. В этот класс можно добавлять новые варианты (секунды, амперы, прочие), но нельзя убирать
 */
enum class Dimension(val abbreviation: String) {
    METER("m"),
    GRAM("g");

    companion object {
        val map = values().associateBy({ it.abbreviation }) { it }
    }
}

/**
 * Приставка размерности. Опять-таки можно добавить новые варианты (деци-, санти-, мега-, ...), но нельзя убирать
 */
enum class DimensionPrefix(val abbreviation: String, val multiplier: Double) {
    ONE("", 1.0),
    KILO("K", 1000.0),
    MILLI("m", 0.001);

    companion object {
        val map = values().associateBy({ it.abbreviation }) { it.multiplier }
    }
}