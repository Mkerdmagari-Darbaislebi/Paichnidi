package math

class Vector(
    var x: Float,
    var y: Float,
    var z: Float = 0f
) : VectorBuilder(arrayOf(x, y, z)) {

    constructor(scalars: Array<Float>) : this(scalars[0], scalars[1], scalars[2])

    constructor(value: Float) : this(value, value, value)
}
