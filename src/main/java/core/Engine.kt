package core

import graphics.Color
import graphics.ShaderProgram
import org.lwjgl.glfw.GLFWCursorPosCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.glfw.GLFWMouseButtonCallback
import org.lwjgl.glfw.GLFWScrollCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import util.Time

private typealias KeyboardInputListener = (Long, Int, Int, Int, Int) -> Unit
private typealias CursorAndScrollWheelInputListener = (Long, Double, Double) -> Unit
private typealias MouseInputListener = (Long, Int, Int, Int) -> Unit

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


    // InputListeners
    private var keyboardInputListener: GLFWKeyCallback? = null
    private var cursorMovementInputListener: GLFWCursorPosCallback? = null
    private var mouseButtonInputListener: GLFWMouseButtonCallback? = null
    private var scrollWheelInputListener: GLFWScrollCallback? = null

    // InputListenerSetters
    fun setKeyboardInputListener(keyListenerFunction: KeyboardInputListener) {
        keyboardInputListener = object : GLFWKeyCallback() {
            override operator fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) =
                keyListenerFunction(window, key, scancode, action, mods)

        }

    }


    fun setCursorMovementInputListener(cursorListenerFunction: CursorAndScrollWheelInputListener) {
        cursorMovementInputListener = object : GLFWCursorPosCallback() {
            override operator fun invoke(window: Long, xpos: Double, ypos: Double) =
                cursorListenerFunction(window, xpos, ypos)

        }
    }

    fun setMouseButtonInputListener(mouseListenerFunction: MouseInputListener) {
        mouseButtonInputListener = object : GLFWMouseButtonCallback() {
            override fun invoke(window: Long, button: Int, action: Int, mods: Int) =
                mouseListenerFunction(window, button, action, mods)

        }
    }

    fun setScrollWheelInputListener(scrollWheelListenerFunction: CursorAndScrollWheelInputListener) {
        scrollWheelInputListener = object : GLFWScrollCallback() {
            override fun invoke(window: Long, xoffset: Double, yoffset: Double) =
                scrollWheelListenerFunction(window, xoffset, yoffset)

        }
    }

    fun resetInputListeners() = Window.setAllInputCallBacks(
        keyboardInputListener,
        cursorMovementInputListener,
        mouseButtonInputListener,
        scrollWheelInputListener
    )

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
        resetInputListeners()

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
