package graphics.loaders

import graphics.Mesh
import org.lwjgl.opengl.GL11

object MeshLoader {

    val meshes: MutableList<Mesh> = mutableListOf()

    fun load(
        meshes: List<Mesh> = MeshLoader.meshes,
        drawMode: Int = GL11.GL_TRIANGLES
    ) = meshes.forEach { mesh -> mesh.draw(drawMode) }
}
