import core.Engine
import data.MeshLoader
import graphics.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Game {

    val vbList = mutableListOf(
        Vertex(arrayOf(-.5f, -.5f, 0f), Color(255, 0,0,1f)),
        Vertex(arrayOf(.5f, -.5f, 0f), Color(255, 0,0,1f)),
        Vertex(arrayOf(.5f, -.0f, 0f), Color(255, 0,0,1f))
    )

    val mesh = Mesh(vbList)

    fun start() {
        MeshLoader.meshes.add(mesh)
        Engine.start()
    }
}

fun main(): Unit = runBlocking {
    launch {
        Game().start()
    }
}
