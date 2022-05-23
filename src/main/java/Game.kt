import unit.Component
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
import unit.Camera

fun main(): Unit = runBlocking {

    // Initialize Engine
    Engine.apply {
        setWindowWidth(1200)
        setWindowHeight(900)
        setWindowTitle("Game Engine")
        setBackgroundColor(Color(100, 100, 120))
        init()
    }

    val vertices = mutableListOf(
        Vector(-.5f, .5f, 2f),
        Vector(-.5f, -.5f, 2f),
        Vector(.5f, -.5f, 2f),
        Vector(.5f, .5f, 2f),
    )

    val indices = intArrayOf(
        0, 1, 3,
        3, 1, 2
    )

    // Create Mesh
    val mesh = ObjMeshLoader.load("Grass_Block.obj")
////Mesh(vertices, indices)
    Mesh.loadTexture("Grass_Block.png")
    // Create ShaderProgram
    val shaderProgram = ShaderProgramBuilder.createShaderProgram(
        "simpleVertexShader.vsh",
        "simpleFragmentShader.fsh"
    )

    // Create Component
    val component = Component(mesh, Vector(0f, 0f, -10f), Vector(0f, 0f, 66f), 1f)

    // Add ComponentKeyListener
    val movement = .5f
    val rotation = 6f
    Engine.apply {
        setKeyboardInputListener { _, key, _, action, _ ->
            if (action != GLFW.GLFW_RELEASE)
                when (key) {
                    GLFW.GLFW_KEY_Z -> { component.move(0f, 0f, -movement / 2) }
                    GLFW.GLFW_KEY_X -> { component.move(0f, 0f, movement / 2) }
                    GLFW.GLFW_KEY_LEFT -> component.move(-movement, 0f, 0f)
                    GLFW.GLFW_KEY_RIGHT -> component.move(movement, 0f, 0f)
                    GLFW.GLFW_KEY_UP -> component.move(0f, movement, 0f)
                    GLFW.GLFW_KEY_DOWN -> component.move(0f, -movement, 0f)
                    GLFW.GLFW_KEY_SPACE -> component.setScale(listOf(.3f, .5f, .7f, 1f).random())
                }
        }
        resetInputListeners()
    }

    // Create Camera
    val camera = Camera()
    // Set DefaultCameraKeyboardInputListener
//    camera.setCameraKeyboardInputListener()

    // Init And Load Projection Matrix
    Renderer.initProjectionMatrix(shaderProgram)

    // Launch a new Coroutine
    launch {
        // Start CEL
        Engine.start(shaderProgram) {
            shaderProgram.loadViewMatrix(camera)
            component.rotate(0f, .03f, 0f)
            // Render Mesh
            Renderer.render(component, shaderProgram)
            Thread.sleep(5)
        }

        // Cleanup
        Mesh.clean()
        shaderProgram.clean()
    }
}