package core

import org.lwjgl.Version
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import data.Constants
import util.Time

object Engine {
    val WIDTH: Int = Constants.WIDTH
    val HEIGHT: Int = Constants.HEIGHT
    val TITLE: String = Constants.TITLE

    private var running: Boolean = true

    private var lastUPS: Double = 0.0
    private var lastFPSUPSout: Double = 0.0

    private var UPS: Double = 0.0

//    private var EngineThread: Thread = Thread { run() }

    private fun run() {
        println("Hello LWJGL ${Version.getVersion()}!")
        GL.createCapabilities()
        GL11.glClearColor(1.0f, 1.0f, 0.0f, 0.0f)
        lastUPS = Time.currentTime()
        loop()
    }

    fun start() {
        running = true
        run()
    }

    init {

        // Setup an error callback
        Window.setupErrorCallback(System.err)

        // Configure GLFW
        Window.configure()

        // Create the window
        Window.createWindow(WIDTH, HEIGHT, TITLE)

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        Window.setKeyCallBack()

        MemoryStack.stackPush().also { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            Window.getWindowSize(pWidth, pHeight)

            // Center the window
            Window.centerWindow(pWidth, pHeight)
        }

        // Make the OpenGL context current
        Window.makeContextCurrent()

        // Enable v-sync
        Window.enableVSync()

        // Make the window visible
        Window.showWindow()
    }

    private fun loop() {
        while (running) {
            var passedTime: Double = Time.currentTime() - lastUPS
            var renderLock: Boolean = false

//          Print FPS/UPS and reset value
            if (Time.currentTime() - lastFPSUPSout > 1000000000) {
                println("FPS: $UPS")
                UPS = 0.0
                lastFPSUPSout = Time.currentTime()
            }

//          Update passedTime/UPDATE_CAP - times
            while ((passedTime) >= Time.UPDATE_CAP) {
                Window.update()
                renderLock = true
                UPS++
                passedTime -= Time.UPDATE_CAP
            }

//          Capture the moment of the last update and UPS incrementation
            lastUPS = Time.currentTime() - passedTime

//          Render if an update was performed
            if (renderLock)
                Window.render()

//          Process inputs
            Window.clean()

            if (Window.windowShouldClose()) {
                running = false
                Window.stop()
            }
        }
    }
}
