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
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.vertexCount, GL11.GL_UNSIGNED_INT, 0)
        GL20.glDisableVertexAttribArray(0)
        GL20.glDisableClientState(GL11.GL_COLOR_ARRAY)
        GL30.glBindVertexArray(0)
    }
}
