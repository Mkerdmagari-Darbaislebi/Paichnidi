package math

import kotlin.math.sqrt

open class VectorBuilder(
    private var scalars: Array<Float>,
) : MatrixBuilder(arrayOf(scalars)) {

    constructor(length: Int, scalar: Float) : this(
        Array<Float>(length) { scalar }
    )

    val length: Int
        get() = scalars.size

    open fun normalize() {
        val length = sqrt(scalars.reduce { acc, fl ->
            acc + fl * fl
        })
        scalars = scalars.map { scalar ->
            scalar / length
        }.toTypedArray()
    }

    // abstract members' implementation
    override fun plusAssign(scalar: Float) {
        this += VectorBuilder(length, scalar)
    }

    override fun minusAssign(scalar: Float) {
        this -= VectorBuilder(length, scalar)
    }

    override fun plus(scalar: Float): MatrixBuilder =
        (this + VectorBuilder(length, scalar))!!


    override fun minus(scalar: Float): MatrixBuilder =
        (this - VectorBuilder(length, scalar))!!

    override fun inc(): MatrixBuilder = this + 1.0f

    override fun dec(): MatrixBuilder = this - 1.0f

}
