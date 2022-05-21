import core.Engine
import graphics.Color
import graphics.Mesh
import graphics.Renderer
import graphics.ShaderProgramBuilder
import  graphics.loaders.ObjMeshLoader
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

    // Create Mesh
    val mesh = ObjMeshLoader.load("cube.obj")

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
