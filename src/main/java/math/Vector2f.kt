package math

import kotlin.math.cos
import kotlin.math.sin

open class Vector2f(
    private var x: Float,
    private var y: Float
) : VectorBuilder(arrayOf(x, y)) {

    fun rotate(theta: Float) {
        x = x * cos(theta) - y * sin(theta)
        y = x * sin(theta) + y * cos(theta)
    }
}