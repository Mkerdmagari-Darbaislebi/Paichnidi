package math

class Matrix4d(
    private var array: Array<Array<Double>> = Array(4) { Array(4) { .0 } }
) : MatrixBuilder(array) {

    // auxiliary constructor
    constructor(value: Double) : this(
        Array(4) { Array(4) { value } }
    )

    // getters and setters
    fun getArray() = array

    infix fun setArray(arr: Array<Array<Double>>) {
        array = arr
    }

    fun setValue(value: Double, x: Int, y: Int) {
        if (areCoordinatesInBoundaries(x, y))
            array[x][y] = value
    }

    fun getValue(x: Int, y: Int) {
        if (areCoordinatesInBoundaries(x, y))
            array[x][y]
    }

    fun setRow(rowIndex: Int, row: DoubleArray) {
        for (i in 0..3) for (j in 0..3) if (i == rowIndex) array[i][j] = row[j]
    }

    fun setColumn(columnIndex: Int, column: DoubleArray) {
        for (i in 0..3) for (j in 0..3) if (j == columnIndex) array[i][j] = column[i]
    }

    // abstract members' implementation
    override fun plusAssign(scalar: Double) {
        this += Matrix4d(scalar)
    }

    override fun minusAssign(scalar: Double) {
        this -= Matrix4d(scalar)
    }

    override fun plus(scalar: Double) = (this + Matrix4d(scalar))!!

    override fun minus(scalar: Double) = (this - Matrix4d(scalar))!!

    override fun inc() = this + 1.0

    override fun dec() = this - 1.0

    // static instances
    companion object {
        private val _IDENTITY_ARRAY by lazy {
            val result = Array(4) { Array(4) { .0 } }
            for (i in 0 until 4)
                result[i][i] = 1.0
            result
        }

        @JvmStatic
        val IDENTITY_MATRIX: Matrix4d
            get() = Matrix4d(_IDENTITY_ARRAY)
    }
}