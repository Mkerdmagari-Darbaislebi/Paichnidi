package graphics

import data.Constants
import data.Constants.PLY_MODEL_PATH
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import java.io.File
import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.math.cos
import kotlin.math.sin

class Mesh(
    private var _vertexList: MutableList<Vertex>,
    private var _indexList: MutableList<Int>
) {

    private var _vertexBuffer: FloatBuffer =
        BufferUtils.createFloatBuffer(_vertexList.size * Constants.VERTEX_ARRAY_CARDINALITY)

    private var _colorBuffer: FloatBuffer =
        BufferUtils.createFloatBuffer(_vertexList.size * Constants.COLOR_ARRAY_CARDINALITY)

    private var _indexBuffer: IntBuffer =
        BufferUtils.createIntBuffer(_indexList.size)

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
        _indexBuffer.put(_indexList.toIntArray())
        _indexBuffer.position(0)
    }


    fun flip() {
        _vertexBuffer.flip()
        _colorBuffer.flip()
        _indexBuffer.flip()
    }

    fun clear() {
        _vertexBuffer.clear()
        _colorBuffer.clear()
        _indexBuffer.clear()
    }

    fun draw(drawMode: Int = GL11.GL_POLYGON) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
//        println(_vertexList[0].flatten())
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)
        GL11.glEnableClientState(GL11.GL_INDEX_ARRAY)
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, _vertexBuffer)
        GL11.glColorPointer(4, GL11.GL_FLOAT, 0, _colorBuffer)
        GL11.glIndexPointer(0, _indexBuffer)
        GL11.glDrawArrays(drawMode, 0, _vertexList.size)

        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY)
        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY)
        GL11.glDisableClientState(GL11.GL_INDEX_ARRAY)

//        clear()
//        _vertexList = _vertexList.map { it.scalars }.map {
//            arrayOf(
//                it[0] * cos(0.2f) - it[1] * sin(0.2f),
//                it[0] * cos(0.2f) + it[1] * sin(0.2f),
//                it[2]
//            )
//        }.map {
//            Vertex(
//                it, Color(
//                    128,
//                    128, 128, 1f
//                )
//            )
//        }.toMutableList()
//
//        _vertexBuffer.put(_vertexList.map { it.flatten() }.flatten().toFloatArray())
//        _vertexBuffer.position(0)
//        _colorBuffer.put(_vertexList.map { it.flattenColor() }.flatten().toFloatArray())
//        _colorBuffer.position(0)
//        _indexBuffer.put(_indexList.toIntArray())
//        _indexBuffer.position(0)

    }

    companion object {
        fun objToMesh(): Mesh {
            val vertexList = mutableListOf<Vertex>()
            val vertexIndices = mutableListOf<Int>()
            var lineData = listOf<String>()
            File(PLY_MODEL_PATH).forEachLine {
                lineData = it.split(" ")
                if (it.startsWith("v ")) {
                    vertexList.add(
                        Vertex(
                            arrayOf(lineData[1].toFloat() / 5, lineData[2].toFloat() / 5, lineData[3].toFloat() / 5),
                            Color(128, 128, 128, 1f)
                        )
                    )
                } else if (it.startsWith("f ")) {
                    for (i in 1 until lineData.size) {
                        vertexIndices.addAll(lineData[i].split("/").map { a -> a.toInt() })
                    }
                }
            }
            return Mesh(vertexList, vertexIndices)
        }
    }
}

fun main() {
    var mesh = Mesh.objToMesh()
}