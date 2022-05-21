package graphics

import math.Vector
import util.IMeshLoader
import java.io.BufferedReader
import java.io.FileReader

object MeshLoader : IMeshLoader {
    override fun OBJToMesh(path: String) : Mesh{
        val vertices : MutableList<Vector> = mutableListOf()
        val indices : MutableList<Int> = mutableListOf()

        try{
            val reader = BufferedReader(FileReader(path))
            reader.readLines().forEach {
                if (it.startsWith("v ")) {
                    var lineData = it.split(" ")
                    var data = lineData.slice(1 until lineData.size).map { a -> a.toFloat() }
                    vertices.add(Vector(arrayOf(data[0], data[1], data[2])))
                }
                if (it.startsWith("f ")){
                    var data = it.split(" ")
                    data.slice(1 until data.size).forEach { str ->
                        indices.add(str.split("/")[0].toInt()-1)
                    }
                }
            }
            reader.close()
        } catch (e : Exception){
            e.printStackTrace()
        }
        return Mesh(vertices, indices.toIntArray())
    }

}