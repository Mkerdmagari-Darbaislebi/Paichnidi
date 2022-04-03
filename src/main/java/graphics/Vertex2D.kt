package graphics

import math.Vector2f

data class Vertex2D(
    val x: Float,
    val y: Float,
    val color: Triple<Int, Int, Int>
) : Vector2f(x, y)