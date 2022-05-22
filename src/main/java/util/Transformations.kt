package util

import math.Matrix4f
import math.Vector
import unit.Camera
import kotlin.math.cos
import kotlin.math.sin

object Transformations {

    val transformationMatrix: (Vector, Vector, Vector) -> Matrix4f = { a, b, c ->
        val translationMatrix = translationMatrix(a)
        val rotationMatrix = rotationMatrix(b)
        val scaleMatrix = scaleMatrix(c)

        (translationMatrix * (rotationMatrix * scaleMatrix))
    }

    val cameraViewMatrix: (Camera) -> Matrix4f = { camera ->
        val rotationMatrix = rotationMatrix(camera.pitch, camera.yaw, 0f)
        val translationMatrix = translationMatrix(
            -camera.position.x,
            -camera.position.y,
            -camera.position.z
        )

        rotationMatrix * translationMatrix
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
        val xRadians = Math.toRadians(x.toDouble()).toFloat()
        val yRadians = Math.toRadians(y.toDouble()).toFloat()
        val zRadians = Math.toRadians(z.toDouble()).toFloat()

        val xRotationMatrix = Matrix4f.IDENTITY_MATRIX
        xRotationMatrix.setRow(1, floatArrayOf(0.0f, cos(xRadians), -sin(xRadians), 0.0f))
        xRotationMatrix.setRow(2, floatArrayOf(0.0f, -sin(xRadians), cos(xRadians), 0.0f))

        val yRotationMatrix = Matrix4f.IDENTITY_MATRIX
        yRotationMatrix.setRow(0, floatArrayOf(cos(yRadians), 0.0f, -sin(yRadians), 0.0f))
        yRotationMatrix.setRow(2, floatArrayOf(sin(yRadians), 1.0f, cos(yRadians), 0.0f))

        val zRotationMatrix = Matrix4f.IDENTITY_MATRIX
        zRotationMatrix.setRow(0, floatArrayOf(cos(zRadians), sin(zRadians), 0.0f, 0.0f))
        zRotationMatrix.setRow(1, floatArrayOf(-sin(zRadians), cos(zRadians), 0.0f, 0.0f))

        zRotationMatrix * (yRotationMatrix * xRotationMatrix)
    }

    val scaleMatrix: TransformationFunction = { x, y, z ->
        val matrix = Matrix4f.IDENTITY_MATRIX
        for (i in 0 until 3)
            matrix.setValue(arrayOf(x, y, z)[i], i, i)
        matrix
    }
}
