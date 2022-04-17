import core.Engine
import graphics.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    val vbList2 = mutableListOf(
        Vertex(arrayOf(-.5f, .5f, 0f), Color(255, 0, 0, 1f)),
        Vertex(arrayOf(-.5f, -.5f, 0f), Color(0, 255, 0, 1f)),
        Vertex(arrayOf(.5f, -.5f, 0f), Color(0, 0, 255, 1f)),
        Vertex(arrayOf(.5f, .5f, 0f), Color(0, 0, 0, 1f))
    )

    val indices = intArrayOf(
        0, 2, 1, 0, 3, 1
    )

    val renderer = Renderer()
    val engine = Engine()
    val mesh = Mesh(vbList2, indices)
    Mesh.loadToVAO(mesh)
    val path = "src/main/java/data/shaders"
    val shader = ShaderProgramBuilder.createShaderProgram(
        "$path/simpleVertexShader.vsh",
        "$path/simpleFragmentShader.fsh"
    )

    launch {
        engine.start {
            shader.start()
            renderer.render(mesh)
            shader.stop()
        }
        Mesh.clean()
        shader.clean()
    }
}
