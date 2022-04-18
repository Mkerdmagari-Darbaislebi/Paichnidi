package input

import org.lwjgl.glfw.GLFWScrollCallback

class ScrollwheelInput : GLFWScrollCallback() {
    override fun invoke(window: Long, xoffset: Double, yoffset: Double) {
        println("scrolled up vertically by $yoffset, scrolled horizontally by $xoffset")
    }
}
