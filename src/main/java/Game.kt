import core.Engine
import graphics.Color
import graphics.Mesh
import graphics.Renderer
import graphics.ShaderProgramBuilder
import  graphics.MeshLoader
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import math.Vector
import util.Transformation

fun main(): Unit = runBlocking {

    // Initialize Engine
    Engine.apply {
        setWindowWidth(800)
        setWindowHeight(800)
        setWindowTitle("Game Engine")
        setBackgroundColor(Color(100, 100, 120))
        init()
    }

    // VertexList
    val vertices = mutableListOf(
        Vector(-.4f, .3f, .0f),
        Vector(-.4f, -.7f, .0f),
        Vector(.6f, -.7f, .0f),
        Vector(.6f, .3f, .0f),
    )

    // IndexList
    val indices = intArrayOf(
        0, 2, 1,
        0, 3, 1,
    )

    // Create Mesh
    val mesh = MeshLoader.OBJToMesh("C:\\Users\\student\\Desktop\\IntellijIDEA\\igrushka\\Paichnidi\\src\\main\\java\\data\\shaders\\models\\KacebisKaci.obj")

    // Create ShaderProgram
    val shader = ShaderProgramBuilder.createShaderProgram(
        "simpleVertexShader.vsh",
        "simpleFragmentShader.fsh"
    )

    var i = 0f
    // Launch a new Coroutine
    launch {
        // Start CEL
        Engine.start(shader) {
            // Transform Mesh
            shader.loadTransformationMatrix(
                Transformation(
                    _scale = Vector(0.5f),
                    _rotation = Vector(i, 0f,i)
                ).getTransformationMatrix()
            )

            i+=1f

            // Render Mesh
            Renderer.render(mesh)
            Thread.sleep(5)
        }

        // Cleanup
        Mesh.clean()
        shader.clean()
    }
}
