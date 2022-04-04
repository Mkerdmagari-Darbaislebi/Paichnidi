package graphics

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

class Shader(type: Int) {
    val id: Int

    init {
        id = GL20.glCreateShader(type)
    }

    fun source(source: CharSequence?) = GL20.glShaderSource(id, source)

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

    companion object {
        fun createShader(type: Int, source: CharSequence?): Shader {
            val shader = Shader(type)
            shader.source(source)
            shader.compile()
            return shader
        }

        fun loadShader(type: Int, path: String?): Shader {
            val builder = StringBuilder()
            try {
                FileInputStream(path).use {
                    BufferedReader(InputStreamReader(it)).use { reader ->
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            builder.append(line).append("\n")
                        }
                    }
                }
            } catch (ex: IOException) {
                throw RuntimeException(
                    "Failed to load a shader file!"
                            + System.lineSeparator() + ex.message
                )
            }
            val source: CharSequence = builder.toString()
            return createShader(type, source)
        }
    }
}