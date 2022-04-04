package graphics

data class Vertex2D(
    val x: Float,
    val y: Float,
    val color: Color
) : VertexBuilder(arrayOf(x,y), color)