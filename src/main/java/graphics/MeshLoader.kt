package graphics

object MeshLoader {
    val meshes : MutableList<Mesh> = mutableListOf()

    fun load() = meshes.forEach{ mesh -> mesh.draw() }
}