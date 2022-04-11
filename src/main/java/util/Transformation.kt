package util

import math.Matrix4f
import kotlin.math.cos
import kotlin.math.sin

object Transformation {

    fun getTranslationMatrix(x : Float, y : Float, z : Float) : Matrix4f {
        val matrix = Matrix4f.IDENTITY_MATRIX
        matrix.setColumn(3, arrayOf(x, y, z, 1.0f))
        return matrix
    }

    fun  getRotationMatrix(x : Float, y : Float, z : Float) : Matrix4f? {
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

}

fun main(){
    Transformation.getRotationMatrix(5.0f,4.0f,9.0f)?.getArray()?.forEach { it.forEach { println(it) } }
}