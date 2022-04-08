import core.SceneBuilder
import data.shaders.FragmentShaders
import data.shaders.VertexShaders
import graphics.Color
import graphics.Mesh
import graphics.Vertex
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Game(
    private val meshes: List<Mesh>,
    private val vShader: String,
    private val fShader: String
) {
    fun start() =
        SceneBuilder(vShader, fShader, meshes).build()
}

fun main(): Unit = runBlocking {

    val vbList2 = mutableListOf(
        Vertex(arrayOf(-.5f, -.5f, 0f), Color(255, 0, 0, 1f)),
        Vertex(arrayOf(.5f, -.5f, 0f), Color(255, 0, 0, 1f)),
        Vertex(arrayOf(.5f, -.0f, 0f), Color(255, 0, 0, 1f)),

        Vertex(arrayOf(.5f, -.5f, 0f), Color(255, 0, 0, 1f)),
        Vertex(arrayOf(-.5f, .5f, 0f), Color(255, 0, 0, 1f)),
        Vertex(arrayOf(-.5f, 0f, 0f), Color(255, 0, 0, 1f))
    )

    val meshes = listOf( Mesh(vbList2) )


    val vsh = VertexShaders.SIMPLE_VERTEX_SHADER
    val fsh = FragmentShaders.SIMPLE_FRAGMENT_SHADER(1f,0f,0f,1f)

    launch {
        Game(meshes, vsh, fsh).start()
    }
}