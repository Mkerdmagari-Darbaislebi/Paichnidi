package math

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

open class Vector3f(
    private var x: Float,
    private var y: Float,
    private var z:Float
) : MatrixBuilder(arrayOf(arrayOf(x, y,z))) {

    val length = 3

    // auxiliary constructor
    constructor(value: Float) : this(value, value,value)

    fun normalize() {
        val length = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
        x /= length
        y /= length
        z /= length
    }

    fun rotate(theta: Float) {
        x = x * cos(theta) - y * sin(theta)
        y = x * sin(theta) + y * cos(theta)
    }

    // abstract members' implementation
    override fun plusAssign(scalar: Float) {
        this += Vector3f(scalar)
    }

    override fun minusAssign(scalar: Float) {
        this -= Vector3f(scalar)
    }

    override fun plus(scalar: Float) = (this + Vector3f(scalar))!!

    override fun minus(scalar: Float) = (this - Vector3f(scalar))!!

    override fun inc() = this + 1.0f

    override fun dec() = this - 1.0f
}