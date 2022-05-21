package graphics.loaders

import graphics.Mesh

interface MeshLoader {
    fun load(path : String) : Mesh
}