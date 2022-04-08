package graphics

import data.Constants
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import java.nio.FloatBuffer

class Mesh(private val _vertexList: MutableList<Vertex>) {

    private var _vertexBuffer: FloatBuffer =
        BufferUtils.createFloatBuffer(_vertexList.size * Constants.VERTEX_ARRAY_CARDINALITY)

    private var _colorBuffer: FloatBuffer =
        BufferUtils.createFloatBuffer(_vertexList.size * Constants.COLOR_ARRAY_CARDINALITY)

    val vertexBuffer: FloatBuffer
        get() = _vertexBuffer

    val colorBuffer: FloatBuffer
        get() = _colorBuffer

    init {
        val positions = _vertexList.map { it.flatten() }.flatten().toFloatArray()
        val colors = _vertexList.map { it.flattenColor() }.flatten().toFloatArray()

        _vertexBuffer.put(positions)
        _vertexBuffer.position(0)
        _colorBuffer.put(colors)
        _colorBuffer.position(0)
    }


    fun flip() {
        _vertexBuffer.flip()
        _colorBuffer.flip()
    }

    fun clear() {
        _vertexBuffer.clear()
        _colorBuffer.clear()
    }

    private fun enableClientState() {
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)
    }

    private fun specifyPointerArray() {
        GL11.glVertexPointer(Constants.VERTEX_ARRAY_CARDINALITY, GL11.GL_FLOAT, 0, _vertexBuffer)
        GL11.glColorPointer(Constants.COLOR_ARRAY_CARDINALITY, GL11.GL_FLOAT, 0, _colorBuffer)
    }

    private fun disableClientState() {
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY)
        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY)
    }

    fun draw(drawMode: Int = GL11.GL_TRIANGLES) {
        enableClientState()
        specifyPointerArray()
        GL11.glDrawArrays(drawMode, 0, _vertexList.size)
        disableClientState()
    }

}
