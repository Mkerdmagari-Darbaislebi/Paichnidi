package core

import graphics.MeshLoader
import input.KeyboardInput
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil
import java.io.PrintStream
import java.nio.IntBuffer

object Window {
    var window = 0L

    private val inputKeyCallback: KeyboardInput = KeyboardInput()

    init {
        check(GLFW.glfwInit()) { "Unable to initialize" }
    }

    fun createWindow(
        WIDTH: Int,
        HEIGHT: Int,
        TITLE: String
    ) {
        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create window!")
    }

    fun render() {
        MeshLoader.load()
        GLFW.glfwSwapBuffers(window)
    }

    fun update() = GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

    fun clean() = GLFW.glfwPollEvents()

    fun setWindowShouldClose() = GLFW.glfwSetWindowShouldClose(window, true)

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
    ) = GLFW.glfwGetWindowSize(window, width, height)

    fun windowShouldClose() = GLFW.glfwWindowShouldClose(window)

    fun makeContextCurrent() = GLFW.glfwMakeContextCurrent(window)

    fun enableVSync() = GLFW.glfwSwapInterval(1)

    fun showWindow() = GLFW.glfwShowWindow(window)

    fun configure() {
        setDefaultWindowHints()
        setHiddenAfterCreation()
        setResizable()
    }

    fun setKeyCallBack() =
        GLFW.glfwSetKeyCallback(window) { window: Long, key: Int,
                                          scancode: Int, action: Int,
                                          mods: Int ->
            Window.inputKeyCallback(
                window, key, scancode,
                action, mods
            )
        }

    private fun setDefaultWindowHints() = GLFW.glfwDefaultWindowHints()

    private fun setHiddenAfterCreation() = GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)

    private fun setResizable() = GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)

    private fun getVideoMode() = GLFW.glfwGetVideoMode(getPrimaryMonitor())

    private fun getPrimaryMonitor() = GLFW.glfwGetPrimaryMonitor()

    private fun setWindowPos(x: Int, y: Int) = GLFW.glfwSetWindowPos(window, x, y)

    private fun freeCallbacks() = Callbacks.glfwFreeCallbacks(window)

    private fun destroyWindow() = GLFW.glfwDestroyWindow(window)

    private fun terminate() = GLFW.glfwTerminate()

    fun setupErrorCallback(ps: PrintStream): GLFWErrorCallback =
        GLFWErrorCallback.createPrint(ps).set()
}
