package core

import data.Constants
import graphics.Mesh
import graphics.loaders.MeshLoader


class SceneBuilder(
    private val vertexShader: String,
    private val fragmentShader: String,
    private val meshes: List<Mesh>
) {
    init {
        setShaders()
        setMeshes()
    }

    private fun setShaders() {
        Constants.VERTEX_SHADER = vertexShader
        Constants.FRAGMENT_SHADER = fragmentShader
    }

    private fun setMeshes() {
        MeshLoader.meshes.clear()
        MeshLoader.meshes.addAll(meshes)
    }

    fun build() = Engine.start()
}