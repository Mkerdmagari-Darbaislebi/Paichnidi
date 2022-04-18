import input.CursorMovementInput
import input.KeyboardInput
import input.MouseButtonInput
import input.ScrollwheelInput
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil
import java.io.PrintStream
import java.nio.IntBuffer

class Window
    (
    private val width: Int,
    private val height: Int,
    private val title: String
) {

    private var window = 0L

    private val inputKeyCallback: KeyboardInput = KeyboardInput()
    private val cursorMovementInput: CursorMovementInput = CursorMovementInput()
    private val mouseButtonInput: MouseButtonInput = MouseButtonInput()
    private val scrollwheelInput: ScrollwheelInput = ScrollwheelInput()

    init {
        check(GLFW.glfwInit()) { "Unable to initialize" }
    }

    fun createWindow() {
        window = GLFW.glfwCreateWindow(500, 500, "title", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create window!")
    }

    fun render() =
        GLFW.glfwSwapBuffers(window)


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

    fun makeContextCurrent() =
        GLFW.glfwMakeContextCurrent(window)

    fun enableVSync() = GLFW.glfwSwapInterval(1)

    fun showWindow() = GLFW.glfwShowWindow(window)

    fun configure() {
        setDefaultWindowHints()
        setHiddenAfterCreation()
        setResizable()
    }

    fun setAllInputCallBacks() {
        setKeyCallBack()
        setCursoMovementCallBack()
        setMouseButtonCallBack()
        setScrollWheelCallBack()
    }

    private fun setKeyCallBack() =
        GLFW.glfwSetKeyCallback(window) { window: Long, key: Int,
                                          scancode: Int, action: Int,
                                          mods: Int ->
            inputKeyCallback(
                window, key, scancode,
                action, mods
            )
        }

    private fun setCursoMovementCallBack() =
        GLFW.glfwSetCursorPosCallback(window) { window: Long, xpos: Double, ypos: Double ->
            cursorMovementInput(window, xpos, ypos)
        }

    private fun setMouseButtonCallBack() =
        GLFW.glfwSetMouseButtonCallback(window) { window: Long, button: Int, action: Int, mods: Int ->
            mouseButtonInput(window, button, action, mods)
        }

    private fun setScrollWheelCallBack() =
        GLFW.glfwSetScrollCallback(window) { window: Long, xpos: Double, ypos: Double ->
            scrollwheelInput(window, xpos, ypos)
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
