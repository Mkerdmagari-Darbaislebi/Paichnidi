package unit

import core.Engine
import math.Vector
import org.lwjgl.glfw.GLFW
import util.KeyboardInputListener

class Camera {
    private var _position: Vector = Vector(0f)
    private var _pitch: Float = 0f
    private var _yaw: Float = 0f
    private var _roll: Float = 0f

    val position get() = _position
    val pitch get() = _pitch
    val yaw get() = _yaw
    val roll get() = _roll

    fun setCameraKeyboardInputListener(
        keyboardListenerFunction: KeyboardInputListener
        = { _, key, _, action, _ ->
            if (action != GLFW.GLFW_RELEASE)
                when (key) {
                    GLFW.GLFW_KEY_W -> _position.z -= .02f
                    GLFW.GLFW_KEY_D -> _position.x += .02f
                    GLFW.GLFW_KEY_A -> _position.x -= .02f
                    GLFW.GLFW_KEY_S -> _position.z += .02f
                }

//            _position.z -= .1f
        }
    ) = Engine.apply {
        setKeyboardInputListener(keyboardListenerFunction)
        resetInputListeners()
    }
}