package graphics

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

object Renderer {

    fun render(mesh: Mesh) {
        GL30.glBindVertexArray(mesh.vaoID)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableVertexAttribArray(1)

        GL11.glDrawElements(
            GL11.GL_TRIANGLES,
            mesh.vertexCount,
            GL11.GL_UNSIGNED_INT,
            0
        )

        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }
}
