package graphics.loaders

import graphics.Mesh
import math.Vector
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileReader

object ObjMeshLoader : MeshLoader {

    private const val BASE_PATH = "src/main/resources/models/"

    override fun load(path: String): Mesh {
        val vertices: MutableList<Vector> = mutableListOf()
        val textures: MutableList<Vector> = mutableListOf()
        val indices: MutableList<Int> = mutableListOf()

        try {
            val reader = BufferedReader(FileReader("$BASE_PATH$path"))
            reader.readLines().forEach {
                if (it.startsWith("v ")) {
                    val lineData = it.split(" ")
                    val data = lineData.slice(1 until lineData.size).map { a -> a.toFloat() }
                    vertices.add(Vector(data[0], data[1], data[2]))
                }
                if (it.startsWith("vt ")){
                    val lineData = it.split(" ")
                    val data = lineData.slice(1 until lineData.size).map { a -> a.toFloat() }
                    textures.add(Vector(data[0],data[1]))
                }
                if (it.startsWith("f ")) {
                    val data = it.split(" ")
                    data.slice(1 until data.size).forEach { str ->
                        indices.add(str.split("/")[0].toInt() - 1)
                    }
                }
            }

            reader.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return Mesh(vertices, textures, indices.toIntArray())
    }

}
