package graphics

import org.lwjgl.BufferUtils
import java.nio.FloatBuffer

// this code will be improved soon
class Mesh3D(
    private var _vertexList : MutableList<Vertex3D>
) {
    private var _vertexBuffer : FloatBuffer = BufferUtils.createFloatBuffer(_vertexList.size)
    private var _colorBuffer: FloatBuffer = BufferUtils.createFloatBuffer(_vertexList.size)

    init {
        var positions = mutableListOf<Float>()
        var colors = mutableListOf<Float>()
        for (vertex in _vertexList){
            positions.addAll(vertex.flatten())
            colors.addAll(vertex.color.flatten())
        }
        _vertexBuffer.put(positions.toFloatArray())
        _vertexBuffer.position(0)
        _colorBuffer.put(positions.toFloatArray())
        _colorBuffer.position(0)
    }

    val vertexBuffer : FloatBuffer get() = _vertexBuffer
    val colorBuffer : FloatBuffer get() = _colorBuffer

    fun flip(){
        _vertexBuffer.flip()
        _colorBuffer.flip()
    }

    fun clear(){
        _vertexBuffer.clear()
        _colorBuffer.clear()
    }
}