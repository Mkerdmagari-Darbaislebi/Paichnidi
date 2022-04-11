package util

import math.Matrix4f
import math.Vector
import kotlin.math.cos
import kotlin.math.sin

class Transformation(
    private var translation : Vector = Vector(0.0f, 0.0f, 0.0f),
    private var rotation : Vector = Vector(0.0f, 0.0f, 0.0f),
    private var scale : Vector = Vector(1.0f, 1.0f, 1.0f)
) {

    val getTranslation get() = translation
    fun setTranslation(vector : Vector) {translation = vector}

    val getRotation get() = rotation
    fun setRotation (vector : Vector) {rotation = vector}

    val getScale get() = scale
    fun setScale (vector : Vector) {scale = vector}

    fun getTransformation() : Matrix4f {
        val translationMatrix = getTranslationMatrix(translation.x, translation.y, translation.z)
        val rotationMatrix = getRotationMatrix(rotation.x, rotation.y, rotation.z)
        val scaleMatrix = getScaleMatrix(scale.x, scale.y, scale.z)

        return (translationMatrix * (rotationMatrix!! * scaleMatrix)!!) as Matrix4f
    }

    companion object {
        fun getTranslationMatrix(x: Float, y: Float, z: Float): Matrix4f {
            val matrix = Matrix4f.IDENTITY_MATRIX
            matrix.setColumn(3, arrayOf(x, y, z, 1.0f))
            return matrix
        }

        fun getRotationMatrix(x: Float, y: Float, z: Float): Matrix4f? {
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

            return (zRotationMatrix * (yRotationMatrix * xRotationMatrix)!!) as Matrix4f
        }

        fun getScaleMatrix(x: Float, y: Float, z: Float) : Matrix4f {
            val matrix = Matrix4f.IDENTITY_MATRIX
            for (i in 0 until arrayOf(x, y, z).size)
                matrix.setValue(arrayOf(x, y, z)[i], i, i)
            return matrix
        }
    }
}

fun main(){
    Transformation.getRotationMatrix(5.0f,4.0f,9.0f)?.getArray()?.forEach { it.forEach { println(it) } }
}