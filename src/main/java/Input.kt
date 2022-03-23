import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFWKeyCallback;


class Input : GLFWKeyCallback() {

    val keys: Array<Boolean> = Array(65535) { false }

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        keys[key] = action != GLFW_RELEASE
    }

}
