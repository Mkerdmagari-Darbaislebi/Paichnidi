package graphics.loaders

import graphics.Mesh

interface MeshLoader {
    /**
     * Loads mesh from a file
     * @param[path] mesh file path
     */
    fun load(path: String): Mesh
}