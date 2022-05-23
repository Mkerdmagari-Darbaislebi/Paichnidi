package util

import math.Matrix4f
import math.Vector
import unit.Camera
import kotlin.math.sin
import kotlin.math.sqrt

object Transformations {

    val transformationMatrix: (Vector, Vector, Vector) -> Matrix4f = { a, b, c ->
        val translationMatrix = translationMatrix(a)
        val rotationMatrix = rotationMatrix(b)
        val scaleMatrix = scaleMatrix(c)

        (scaleMatrix * (rotationMatrix * translationMatrix))
    }

    val cameraViewMatrix: (Camera) -> Matrix4f = { camera ->
        val rotationMatrix = rotationMatrix(camera.pitch, camera.yaw, 0f)
        val translationMatrix = translationMatrix(
            -camera.position.x,
            -camera.position.y,
            -camera.position.z
        )

        translationMatrix * rotationMatrix
    }

    fun translationMatrix(v: Vector) = translationMatrix(v.x, v.y, v.z)
    fun rotationMatrix(v: Vector) = rotationMatrix(v.x, v.y, v.z)
    fun scaleMatrix(v: Vector) = scaleMatrix(v.x, v.y, v.z)

    val translationMatrix: TransformationFunction = { x, y, z ->

        val matrix = Matrix4f.IDENTITY_MATRIX
        matrix.setRow(3, floatArrayOf(x, y, z, 1.0f))

        matrix
    }

    val rotationMatrix: TransformationFunction = { x, y, z ->
        Matrix4f.IDENTITY_MATRIX
            .rotX(x)
            .rotY(y)
            .rotZ(z)
    }

    val scaleMatrix: TransformationFunction = { x, y, z ->
        val matrix = Matrix4f.IDENTITY_MATRIX
        for (i in 0 until 3)
            matrix[i][i] = arrayOf(x, y, z)[i]
        matrix
    }

    private fun Matrix4f.rotX(alpha: Float): Matrix4f {
        val sin = sin(alpha)
        val cos = cosFromSin(sin.toDouble(), 3.0).toFloat()

        val firstRow = FloatArray(4) { 0f }
        val secondRow = FloatArray(4) { 0f }

        for (i in firstRow.indices) firstRow[i] = array[1][i] * cos + array[2][i] * sin
        for (i in secondRow.indices) secondRow[i] = array[1][i] * (-sin) + array[2][i] * cos

        setRow(2, secondRow)
        setRow(1, firstRow)
        return this
    }

    private fun Matrix4f.rotY(alpha: Float): Matrix4f {
        val sin = sin(alpha)
        val cos = cosFromSin(sin.toDouble(), alpha.toDouble()).toFloat()

        val zeroRow = FloatArray(4) { 0f }
        val secondRow = FloatArray(4) { 0f }

        for (i in zeroRow.indices) zeroRow[i] = array[0][i] * cos + array[2][0] * (-sin)
        for (i in secondRow.indices) secondRow[i] = array[0][i] * sin + array[2][i] * cos

        setRow(2, secondRow)
        setRow(0, zeroRow)
        return this
    }

    private fun Matrix4f.rotZ(alpha: Float): Matrix4f {
        val sin = sin(alpha)
        val cos = cosFromSin(sin.toDouble(), alpha.toDouble()).toFloat()

        val zeroRow = FloatArray(4) { 0f }
        val firstRow = FloatArray(4) { 0f }

        for (i in zeroRow.indices) zeroRow[i] = array[0][i] * cos + array[1][0] * sin
        for (i in firstRow.indices) firstRow[i] = array[0][i] * (-sin) + array[1][i] * cos

        setRow(1, firstRow)
        setRow(0, zeroRow)
        return this
    }

    private fun cosFromSin(sin: Double, alpha: Double): Double {
        val cos = sqrt(1 - sin * sin)
        val a = alpha + 1.5707963267948966
        var b = a - (a / 6.283185307179586).toInt() * 6.283185307179586
        b += (if (b < 0) 6.283185307179586 else 0.0)

        return if (b >= Math.PI) -cos else cos
    }
}
