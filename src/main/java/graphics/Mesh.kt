package graphics

import data.Constants
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Mesh(
    private val vertexList: MutableList<Vertex>,
    private val indexList: IntArray
) {
    private var _vaoID: Int? = null
    private lateinit var _vertexBuffer: FloatBuffer
    private lateinit var _indexBuffer: IntBuffer
    private lateinit var _colorBuffer: FloatBuffer

    val vertexBuffer: FloatBuffer
        get() = _vertexBuffer

    val indexBuffer: IntBuffer
        get() = _indexBuffer

    val colorBuffer: FloatBuffer
        get() = _colorBuffer

    val vaoID: Int
        get() = _vaoID!!

    val vertexCount: Int
        get() = indexList.size

    init {
        storeDataInBuffer()
    }

    private fun storeDataInBuffer() {
        val vertices = vertexList.map { it.flatten() }.flatten().toFloatArray()
        val indices = indexList
        val colors = vertexList.map { it.flattenColor() }.flatten().toFloatArray()

        _vertexBuffer = BufferUtils.createFloatBuffer(vertices.size)
        _indexBuffer = BufferUtils.createIntBuffer(indices.size)
        _colorBuffer = BufferUtils.createFloatBuffer(colors.size)

        _vertexBuffer.put(vertices)
        _indexBuffer.put(indices)
        _colorBuffer.put(colors)

        flip()
    }


    private fun flip() {
        _vertexBuffer.flip()
        _indexBuffer.flip()
        _colorBuffer.flip()
    }

    fun clear() {
        _vertexBuffer.clear()
        _indexBuffer.clear()
        _colorBuffer.clear()
    }

    companion object {

        private val vaos = mutableListOf<Int>()
        private val vbos = mutableListOf<Int>()

        fun loadToVAO(mesh: Mesh) {
            val vaoID = createVAO()
            mesh._vaoID = vaoID
            bindIndicesBuffer(mesh.indexBuffer)
            storeDataInAttrList(0, mesh.vertexBuffer)
            unbindVAO()
            mesh.clear()
        }

        private fun createVAO(): Int {
            val vaoID = GL30.glGenVertexArrays()
            vaos.add(vaoID)
            GL30.glBindVertexArray(vaoID)
            return vaoID
        }

        private fun storeDataInAttrList(attrNumber: Int, vertexBuffer: FloatBuffer) {
            val vboID = GL15.glGenBuffers()
            vbos.add(vboID)
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID)
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW)
            GL20.glVertexAttribPointer(attrNumber, Constants.VERTEX_ARRAY_CARDINALITY, GL11.GL_FLOAT, false, 0, 0)
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        }

        private fun unbindVAO() {
            GL30.glBindVertexArray(0)
        }

        private fun bindIndicesBuffer(indexBuffer: IntBuffer) {
            val vboID = GL15.glGenBuffers()
            vbos.add(vboID)
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID)
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW)
//            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
        }

        fun clean() {
            vaos.forEach { GL30.glDeleteVertexArrays(it) }
            vbos.forEach { GL30.glDeleteBuffers(it) }
        }
    }
}
