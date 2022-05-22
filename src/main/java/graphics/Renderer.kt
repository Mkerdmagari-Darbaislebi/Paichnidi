package graphics

import core.Component
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

object Renderer {

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
}
