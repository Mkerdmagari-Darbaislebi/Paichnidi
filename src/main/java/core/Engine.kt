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

    // Window AttributeSetters
    fun setBackgroundColor(color: Color) {
        _windowBackgroundColor = color
    }

    fun setWindowWidth(width: Int) {
        _windowWidth = width
    }

    fun setWindowHeight(height: Int) {
        _windowHeight = height
    }

    fun setWindowTitle(title: String) {
        _windowTitle = title
    }


    // InputListeners
    private var keyboardInputListeners: MutableList<GLFWKeyCallback> = mutableListOf()
    private var cursorMovementInputListeners: MutableList<GLFWCursorPosCallback> = mutableListOf()
    private var mouseButtonInputListeners: MutableList<GLFWMouseButtonCallback> = mutableListOf()
    private var scrollWheelInputListeners: MutableList<GLFWScrollCallback> = mutableListOf()

    // InputListenerSetters
    fun setKeyboardInputListener(keyListenerFunction: KeyboardInputListener) =
        keyboardInputListeners.add(object : GLFWKeyCallback() {
            override operator fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) =
                keyListenerFunction(window, key, scancode, action, mods)

        })


    fun setCursorMovementInputListener(cursorListenerFunction: CursorAndScrollWheelInputListener) =
        cursorMovementInputListeners.add(object : GLFWCursorPosCallback() {
            override operator fun invoke(window: Long, xpos: Double, ypos: Double) =
                cursorListenerFunction(window, xpos, ypos)

        })


    fun setMouseButtonInputListener(mouseListenerFunction: MouseInputListener) =
        mouseButtonInputListeners.add(object : GLFWMouseButtonCallback() {
            override fun invoke(window: Long, button: Int, action: Int, mods: Int) =
                mouseListenerFunction(window, button, action, mods)

        })


    fun setScrollWheelInputListener(scrollWheelListenerFunction: CursorAndScrollWheelInputListener) =
        scrollWheelInputListeners.add(object : GLFWScrollCallback() {
            override fun invoke(window: Long, xoffset: Double, yoffset: Double) =
                scrollWheelListenerFunction(window, xoffset, yoffset)

        })


    fun resetInputListeners() = Window.setAllInputCallBacks(
        keyboardInputListeners,
        cursorMovementInputListeners,
        mouseButtonInputListeners,
        scrollWheelInputListeners
    )

    // Start CEL
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
