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
import util.Transformation
import kotlin.random.Random

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
    val shader = ShaderProgramBuilder.createShaderProgram(
        "simpleVertexShader.vsh",
        "simpleFragmentShader.fsh"
    )

    var horizontal = 0f
    var rot = 0f
    var scale = 0.5f
    val scales = arrayOf(
        .3f, .5f, .7f, 1f, 1.3f
    )

    // Add CustomKeyListener
    Engine.apply {
        setKeyboardInputListener { _, key, _, action, _ ->
            if (action != GLFW.GLFW_RELEASE)
                when (key) {
                    GLFW.GLFW_KEY_LEFT -> horizontal -= .1f
                    GLFW.GLFW_KEY_RIGHT -> horizontal += .1f
                    GLFW.GLFW_KEY_UP -> rot++
                    GLFW.GLFW_KEY_DOWN -> rot--
                    GLFW.GLFW_KEY_SPACE -> scale = scales.random()
                }
        }
        resetInputListeners()
    }

    // Launch a new Coroutine
    launch {
        // Start CEL
        Engine.start(shader) {
            // Transform Mesh
            shader.loadTransformationMatrix(
                Transformation(
                    _scale = Vector(scale),
                    _translation = Vector(horizontal, 0f, 0f),
                    _rotation = Vector(0f, 0f, rot)
                ).getTransformationMatrix()
            )
//            i += .1f

            // Render Mesh
            Renderer.render(mesh)
            Thread.sleep(5)
        }

        // Cleanup
        Mesh.clean()
        shader.clean()
    }
}
