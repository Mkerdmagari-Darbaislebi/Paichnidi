package math

import kotlin.math.cos
import kotlin.math.sin

open class Vector3f(
    private var x: Float,
    private var y: Float,
    private var z: Float
) : VectorBuilder(arrayOf(x, y, z)) {

    fun rotate(theta: Float) {
        x = x * cos(theta) - y * sin(theta)
        y = x * sin(theta) + y * cos(theta)
    }

}
