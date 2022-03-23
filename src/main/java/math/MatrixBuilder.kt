package math

abstract class MatrixBuilder(
    private var array: Array<Array<Double>> = Array(0) { Array(0) { .0 } }
) {

    private val firstDimension: Int
        get() = array.size

    private val secondDimension: Int
        get() = array[0].size

    val areDimensionsEqual = fun(matrixBuilder: MatrixBuilder) =
        firstDimension == matrixBuilder.firstDimension &&
                secondDimension == matrixBuilder.secondDimension

    operator fun plusAssign(matrixBuilder: MatrixBuilder) {
        if (areDimensionsEqual(matrixBuilder))
            array = (this + matrixBuilder)!!.array
    }

    operator fun minusAssign(matrixBuilder: MatrixBuilder) {
        if (areDimensionsEqual(matrixBuilder))
            array = (this - matrixBuilder)!!.array
    }

    operator fun plus(matrixBuilder: MatrixBuilder): MatrixBuilder? {
        if (areDimensionsEqual(matrixBuilder)) {
            val result = this
            for (i in 0 until firstDimension)
                for (j in 0 until secondDimension)
                    result.array[i][j] += matrixBuilder.array[i][j]

            return result
        }
        return null
    }

    operator fun minus(matrixBuilder: MatrixBuilder): MatrixBuilder? {
        if (areDimensionsEqual(matrixBuilder)) {
            val result = this
            for (i in 0 until firstDimension)
                for (j in 0 until secondDimension)
                    result.array[i][j] -= matrixBuilder.array[i][j]

            return result
        }
        return null
    }


    fun dotProduct(matrixBuilder: MatrixBuilder): Double? {
        if (areDimensionsEqual(matrixBuilder)) {
            var result = .0
            for (i in 0 until firstDimension)
                for (j in 0 until secondDimension)
                    result += array[i][j] * matrixBuilder.array[i][j]

            return result
        }
        return null
    }
}
