package graphics

import math.Vector

open class Vertex(
    val x: Float,
    val y: Float,
    val z: Float = 0f,
    private val color: Color
) : Vector(arrayOf(x,y,z)) {

    constructor(
        vertices: Array<Float>,
        color: Color
    ) : this(vertices[0],vertices[1],vertices[2], color)

    fun flattenColor() = color.flatten()
}