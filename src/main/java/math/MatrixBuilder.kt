package math

abstract class MatrixBuilder(
    protected val firstDimension: Int,
    protected val secondDimension: Int
) {
    var array = Array(firstDimension) { Array(secondDimension) { .0f } }

    val areDimensionsEqual = fun(matrixBuilder: MatrixBuilder) =
        firstDimension == matrixBuilder.firstDimension && secondDimension == matrixBuilder.secondDimension

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


    // dot product
    fun dotProduct(matrixBuilder: MatrixBuilder): Float? {
        if (areDimensionsEqual(matrixBuilder)) {
            val result = .0f
            for (i in 0 until firstDimension)
                for (j in 0 until secondDimension) {

                }
                    return result
        }
        return null
    }
}
