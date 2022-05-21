package util

import graphics.Mesh

interface IMeshLoader {
    fun OBJToMesh(filePath : String) : Mesh
}