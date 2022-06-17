package core

import graphics.Color
import graphics.ShaderProgram
import org.lwjgl.glfw.GLFWCursorPosCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.glfw.GLFWMouseButtonCallback
import org.lwjgl.glfw.GLFWScrollCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import org.lwjgl.opengl.GL11.glEnable
import org.lwjgl.system.MemoryStack
import util.CursorAndScrollWheelInputListener
import util.KeyboardInputListener
import util.MouseInputListener
import util.Time

/**
 * Singleton to start CEL
 */
object Engine {

    // CEL Controllers
    private var running: Boolean = true
    private var lastUPS: Double = 0.0
    private var lastFPSUPSout: Double = 0.0
    private var UPS: Double = 0.0

    // Window Attributes
    private var _windowWidth = 500
    private var _windowHeight = 500
    private var _windowTitle = "Game"
    private var _windowBackgroundColor = Color(255, 0, 0)

    val windowWidth get() = _windowWidth
    val windowHeight get() = _windowHeight
    val windowTitle get() = _windowTitle
    val windowBackgroundColor get() = _windowBackgroundColor

    /**
     * Sets background color
     * @param[color] color to set
     */
    fun setBackgroundColor(color: Color) {
        _windowBackgroundColor = color
    }

    /**
     * Sets window width
     * @param[width] width to set
     */
    fun setWindowWidth(width: Int) {
        _windowWidth = width
    }

    /**
     * Sets window height
     * @param[height] height to set
     */
    fun setWindowHeight(height: Int) {
        _windowHeight = height
    }

    /**
     * Sets window title
     * @param[title] title to set
     */
    fun setWindowTitle(title: String) {
        _windowTitle = title
    }


    // InputListeners
    private var keyboardInputListeners: MutableList<GLFWKeyCallback> = mutableListOf()
    private var cursorMovementInputListeners: MutableList<GLFWCursorPosCallback> = mutableListOf()
    private var mouseButtonInputListeners: MutableList<GLFWMouseButtonCallback> = mutableListOf()
    private var scrollWheelInputListeners: MutableList<GLFWScrollCallback> = mutableListOf()

    /**
     * Adds keyboard input listener
     * @param[keyListenerFunction] callback
     */
    fun setKeyboardInputListener(keyListenerFunction: KeyboardInputListener) =
        keyboardInputListeners.add(object : GLFWKeyCallback() {
            override operator fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) =
                keyListenerFunction(window, key, scancode, action, mods)

        })


    /**
     * Adds cursor movement input listener
     * @param[cursorListenerFunction] callback
     */
    fun setCursorMovementInputListener(cursorListenerFunction: CursorAndScrollWheelInputListener) =
        cursorMovementInputListeners.add(object : GLFWCursorPosCallback() {
            override operator fun invoke(window: Long, xpos: Double, ypos: Double) =
                cursorListenerFunction(window, xpos, ypos)

        })


    /**
     * Adds mouse button input listener
     * @param[mouseListenerFunction] callback
     */
    fun setMouseButtonInputListener(mouseListenerFunction: MouseInputListener) =
        mouseButtonInputListeners.add(object : GLFWMouseButtonCallback() {
            override fun invoke(window: Long, button: Int, action: Int, mods: Int) =
                mouseListenerFunction(window, button, action, mods)

        })


    /**
     * Adds scroll whell input listener
     * @param[scrollWheelListenerFunction] callback
     */
    fun setScrollWheelInputListener(scrollWheelListenerFunction: CursorAndScrollWheelInputListener) =
        scrollWheelInputListeners.add(object : GLFWScrollCallback() {
            override fun invoke(window: Long, xoffset: Double, yoffset: Double) =
                scrollWheelListenerFunction(window, xoffset, yoffset)

        })


    /**
     * Clears all input listeners
     */
    fun resetInputListeners() = Window.setAllInputCallBacks(
        keyboardInputListeners,
        cursorMovementInputListeners,
        mouseButtonInputListeners,
        scrollWheelInputListeners
    )

    /**
     * Starts CEL
     * @param[shaderProgram] handles shading
     * @param[action] game itself
     */
    fun start(
        shaderProgram: ShaderProgram,
        action: () -> Unit
    ) {
        // Looping Condition
        running = true

        // Background Setup
        _windowBackgroundColor.apply {
            GL11.glClearColor(
                red, green,
                blue, alpha
            )
            glEnable(GL_DEPTH_TEST)
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
            _windowWidth,
            _windowHeight,
            _windowTitle
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

    /**
     * Stops CEL
     */
    private fun handleExit() = Window.apply {
        if (windowShouldClose()) {
            running = false
            stop()
        }
    }

    /**
     * CEL
     * @param[shaderProgram] to start and end shaderProgram
     * @param[action] game logic
     */
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
