package core

import graphics.Color
import graphics.ShaderProgram
import org.lwjgl.Version
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import util.Time

object Engine {

    // CEL Controllers
    private var running: Boolean = true
    private var lastUPS: Double = 0.0
    private var lastFPSUPSout: Double = 0.0
    private var UPS: Double = 0.0

    // Window Attributes
    private var windowWidth = 500
    private var windowHeight = 500
    private var windowTitle = "Game"
    private var windowBackgroundColor = Color(255, 0, 0)

    // Window AttributeSetters
    fun setBackgroundColor(color: Color) {
        windowBackgroundColor = color
    }

    fun setWindowWidth(width: Int) {
        windowWidth = width
    }

    fun setWindowHeight(height: Int) {
        windowHeight = height
    }

    fun setWindowTitle(title: String) {
        windowTitle = title
    }

    // Start CEL
    fun start(
        shaderProgram: ShaderProgram,
        action: () -> Unit
    ) {
        // Looping Condition
        running = true

        // Background Setup
        windowBackgroundColor.apply {
            GL11.glClearColor(
                red, green,
                blue, alpha
            )
        }

        // Time Util Setup
        lastUPS = Time.currentTime()

        // CEL call
        coreEngineLoop(shaderProgram) { action() }
    }

    // Engine Initializer
    fun init() {

        // Setup an error callback
        Window.setupErrorCallback(System.err)

        // Configure GLFW
        Window.configure()

        // Create the window
        Window.createWindow(
            windowWidth,
            windowHeight,
            windowTitle
        )

        // Make the OpenGL context current
        Window.makeContextCurrent()

        // Set up capabilities
        GL.createCapabilities()

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        Window.setAllInputCallBacks()

        MemoryStack.stackPush().also { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            Window.getWindowSize(pWidth, pHeight)

            // Center the window
            Window.centerWindow(pWidth, pHeight)
        }

        // Enable v-sync
        Window.enableVSync()

        // Make the window visible
        Window.showWindow()

    }

    private fun handleExit() = Window.apply {
        if (windowShouldClose()) {
            running = false
            stop()
        }
    }

    // CEL
    private fun coreEngineLoop(
        shaderProgram: ShaderProgram,
        action: () -> Unit
    ) {
        while (running) {

            var passedTime: Double = Time.currentTime() - lastUPS
            var renderLock = false

            // Print FPS/UPS and reset value
            if (Time.currentTime() - lastFPSUPSout > 1000000000) {
                println("FPS: $UPS")
                UPS = 0.0
                lastFPSUPSout = Time.currentTime()
            }

            // Update passedTime/UPDATE_CAP - times
            while ((passedTime) >= Time.UPDATE_CAP) {
                Window.update()
                renderLock = true
                UPS++
                passedTime -= Time.UPDATE_CAP
            }

            // Capture the moment of the last update and UPS incrementation
            lastUPS = Time.currentTime() - passedTime

            shaderProgram.start()
            action()
            shaderProgram.stop()

            // Render if an update was performed
            if (renderLock)
                Window.render()

            // Process inputs
            Window.clean()

            handleExit()
        }
    }
}
