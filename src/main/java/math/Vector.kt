package math

import kotlin.math.cos
import kotlin.math.sin

open class Vector(
    var _x: Float,
    var _y: Float,
    var _z: Float = 0f
) : VectorBuilder(arrayOf(_x, _y, _z)) {

    constructor(scalars: Array<Float>) : this(scalars[0], scalars[1], scalars[2])

    constructor(value: Float) : this(value, value, value)

    fun rotate(theta: Float) {
        _x = _x * cos(theta) - _y * sin(theta)
        _y = _x * sin(theta) + _y * cos(theta)
    }

}
