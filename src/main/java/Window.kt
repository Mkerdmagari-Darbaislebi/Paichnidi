import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil

class Window(
    private val WIDTH: Int,
    private val HEIGHT: Int,
    private val TITLE: String
) {
    var window = 0L

    fun createWindow() {
        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, MemoryUtil.NULL, MemoryUtil.NULL)
    }

    fun render() = GLFW.glfwSwapBuffers(window)

    fun update() = GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

    fun clean() = GLFW.glfwPollEvents()
}