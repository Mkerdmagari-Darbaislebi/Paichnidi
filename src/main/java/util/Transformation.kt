package util

import math.Matrix4f
import math.Vector
import kotlin.math.cos
import kotlin.math.sin

class Transformation(
    private var _translation: Vector = Vector(0f),
    private var _rotation: Vector = Vector(0f),
    private var _scale: Vector = Vector(1.0f)
) {

    val translation
        get() = _translation

    val scale
        get() = _scale

    val rotation
        get() = _rotation

    fun setTranslation(vector: Vector) = run { _translation = vector }

    fun setRotation(vector: Vector) = run { _rotation = vector }

    fun setScale(vector: Vector) = run { _scale = vector }

    fun getTransformationMatrix(): Matrix4f {
        val translationMatrix = getTranslationMatrix(_translation.x, _translation.y, _translation.z)
        val rotationMatrix = getRotationMatrix(_rotation.x, _rotation.y, _rotation.z)
        val scaleMatrix = getScaleMatrix(_scale.x, _scale.y, _scale.z)

        return (translationMatrix * (rotationMatrix * scaleMatrix))
    }

    companion object {

        fun getTranslationMatrix(x: Float, y: Float, z: Float): Matrix4f {
            val matrix = Matrix4f.IDENTITY_MATRIX
            matrix.setColumn(3, arrayOf(x, y, z, 1.0f))
            return matrix
        }

        fun getRotationMatrix(x: Float, y: Float, z: Float): Matrix4f {
            val xRadians = Math.toRadians(x.toDouble()).toFloat()
            val yRadians = Math.toRadians(y.toDouble()).toFloat()
            val zRadians = Math.toRadians(z.toDouble()).toFloat()

            val xRotationMatrix = Matrix4f.IDENTITY_MATRIX
            xRotationMatrix.setRow(1, arrayOf(0.0f, cos(xRadians), -sin(xRadians), 0.0f))
            xRotationMatrix.setRow(2, arrayOf(0.0f, sin(xRadians), cos(xRadians), 0.0f))

            val yRotationMatrix = Matrix4f.IDENTITY_MATRIX
            yRotationMatrix.setRow(0, arrayOf(cos(yRadians), -sin(yRadians), 0.0f, 0.0f))
            yRotationMatrix.setRow(2, arrayOf(sin(yRadians), cos(yRadians), 1.0f, 0.0f))

            val zRotationMatrix = Matrix4f.IDENTITY_MATRIX
            zRotationMatrix.setRow(0, arrayOf(cos(zRadians), -sin(zRadians), 0.0f, 0.0f))
            zRotationMatrix.setRow(1, arrayOf(sin(zRadians), cos(zRadians), 0.0f, 0.0f))

            return (zRotationMatrix * (yRotationMatrix * xRotationMatrix))
        }

        fun getScaleMatrix(x: Float, y: Float, z: Float): Matrix4f {
            val matrix = Matrix4f.IDENTITY_MATRIX
            for (i in 0 until arrayOf(x, y, z).size)
                matrix.setValue(arrayOf(x, y, z)[i], i, i)
            return matrix
        }
    }
}
