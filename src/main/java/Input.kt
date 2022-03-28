import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFWKeyCallback


class Input : GLFWKeyCallback() {

    val keys: Array<Boolean> = Array(65535) { false }

    override operator fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        keys[key] = action != GLFW_RELEASE
        if (keys[key])
            println(key)
        handleEscape(key, action)
    }

    private fun handleEscape(key: Int, action: Int) {
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            Window.setWindowShouldClose()
    }

}
