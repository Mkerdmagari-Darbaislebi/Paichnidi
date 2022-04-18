package input

import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFWMouseButtonCallback

class MouseButtonInput : GLFWMouseButtonCallback() {
    override fun invoke(window: Long, button: Int, action: Int, mods: Int) {
        if(action != GLFW_RELEASE)
            println("button number $button clicked")
    }

}
