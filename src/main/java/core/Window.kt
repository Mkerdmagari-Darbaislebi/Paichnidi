package core

import input.CursorMovementInput
import input.KeyboardInput
import input.MouseButtonInput
import input.ScrollWheelInput
import org.lwjgl.glfw.*
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil
import java.io.PrintStream
import java.nio.IntBuffer

object Window {

    // WindowId to Which OpenGL Associates its Context
    private var windowID = 0L

    private val defaultKeyCallback: KeyboardInput = KeyboardInput()
    private val defaultCursorMovementCallback: CursorMovementInput = CursorMovementInput()
    private val defaultMouseButtonCallback: MouseButtonInput = MouseButtonInput()
    private val defaultScrollWheelCallback: ScrollWheelInput = ScrollWheelInput()

    // Library Init Checker
    init {
        check(GLFW.glfwInit()) { "Unable to initialize" }
    }

    /**
     * Creates a window and its associated OpenGL or OpenGL ES context. Most of the options controlling how the window and its context should be created are specified with window hint.
     */
    fun createWindow(
        width: Int,
        height: Int,
        title: String
    ) {
        windowID = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (windowID == MemoryUtil.NULL) throw RuntimeException("Failed to create window!")
    }

    /**
     * Swaps the front and back buffers of the specified window when rendering with OpenGL or OpenGL ES. If the swap interval is greater than zero, the GPU driver waits the specified number of screen updates before swapping the buffers.
     */
    fun render() =
        GLFW.glfwSwapBuffers(windowID)


    /**
     * Sets portions of every pixel in a particular buffer to the same value. The value to which each buffer is cleared depends on the setting of the clear value for that buffer
     */
    fun update() = GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

    /**
     * Processes all pending events.
     */
    fun clean() = GLFW.glfwPollEvents()

    /**
     * Closes the window
     */
    fun stop() {
        freeCallbacks()
        destroyWindow()
        terminate()
    }

    fun centerWindow(width: IntBuffer, height: IntBuffer) {
        val videoMode = getVideoMode()
        videoMode?.let { vm ->
            setWindowPos(
                (vm.width() - width[0]) / 2,
                (vm.height() - height[0]) / 2
            )
        }
    }

    fun getWindowSize(
        width: IntBuffer,
        height: IntBuffer,
    ) = GLFW.glfwGetWindowSize(windowID, width, height)

    fun windowShouldClose() = GLFW.glfwWindowShouldClose(windowID)

    /**
     * Makes the OpenGL or OpenGL ES context of the specified window current on the calling thread. A context must only be made current on a single thread at a time and each thread can have only a single current context at a time.
     */
    fun makeContextCurrent() =
        GLFW.glfwMakeContextCurrent(windowID)

    fun enableVSync() = GLFW.glfwSwapInterval(1)

    fun showWindow() = GLFW.glfwShowWindow(windowID)

    /**
     * Sets Default window hints, makes it resizable
     */
    fun configure() {
        setDefaultWindowHints()
        setHiddenAfterCreation()
        setResizable()
    }

    /**
     * Sets input callbacks
     */
    fun setAllInputCallBacks(
        keyboardInputListener: List<GLFWKeyCallback>,
        cursorMovementInputListener: List<GLFWCursorPosCallback>,
        mouseButtonInputListener: List<GLFWMouseButtonCallback>,
        scrollWheelInputListener: List<GLFWScrollCallback>
    ) {
        setKeyCallback(keyboardInputListener)
        setCursorMovementCallback(cursorMovementInputListener)
        setMouseButtonCallback(mouseButtonInputListener)
        setScrollWheelCallback(scrollWheelInputListener)
    }

    private fun setKeyCallback(keyboardInputListener: List<GLFWKeyCallback>) {
        GLFW.glfwSetKeyCallback(windowID) { window: Long, key: Int,
                                            scancode: Int, action: Int,
                                            mods: Int ->
            defaultKeyCallback(
                window, key, scancode,
                action, mods
            )

            keyboardInputListener.forEach {
                it(
                    window, key, scancode,
                    action, mods
                )
            }
        }
    }

    private fun setCursorMovementCallback(cursorMovementInputListener: List<GLFWCursorPosCallback>) =
        GLFW.glfwSetCursorPosCallback(windowID) { window: Long, xpos: Double, ypos: Double ->
            defaultCursorMovementCallback(window, xpos, ypos)

            cursorMovementInputListener.forEach {
                it(window, xpos, ypos)
            }
        }

    private fun setMouseButtonCallback(mouseButtonInputListener: List<GLFWMouseButtonCallback>) =
        GLFW.glfwSetMouseButtonCallback(windowID) { window: Long, button: Int, action: Int, mods: Int ->
            defaultMouseButtonCallback(window, button, action, mods)

            mouseButtonInputListener.forEach {
                it(window, button, action, mods)
            }
        }

    private fun setScrollWheelCallback(scrollWheelInputListener: List<GLFWScrollCallback>) =
        GLFW.glfwSetScrollCallback(windowID) { window: Long, xpos: Double, ypos: Double ->
            defaultScrollWheelCallback(window, xpos, ypos)

            scrollWheelInputListener.forEach {
                it(window, xpos, ypos)
            }
        }

    private fun setDefaultWindowHints() = GLFW.glfwDefaultWindowHints()

    private fun setHiddenAfterCreation() = GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)

    private fun setResizable() = GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)

    private fun getVideoMode() = GLFW.glfwGetVideoMode(getPrimaryMonitor())

    private fun getPrimaryMonitor() = GLFW.glfwGetPrimaryMonitor()

    private fun setWindowPos(x: Int, y: Int) = GLFW.glfwSetWindowPos(windowID, x, y)

    private fun freeCallbacks() = Callbacks.glfwFreeCallbacks(windowID)

    private fun destroyWindow() = GLFW.glfwDestroyWindow(windowID)

    private fun terminate() = GLFW.glfwTerminate()

    fun setupErrorCallback(ps: PrintStream): GLFWErrorCallback =
        GLFWErrorCallback.createPrint(ps).set()
}
