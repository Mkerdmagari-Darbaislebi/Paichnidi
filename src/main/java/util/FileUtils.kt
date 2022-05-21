package util

import java.io.BufferedReader
import java.io.FileReader

object FileUtils {

    private const val BASE_PATH = "src/main/resources/shaders/"

    fun readFromFile(path: String): String {
        val result = java.lang.StringBuilder()
        try {
            val reader =
                BufferedReader(FileReader("$BASE_PATH$path"))

            reader.readLines().forEach { result.append(it).append("\n") }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result.toString()
    }

}
