package graphics

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20

class Shader(type: Int) {
    val id: Int

    init {
        id = GL20.glCreateShader(type)
    }

    fun source(source: String) = GL20.glShaderSource(id, source)

    fun compile() {
        GL20.glCompileShader(id)
        checkStatus()
    }

    private fun checkStatus() {
        val status = GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS)
        if (status != GL11.GL_TRUE) {
            throw RuntimeException(GL20.glGetShaderInfoLog(id))
        }
    }

    fun delete() = GL20.glDeleteShader(id)

}
