package graphics

import math.Vector
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.openvr.Texture
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * @property[vertexList] List of position vectors
 * @property[textureCoordinates] List of texture vectors
 * @property[indexList] Adjacency array
 */
class Mesh(
    private val vertexList: MutableList<Vector>,
    private val textureCoordinates: MutableList<Vector>,
    private val indexList: IntArray,
) {

    init {
        storeDataInBuffer()
        loadToVAO(this)
    }

    private var _vaoID: Int? = null
    private lateinit var _vertexBuffer: FloatBuffer
    private lateinit var _indexBuffer: IntBuffer
    private lateinit var _textCoordsBuffer: FloatBuffer

    val vertexBuffer: FloatBuffer
        get() = _vertexBuffer

    val indexBuffer: IntBuffer
        get() = _indexBuffer

    val textCoordsBuffer
        get() = _textCoordsBuffer

    val vaoID: Int
        get() = _vaoID!!

    val vertexCount: Int
        get() = indexList.size

    private fun storeDataInBuffer() {
        val vertices = vertexList.map { it.flatten.toList() }.flatten().toFloatArray()
        val textures = textureCoordinates.map { it.flatten.toList().subList(0,2) }.flatten().toFloatArray()
        val indices = indexList

        _vertexBuffer = BufferUtils.createFloatBuffer(vertices.size)
        _textCoordsBuffer = BufferUtils.createFloatBuffer(textures.size)
        _indexBuffer = BufferUtils.createIntBuffer(indices.size)

        _vertexBuffer.put(vertices)
        _textCoordsBuffer.put(textures)
        _indexBuffer.put(indices)

        flip()
    }


    private fun flip() {
        _vertexBuffer.flip()
        _textCoordsBuffer.flip()
        _indexBuffer.flip()
    }

    fun clear() {
        _vertexBuffer.clear()
        _textCoordsBuffer.clear()
        _indexBuffer.clear()
    }

    companion object {

        private val vaos = mutableListOf<Int>()
        private val vbos = mutableListOf<Int>()
        private val textures = mutableListOf<Int>()

        private fun loadToVAO(mesh: Mesh) {
            val vaoID = createVAO()
            mesh._vaoID = vaoID
            bindIndicesBuffer(mesh.indexBuffer)
            storeDataInAttrList(0, 3,mesh.vertexBuffer)
            storeDataInAttrList(1, 2, mesh.textCoordsBuffer)
            unbindVAO()
            mesh.clear()
        }

        private fun createVAO(): Int {
            val vaoID = GL30.glGenVertexArrays()
            vaos.add(vaoID)
            GL30.glBindVertexArray(vaoID)
            return vaoID
        }

        fun loadTexture(path: String): Int{
            val textureID = TextureLoader.loadTexture(path)
            textures.add(textureID)
            return textureID
        }

        private fun storeDataInAttrList(attrNumber: Int, cardinality: Int, vertexBuffer: FloatBuffer) {
            val vboID = GL15.glGenBuffers()
            vbos.add(vboID)
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID)
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW)
            GL20.glVertexAttribPointer(attrNumber, cardinality, GL11.GL_FLOAT, false, 0, 0)
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

        private fun bindTextCoordsBuffer(textCoordsBuffer: FloatBuffer) {
            val vboID = GL15.glGenBuffers()
            vbos.add(vboID)
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID)
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textCoordsBuffer, GL15.GL_STATIC_DRAW)
            GL20.glVertexAttribPointer(1, 2, GL15.GL_FLOAT, false, 0, 0)
        }

        fun clean() {
            vaos.forEach { GL30.glDeleteVertexArrays(it) }
            vbos.forEach { GL30.glDeleteBuffers(it) }
            textures.forEach{ GL30.glDeleteBuffers(it)}
        }
    }
}
