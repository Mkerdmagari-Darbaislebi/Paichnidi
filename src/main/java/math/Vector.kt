package math

class Vector(
    var x: Float,
    var y: Float,
    var z: Float = 0f
) : LinAlgObj {

    // Auxiliary Constructors
    constructor(scalars: Array<Float>) : this(scalars[0], scalars[1], scalars[2])

    constructor(value: Float) : this(value, value, value)

    // Vector Operations
    override fun plusAssign(scalar: Float) {
        this += Vector(scalar)
    }

    override fun minusAssign(scalar: Float) {
        this -= Vector(scalar)
    }

    override fun plus(scalar: Float) =
        this + Vector(scalar)

    override fun minus(scalar: Float) =
        this - Vector(scalar)

    override fun inc() =
        this + 1f

    override fun dec() =
        this - 1f

    operator fun plusAssign(vector: Vector) {
        x += vector.x
        y += vector.y
        z += vector.z
    }

    operator fun minusAssign(vector: Vector) {
        x -= vector.x
        y -= vector.y
        z -= vector.z
    }

    operator fun plus(vector: Vector) =
        Vector(
            x + vector.x,
            y + vector.y,
            z + vector.z
        )

    operator fun minus(vector: Vector) =
        Vector(
            x - vector.x,
            y - vector.y,
            z - vector.z
        )

    val flatten = arrayOf(x, y, z)
}
