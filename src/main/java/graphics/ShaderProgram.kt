package graphics

import math.Matrix4f
import org.lwjgl.opengl.GL20

abstract class ShaderProgram(
    private val program: Int,
    private val vsh: Shader,
    private val fsh: Shader
) {

    abstract fun bindAttributes()

    abstract fun getAllUniformLocations()

    fun start() =
        GL20.glUseProgram(program)

    fun stop() =
        GL20.glUseProgram(0)

    fun clean() {
        stop()

        GL20.glDetachShader(program, vsh.id)
        GL20.glDetachShader(program, fsh.id)

        vsh.delete()
        fsh.delete()

        GL20.glDeleteProgram(program)
    }

    fun bindAttribute(attr: Int, name: String) =
        GL20.glBindAttribLocation(program, attr, name)

    fun getUniformLocation(name: String) =
        GL20.glGetUniformLocation(program, name)

    abstract fun loadTransformationMatrix(matrix: Matrix4f)
}
