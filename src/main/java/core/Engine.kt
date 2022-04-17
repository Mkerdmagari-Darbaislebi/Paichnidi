package core

import data.Constants
import graphics.Color
import org.lwjgl.Version
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import util.Time

class Engine(
    private var window: Window =
        Window(
            Constants.WIDTH,
            Constants.HEIGHT,
            Constants.TITLE
        )
) {

    private var running: Boolean = true
    private var lastUPS: Double = 0.0
    private var lastFPSUPSout: Double = 0.0

    private var UPS: Double = 0.0

    private fun setBackgroundColor(color: Color) =
        GL11.glClearColor(color.red, color.green, color.blue, color.alpha)


    private fun run(backgroundColor: Color, action: () -> Unit) {
        println("Hello LWJGL ${Version.getVersion()}!")
        setBackgroundColor(backgroundColor)
        lastUPS = Time.currentTime()
        loop(action)
    }

    fun start(action: () -> Unit) {
        running = true
        run(
            Color(255, 0, 255, 1f),
            action
        )
    }

    init {

        // Setup an error callback
        window.setupErrorCallback(System.err)

        // Configure GLFW
        window.configure()

        // Create the window
        window.createWindow()

        // Make the OpenGL context current
        window.makeContextCurrent()

        // Set up capabilities
        GL.createCapabilities()

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        window.setKeyCallBack()

        MemoryStack.stackPush().also { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            window.getWindowSize(pWidth, pHeight)

            // Center the window
            window.centerWindow(pWidth, pHeight)
        }

        // Enable v-sync
        window.enableVSync()

        // Make the window visible
        window.showWindow()

    }

    private fun handleExit() = window.apply {
        if (windowShouldClose()) {
            running = false
            stop()
        }
    }

    private fun loop(action: () -> Unit) {
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
                window.update()
                renderLock = true
                UPS++
                passedTime -= Time.UPDATE_CAP
            }

            // Capture the moment of the last update and UPS incrementation
            lastUPS = Time.currentTime() - passedTime

            action()

            // Render if an update was performed
            if (renderLock)
                window.render()

            // Process inputs
            window.clean()


            handleExit()
        }
    }
}
