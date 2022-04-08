package graphics

import math.Vector

open class Vertex(
    private val scalars: Array<Float>,
    private val color: Color
) : Vector(scalars) {

    constructor(
        x: Float,
        y: Float,
        z: Float = 0f,
        color: Color
    ) : this(arrayOf(x, y, z), color)

    fun flattenColor() = color.flatten()
}