package unit

import graphics.Mesh
import math.Matrix4f
import math.Vector
import util.Transformations

class Component(
    val mesh: Mesh,
    var position: Vector,
    var rotation: Vector,
    var scale: Float
) {
    val move: (Float, Float, Float) -> Unit = { x, y, z ->
        position.x += x
        position.y += y
        position.z += z
    }

    val rotate: (Float, Float, Float) -> Unit = { x, y, z -> rotation = rotation + Vector(x, y, z) }

    val setScale: (Float) -> Unit = { scale = it }

    val transformation: Matrix4f
        get() = Transformations.transformationMatrix(position, rotation, Vector(scale))
}