package graphics

import unit.Component
import core.Engine
import math.Matrix4f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import kotlin.math.tan

object Renderer {


    private val FIELD_OF_VIEW = 70f
    private val NEAR_PLANE = .1f
    private val FAR_PLANE = 1000f
    private lateinit var projectionMatrix: Matrix4f

    fun initProjectionMatrix(shaderProgram: ShaderProgram) {
        setProjectionMatrix()
        shaderProgram.start()
        shaderProgram.loadProjectionMatrix(projectionMatrix)
        shaderProgram.stop()
    }

    fun render(component: Component, shaderProgram: ShaderProgram) {
        val mesh = component.mesh
        GL30.glBindVertexArray(mesh.vaoID)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableVertexAttribArray(1)
        shaderProgram.loadTransformationMatrix(component.transformation)
        GL11.glDrawElements(
            GL11.GL_POLYGON,
            mesh.vertexCount,
            GL11.GL_UNSIGNED_INT,
            0
        )

        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }

    private fun setProjectionMatrix() {
        val aspectRatio =
            Engine.windowWidth.toFloat() / Engine.windowHeight.toFloat()
        val yScale: Float = ((1f / tan(Math.toRadians((FIELD_OF_VIEW / 2).toDouble()))) * aspectRatio).toFloat()
        val xScale = yScale / aspectRatio
        val frustumLength = FAR_PLANE - NEAR_PLANE

        projectionMatrix = Matrix4f()
        projectionMatrix[0][0] = xScale
        projectionMatrix[1][1] = yScale
        projectionMatrix[2][2] = -((FAR_PLANE + NEAR_PLANE) / frustumLength)
        projectionMatrix[2][3] = -1f
        projectionMatrix[3][2] = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength)
        projectionMatrix[3][3] = 0f
    }
}
