package graphics

import data.Constants
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class Renderer {
    fun render(mesh: Mesh) {
        GL30.glBindVertexArray(mesh.vaoID)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableClientState(GL11.GL_COLOR_ARRAY)
        GL20.glColorPointer(Constants.COLOR_ARRAY_CARDINALITY, GL11.GL_FLOAT, 0, mesh.colorBuffer)
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.vertexCount)
        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }
}
