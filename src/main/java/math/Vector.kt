package math

import kotlin.math.cos
import kotlin.math.sin

open class Vector(
    private var x: Float,
    private var y: Float,
    private var z: Float = 0f
) : VectorBuilder(arrayOf(x, y, z)) {

    constructor(scalars: Array<Float>) : this(scalars[0], scalars[1], scalars[2])

    fun rotate(theta: Float) {
        x = x * cos(theta) - y * sin(theta)
        y = x * sin(theta) + y * cos(theta)
    }

}
