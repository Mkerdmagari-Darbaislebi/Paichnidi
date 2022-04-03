package graphics

import math.Vector3f

data class Vertex3D(
    val x: Float,
    val y: Float,
    val z: Float,
    val color: Triple<Int, Int, Int>
) : Vector3f(x, y, z)