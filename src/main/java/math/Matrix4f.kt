package math

class Matrix4f(
    private var array: Array<Array<Float>> = Array(4) { Array(4) { .0f } }
) : MatrixBuilder(array) {

    // auxiliary constructor
    constructor(value: Float) : this(
        Array(4) { Array(4) { value } }
    )

    // getters and setters
    fun getArray() = array

    infix fun setArray(arr: Array<Array<Float>>) {
        array = arr
    }

    fun setValue(value: Float, x: Int, y: Int) {
        if (areCoordinatesInBoundaries(x, y))
            array[x][y] = value
    }

    fun getValue(x: Int, y: Int) {
        if (areCoordinatesInBoundaries(x, y))
            array[x][y]
    }

    fun setRow(rowIndex: Int, row: Array<Float>) {
        array[rowIndex] = row
    }

    fun setColumn(columnIndex: Int, column: Array<Float>) {
        for (i in 0..3) for (j in 0..3) if (j == columnIndex) array[i][j] = column[i]
    }


    operator fun times(matrix: Matrix4f): Matrix4f {
        val resultArray = Array(4) { Array(4) { 0f } }
        for (e in 0 until 4)
            for (i in 0 until 4)
                for (j in 0 until 4)
                    resultArray[e][i] += array[e][j] * matrix.array[j][i]
        return Matrix4f(resultArray)
    }

    // abstract members' implementation
    override fun plusAssign(scalar: Float) {
        this += Matrix4f(scalar)
    }

    override fun minusAssign(scalar: Float) {
        this -= Matrix4f(scalar)
    }

    override fun plus(scalar: Float) = (this + Matrix4f(scalar))!!

    override fun minus(scalar: Float) = (this - Matrix4f(scalar))!!

    override fun inc() = this + 1.0f

    override fun dec() = this - 1.0f

    // static instances
    companion object {
        private val _IDENTITY_ARRAY by lazy {
            val result = Array(4) { Array(4) { .0f } }
            for (i in 0 until 4)
                result[i][i] = 1.0f
            result
        }

        @JvmStatic
        val IDENTITY_MATRIX: Matrix4f
            get() = Matrix4f(_IDENTITY_ARRAY.clone())
    }
}