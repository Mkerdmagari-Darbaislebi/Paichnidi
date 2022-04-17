package util

import java.io.BufferedReader
import java.io.FileReader

object FileUtils {
    fun readFromFile(path: String): String {
        val result = java.lang.StringBuilder()
        try {
            val reader = BufferedReader(FileReader(path))
            reader.readLines().forEach { result.append(it).append("\n") }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result.toString()
    }
}