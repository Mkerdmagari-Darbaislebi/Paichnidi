package graphics

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import java.nio.FloatBuffer

class Mesh(_vertexList: MutableList<VertexBuilder>) {

    private var _vertexBuffer: FloatBuffer = BufferUtils.createFloatBuffer(_vertexList.size * 2)
    private var _colorBuffer: FloatBuffer = BufferUtils.createFloatBuffer(_vertexList.size * 4)

    init {
        val positions = _vertexList.map { it.flatten() }.flatten().toFloatArray()
        val colors = _vertexList.map { it.flattenColor() }.flatten().toFloatArray()

        _vertexBuffer.put(positions)
        _vertexBuffer.position(0)
        _colorBuffer.put(colors)
        _colorBuffer.position(0)
    }

    val vertexBuffer: FloatBuffer get() = _vertexBuffer
    val colorBuffer: FloatBuffer get() = _colorBuffer

    fun flip() {
        _vertexBuffer.flip()
        _colorBuffer.flip()
    }

    fun clear() {
        _vertexBuffer.clear()
        _colorBuffer.clear()
    }

    fun draw(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)
        GL11.glVertexPointer(3, GL11.GL_FLOAT,0 , _vertexBuffer)
        GL11.glColorPointer(4,GL11.GL_FLOAT,0, _colorBuffer)
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3)
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY)
        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY)
    }

}