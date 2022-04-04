package graphics

data class Vertex3D(
    val x: Float,
    val y: Float,
    val z: Float,
    val color: Color
) : VertexBuilder(arrayOf(x, y, z), color)