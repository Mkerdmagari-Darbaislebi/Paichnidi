package graphics.loaders

import graphics.Shader
import org.lwjgl.opengl.GL20

object ShaderLoader {

    fun load(
        vertexShaderSource: String,
        fragmentShaderSource: String
    ) {
        val program = GL20.glCreateProgram()

        val vertexShader = createShader(GL20.GL_VERTEX_SHADER, vertexShaderSource)
        val fragmentShader = createShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderSource)

        attachShaders(
            program,
            vertexShader.id,
            fragmentShader.id
        )


        linkShaderProgram(program)
        useShaderProgram(program)

        deleteShaders(
            vertexShader,
            fragmentShader
        )
    }

    private fun createShader(type: Int, source: String): Shader {
        val shader = Shader(type)
        shader.source(source)
        shader.compile()
        return shader
    }

    private fun attachShaders(program: Int, vshID: Int, fshID: Int) {
        GL20.glAttachShader(program, vshID)
        GL20.glAttachShader(program, fshID)

    }

    private fun useShaderProgram(program: Int) = GL20.glUseProgram(program)

    private fun linkShaderProgram(program: Int) {
        GL20.glLinkProgram(program)

        if (getProgrami(program, GL20.GL_LINK_STATUS) == 0)
            error(GL20.glGetProgramInfoLog(program, getProgrami(program, GL20.GL_INFO_LOG_LENGTH)))
    }


    private fun deleteShaders(
        vsh: Shader,
        fsh: Shader
    ) {
        vsh.delete()
        fsh.delete()
    }

    private fun getProgrami(program: Int, code: Int): Int =
        GL20.glGetProgrami(program, code)

}
