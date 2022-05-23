package math

class Matrix4f(
    val array: Array<FloatArray> =
        Array(4) { FloatArray(4) { .0f } }
) : LinAlgObj {

    val flatten
        get() = array.map { it.toList() }.toList().flatten().toFloatArray()


    private val areInBounds: (Int, Int) -> Boolean = { a, b ->
        a in 0..4 && b in 0..4
    }

    // Auxiliary Constructor
    constructor(value: Float) : this(
        Array(4) { FloatArray(4) { value } }
    )

    // Getters/Setters
    operator fun get(index: Int): FloatArray =
        array[index]

    val setArray: (Array<FloatArray>) -> Unit = {
        for (i in 0 until 4) for (j in 0 until 4) array[i][j] = it[i][j]
    }

    val setRow: (Int, FloatArray) -> Unit = { index, row ->
        array[index] = row
    }

    val setColumn: (Int, FloatArray) -> Unit = { index, col ->
        for (i in 0..3) array[i][index] = col[i]
    }

    // Matrix Operations
    operator fun times(matrix: Matrix4f): Matrix4f {
        val resultArray = Array(4) { FloatArray(4) { 0f } }
        for (e in 0 until 4)
            for (i in 0 until 4)
                for (j in 0 until 4)
                    resultArray[e][i] += array[e][j] * matrix.array[j][i]
        return Matrix4f(resultArray)
    }

    override operator fun plusAssign(scalar: Float) {
        this += Matrix4f(scalar)
    }

    override fun minusAssign(scalar: Float) {
        this -= Matrix4f(scalar)
    }

    override fun plus(scalar: Float) = (this + Matrix4f(scalar))

    override fun minus(scalar: Float) = (this - Matrix4f(scalar))

    override fun inc() = this + 1.0f

    override fun dec() = this - 1.0f

    operator fun plusAssign(matrix: Matrix4f) =
        setArray((this + matrix).array)

    operator fun minusAssign(matrix: Matrix4f) =
        setArray((this - matrix).array)

    operator fun plus(matrix: Matrix4f): Matrix4f {
        val res = array
        for (i in 0 until 4) for (j in 0 until 4)
            res[i][j] += matrix.array[i][j]
        return Matrix4f(res)
    }

    operator fun minus(matrix: Matrix4f): Matrix4f {
        val res = array
        for (i in 0 until 4) for (j in 0 until 4)
            res[i][j] -= matrix.array[i][j]
        return Matrix4f(res)
    }

    infix fun dot(matrix: Matrix4f): Float {
        var res = 0f
        for (i in 0 until 4) for (j in 0 until 4)
            res += array[i][j] * matrix.array[i][j]
        return res
    }

    // static instances
    companion object {
        private val _IDENTITY_ARRAY by lazy {
            val result = Array(4) { FloatArray(4) { .0f } }
            for (i in 0 until 4)
                result[i][i] = 1.0f
            result
        }

        @JvmStatic
        val IDENTITY_MATRIX: Matrix4f
            get() = Matrix4f(_IDENTITY_ARRAY.clone())
    }

}
