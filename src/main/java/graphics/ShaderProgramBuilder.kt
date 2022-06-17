package graphics

import math.Matrix4f
import math.Vector
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20
import unit.Camera
import util.FileUtils
import util.Transformations
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
            private var locationTransformationMatrix = -1
            private var locationProjectionMatrix = -1
            private var locationViewMatrix = -1
            private var locationTextureSampler = -1

            init {
                getAllUniformLocations()
            }

            override fun bindAttributes() {
                bindAttribute(0, "position")
                bindAttribute(1, "textureCoord")
            }

            override fun getAllUniformLocations() {
                locationTransformationMatrix =
                    getUniformLocation("transformationMatrix")
                locationProjectionMatrix =
                    getUniformLocation("projectionMatrix")
                locationViewMatrix =
                    getUniformLocation("viewMatrix")
                locationTextureSampler =
                    getUniformLocation("textureSampler")
            }

            override fun loadTransformationMatrix(matrix: Matrix4f) =
                loadMatrix(locationTransformationMatrix, matrix)


            override fun loadProjectionMatrix(matrix: Matrix4f) =
                loadMatrix(locationProjectionMatrix, matrix)

            override fun loadViewMatrix(camera: Camera) =
                loadMatrix(locationViewMatrix, Transformations.cameraViewMatrix(camera))

            override fun loadTextureSampler(value: Int) {
                loadInt(locationTextureSampler, 1)
            }
        }
    }

    private fun loadFloat(location: Int, value: Float) =
        GL20.glUniform1f(location, value)

    fun loadVertex(location: Int, vector: Vector) =
        GL20.glUniform3f(
            location,
            vector.x,
            vector.y,
            vector.z
        )

    fun loadBoolean(location: Int, value: Boolean) =
        GL20.glUniform1f(
            location,
            (if (value) 1f else 0f)
        )

    fun loadInt(location: Int, value: Int){
        GL20.glUniform1i(location , value)
    }

    fun loadMatrix(location: Int, matrix: Matrix4f) {
        matrixBuffer.put(matrix.flatten)
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
