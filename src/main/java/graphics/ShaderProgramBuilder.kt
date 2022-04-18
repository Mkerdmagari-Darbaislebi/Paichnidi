package graphics

import math.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20
import util.FileUtils
import java.nio.FloatBuffer

object ShaderProgramBuilder {

    private val matrixBuffer: FloatBuffer =
        BufferUtils.createFloatBuffer(16)

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

        return object : ShaderProgram(
            program,
            vertexShader,
            fragmentShader
        ) {
            private var location_transfomationMatrix = -1

            init {
                getAllUniformLocations()
            }

            override fun bindAttributes() {
                bindAttribute(0, "position")
            }

            override fun getAllUniformLocations() {
                location_transfomationMatrix =
                    getUniformLocation("transformationMatrix")
            }

            override fun loadTransformationMatrix(matrix: Matrix4f) {
                loadMatrix(location_transfomationMatrix, matrix)
            }
        }
    }

    private fun loadFloat(location: Int, value: Float) =
        GL20.glUniform1f(location, value)

    fun loadVertex(location: Int, vertex: Vertex) =
        GL20.glUniform3f(
            location,
            vertex.x,
            vertex.y,
            vertex.z
        )

    fun loadBoolean(location: Int, value: Boolean) =
        GL20.glUniform1f(
            location,
            (if (value) 1f else 0f)
        )

    fun loadMatrix(location: Int, matrix: Matrix4f) {
        matrixBuffer.put(matrix.flatten().toFloatArray())
        matrixBuffer.flip()
        GL20.glUniformMatrix4fv(location, false, matrixBuffer)
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
}
