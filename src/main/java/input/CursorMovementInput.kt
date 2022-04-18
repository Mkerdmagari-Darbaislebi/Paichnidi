package input

import org.lwjgl.glfw.GLFWCursorPosCallback

class CursorMovementInput : GLFWCursorPosCallback(){
    override fun invoke(window: Long, xpos: Double, ypos: Double) {
        print("cursor position is $xpos    $ypos\n")
    }
}
