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

    // ID Creation
    fun createWindow(
        width: Int,
        height: Int,
        title: String
    ) {
        windowID = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (windowID == MemoryUtil.NULL) throw RuntimeException("Failed to create window!")
    }

    fun render() =
        GLFW.glfwSwapBuffers(windowID)


    fun update() = GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

    fun clean() = GLFW.glfwPollEvents()

//    fun setWindowShouldClose() = GLFW.glfwSetWindowShouldClose(window, true)

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

    fun makeContextCurrent() =
        GLFW.glfwMakeContextCurrent(windowID)

    fun enableVSync() = GLFW.glfwSwapInterval(1)

    fun showWindow() = GLFW.glfwShowWindow(windowID)

    fun configure() {
        setDefaultWindowHints()
        setHiddenAfterCreation()
        setResizable()
    }

    fun setAllInputCallBacks(
        keyboardInputListener: GLFWKeyCallback?,
        cursorMovementInputListener: GLFWCursorPosCallback?,
        mouseButtonInputListener: GLFWMouseButtonCallback?,
        scrollWheelInputListener: GLFWScrollCallback?
    ) {
        setKeyCallback(keyboardInputListener)
        setCursorMovementCallback(cursorMovementInputListener)
        setMouseButtonCallback(mouseButtonInputListener)
        setScrollWheelCallback(scrollWheelInputListener)
    }

    private fun setKeyCallback(keyboardInputListener: GLFWKeyCallback?) {
        GLFW.glfwSetKeyCallback(windowID) { window: Long, key: Int,
                                            scancode: Int, action: Int,
                                            mods: Int ->
            defaultKeyCallback(
                window, key, scancode,
                action, mods
            )

            keyboardInputListener?.let {
                it(
                    window, key, scancode,
                    action, mods
                )
            }
        }
    }

    private fun setCursorMovementCallback(cursorMovementInputListener: GLFWCursorPosCallback?) =
        GLFW.glfwSetCursorPosCallback(windowID) { window: Long, xpos: Double, ypos: Double ->
            defaultCursorMovementCallback(window, xpos, ypos)

            cursorMovementInputListener?.let {
                it(window, xpos, ypos)
            }
        }

    private fun setMouseButtonCallback(mouseButtonInputListener: GLFWMouseButtonCallback?) =
        GLFW.glfwSetMouseButtonCallback(windowID) { window: Long, button: Int, action: Int, mods: Int ->
            defaultMouseButtonCallback(window, button, action, mods)

            mouseButtonInputListener?.let {
                it(window, button, action, mods)
            }
        }

    private fun setScrollWheelCallback(scrollWheelInputListener: GLFWScrollCallback?) =
        GLFW.glfwSetScrollCallback(windowID) { window: Long, xpos: Double, ypos: Double ->
            defaultScrollWheelCallback(window, xpos, ypos)

            scrollWheelInputListener?.let {
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
