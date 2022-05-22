import core.Component
import core.Engine
import graphics.Color
import graphics.Mesh
import graphics.Renderer
import graphics.ShaderProgramBuilder
import  graphics.loaders.ObjMeshLoader
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import math.Vector
import org.lwjgl.glfw.GLFW

fun main(): Unit = runBlocking {

    // Initialize Engine
    Engine.apply {
        setWindowWidth(1920)
        setWindowHeight(1080)
        setWindowTitle("Game Engine")
        setBackgroundColor(Color(100, 100, 120))
        init()
    }

    // Create Mesh
    val mesh = ObjMeshLoader.load("cube.obj")

    // Create ShaderProgram
    val shaderProgram = ShaderProgramBuilder.createShaderProgram(
        "simpleVertexShader.vsh",
        "simpleFragmentShader.fsh"
    )

    val scales = arrayOf(
        .3f, .5f, .7f, 1f, 1.3f
    )

    val component = Component(mesh, Vector(0f, 0f, 0f), Vector(0f, 0f, 0f), 0.7f)

    // Add CustomKeyListener
    Engine.apply {
        setKeyboardInputListener { _, key, _, action, _ ->
            if (action != GLFW.GLFW_RELEASE)
                when (key) {
                    GLFW.GLFW_KEY_LEFT -> component.move(-0.1f, 0f, 0f)
                    GLFW.GLFW_KEY_RIGHT -> component.move(-0.1f, 0f, 0f)
                    GLFW.GLFW_KEY_UP -> component.rotate(0f, 0.1f, 0f)
                    GLFW.GLFW_KEY_DOWN -> component.rotate(0f, -0.1f, 0f)
                    GLFW.GLFW_KEY_SPACE -> component.setScale(scales.random())
                }
        }
        resetInputListeners()
    }


    // Launch a new Coroutine
    launch {
        // Start CEL
        Engine.start(shaderProgram) {
            // Transform Mesh
//            i += .1f

            // Render Mesh
            Renderer.render(component, shaderProgram)
            Thread.sleep(5)
        }

        // Cleanup
        Mesh.clean()
        shaderProgram.clean()
    }
}