package core

import graphics.Mesh
import math.Matrix4f
import math.Vector
import util.Transformation

class Component(
    val mesh: Mesh,
    private var position: Vector,
    private var rotation: Vector,
    private var scale: Float
) {
    val move: (Float, Float, Float) -> Unit = { x, y, z -> position = position + Vector(x, y, z) }

    val rotate: (Float, Float, Float) -> Unit = { x, y, z -> rotation = rotation + Vector(x, y, z) }

    val setScale: (Float) -> Unit = { scale = it }

    val transformation: Matrix4f
        get() = Transformation(position, rotation, Vector(scale)).getTransformationMatrix()
}