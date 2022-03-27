package math

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Vector3f(
    private var x: Double,
    private var y: Double,
    private var z:Double
) : MatrixBuilder(arrayOf(arrayOf(x, y,z))) {

    val length = 3

    // auxiliary constructor
    constructor(value: Double) : this(value, value,value)

    fun normalize() {
        val length = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
        x /= length
        y /= length
        z /= length
    }

    fun rotate(theta: Double) {
        x = x * cos(theta) - y * sin(theta)
        y = x * sin(theta) + y * cos(theta)
    }

    // abstract members' implementation
    override fun plusAssign(scalar: Double) {
        this += Vector3f(scalar)
    }

    override fun minusAssign(scalar: Double) {
        this -= Vector3f(scalar)
    }

    override fun plus(scalar: Double) = (this + Vector3f(scalar))!!

    override fun minus(scalar: Double) = (this - Vector3f(scalar))!!

    override fun inc() = this + 1.0

    override fun dec() = this - 1.0
}