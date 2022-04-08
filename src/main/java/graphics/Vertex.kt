package graphics

import math.Vector
import math.VectorBuilder

open class Vertex(
    val scalars: Array<Float>,
    val color: Color
) : Vector(scalars) {

    constructor(
        x: Float,
        y: Float,
        z: Float = 0f,
        color: Color
    ) : this(arrayOf(x, y, z), color)

    fun flattenColor() = color.flatten()
}