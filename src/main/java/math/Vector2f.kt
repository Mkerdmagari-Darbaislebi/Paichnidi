package math

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Vector2f(
    private var x: Float,
    private var y: Float
) : MatrixBuilder(arrayOf(arrayOf(x, y))) {

    val length = 2

    // auxiliary constructor
    constructor(value: Float) : this(value, value)

    fun normalize() {
        val length = sqrt(x.pow(2) + y.pow(2))
        x /= length
        y /= length
    }

    fun rotate(theta: Float) {
        x = x * cos(theta) - y * sin(theta)
        y = x * sin(theta) + y * cos(theta)
    }

    // abstract members' implementation
    override fun plusAssign(scalar: Float) {
        this += Vector2f(scalar)
    }

    override fun minusAssign(scalar: Float) {
        this -= Vector2f(scalar)
    }

    override fun plus(scalar: Float) = (this + Vector2f(scalar))!!

    override fun minus(scalar: Float) = (this - Vector2f(scalar))!!

    override fun inc() = this + 1.0f

    override fun dec() = this - 1.0f
}