package graphics

import org.lwjgl.opengl.GL20
import util.FileUtils

object ShaderProgramBuilder {

    fun createShaderProgram(vertexShaderPath: String, fragmentShaderPath: String): ShaderProgram {
        val vertexShader = loadShader(vertexShaderPath, GL20.GL_VERTEX_SHADER)
        val fragmentShader = loadShader(fragmentShaderPath, GL20.GL_FRAGMENT_SHADER)

        val program = GL20.glCreateProgram()

        attachShaders(
            program,
            vertexShader,
            fragmentShader
        )

        GL20.glLinkProgram(program)
        GL20.glValidateProgram(program)

        return object : ShaderProgram {


            override fun start() =
                GL20.glUseProgram(program)

            override fun stop() =
                GL20.glUseProgram(0)

            override fun clean() {
                stop()

                GL20.glDetachShader(program, vertexShader.id)
                GL20.glDetachShader(program, fragmentShader.id)

                vertexShader.delete()
                fragmentShader.delete()

                GL20.glDeleteProgram(program)
            }

            override fun bindAttributes() {
                bindAttribute(0, "position")
            }

            fun bindAttribute(attr: Int, name: String) =
                GL20.glBindAttribLocation(program, attr, name)
        }
    }

    private fun loadShader(path: String, type: Int): Shader =
        createShader(type, FileUtils.readFromFile(path))

    private fun createShader(type: Int, source: String): Shader {
        val shader = Shader(type)
        shader.source(source)
        shader.compile()
        return shader
    }

    private fun attachShaders(program: Int, vsh: Shader, fsh: Shader) {
        GL20.glAttachShader(program, vsh.id)
        GL20.glAttachShader(program, fsh.id)

    }

    interface ShaderProgram {
        fun start()
        fun stop()
        fun clean()
        fun bindAttributes()
    }
}