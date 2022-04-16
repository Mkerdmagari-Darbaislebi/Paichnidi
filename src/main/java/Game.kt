import core.Engine
import graphics.Color
import graphics.Mesh
import graphics.Renderer
import graphics.Vertex
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    val vbList2 = mutableListOf(
        Vertex(arrayOf(-.5f, -.5f, 0f), Color(255, 255, 0, 1f)),
        Vertex(arrayOf(.5f, -.5f, 0f), Color(255, 255, 0, 1f)),
        Vertex(arrayOf(.5f, -.0f, 0f), Color(255, 255, 0, 1f)),

        Vertex(arrayOf(.5f, -.5f, 0f), Color(255, 0, 0, 1f)),
        Vertex(arrayOf(-.5f, .5f, 0f), Color(255, 0, 0, 1f)),
        Vertex(arrayOf(-.5f, 0f, 0f), Color(255, 0, 0, 1f))
    )

    val renderer = Renderer()
    val engine = Engine()
    val mesh = Mesh(vbList2, IntArray(1) { 1 })
    Mesh.loadToVAO(mesh)

    launch {
        engine.start {
            renderer.render(mesh)
        }
        Mesh.clean()
    }
}
