package util

import data.Constants.PLY_HEADER_LENGTH
import java.io.BufferedReader

object FileUtils {
    fun readPlyHeader(bufferedReader : BufferedReader) : String{
        var vertexCount = ""
        for (i in 1..PLY_HEADER_LENGTH) {
            if (i == 4){
                vertexCount = bufferedReader.readLine()
            }
            else {
                bufferedReader.readLine()
            }
        }
        return vertexCount
    }
}