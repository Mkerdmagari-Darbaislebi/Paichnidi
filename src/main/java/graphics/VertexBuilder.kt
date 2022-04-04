package graphics

import math.VectorBuilder

open class VertexBuilder(
    private val scalars: Array<Float>,
    private val color: Color
) : VectorBuilder(scalars) {
    fun flattenColor() = color.flatten()
}