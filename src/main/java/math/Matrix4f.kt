package math

class Matrix4f(
    private var arr: Array<FloatArray> =
        Array(4) { FloatArray(4) { .0f } }
) : LinAlgObj {

    val flatten
        get() = arr.map { it.toList() }.toList().flatten().toFloatArray()


    val areInBounds: (Int, Int) -> Boolean = { a, b ->
        a in 0..4 && b in 0..4
    }

    // Auxiliary Constructor
    constructor(value: Float) : this(
        Array(4) { FloatArray(4) { value } }
    )

    // Getters/Setters
    val array get() = arr

    val setArray: (Array<FloatArray>) -> Unit = { arr = it }

    val setValue: (Float, Int, Int) -> Unit = { value, x, y ->
        if (areInBounds(x, y))
            arr[x][y] = value
    }

    val getValue: (Int, Int) -> Float? = { x, y -> if (areInBounds(x, y)) arr[x][y] else null }

    val setRow: (Int, FloatArray) -> Unit = { index, row ->
        arr[index] = row
    }

    val setColumn: (Int, FloatArray) -> Unit = { index, col ->
        for (i in 0..3) arr[i][index] = col[i]
    }

    // Matrix Operations
    operator fun times(matrix: Matrix4f): Matrix4f {
        val resultArray = Array(4) { FloatArray(4) { 0f } }
        for (e in 0 until 4)
            for (i in 0 until 4)
                for (j in 0 until 4)
                    resultArray[e][i] += arr[e][j] * matrix.arr[j][i]
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
        setArray((this + matrix).arr)

    operator fun minusAssign(matrix: Matrix4f) =
        setArray((this - matrix).arr)

    operator fun plus(matrix: Matrix4f): Matrix4f {
        val res = arr
        for (i in 0 until 4) for (j in 0 until 4)
            res[i][j] += matrix.arr[i][j]
        return Matrix4f(res)
    }

    operator fun minus(matrix: Matrix4f): Matrix4f {
        val res = arr
        for (i in 0 until 4) for (j in 0 until 4)
            res[i][j] -= matrix.arr[i][j]
        return Matrix4f(res)
    }

    infix fun dot(matrix: Matrix4f): Float {
        var res = 0f
        for (i in 0 until 4) for (j in 0 until 4)
            res += arr[i][j] * matrix.arr[i][j]
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
