package unit

import graphics.loaders.ObjMeshLoader
import math.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ComponentTest {

    @Test
    fun getMove() {
        val testObj = Component(
            ObjMeshLoader.load("Grass_Block.obj"),
            Vector(1.1f, 4.1f, 4.9f),
            Vector(0.5f, -0.75f, 0.65f),
            2.3f)

        testObj.move(1.1f, 1.2f, 1.3f)
        Assertions.assertEquals(Vector(2.2f,5.3f,6.2f), testObj.position)
    }

    @Test
    fun getRotate() {
        val testObj = Component(
            ObjMeshLoader.load("Grass_Block.obj"),
            Vector(1.1f, 4.1f, 4.9f),
            Vector(0.5f, -0.75f, 0.65f),
            2.3f)
        val res = testObj.rotation + Vector(1.1f, 1.2f, 1.3f)
        testObj.rotate(1.1f, 1.2f, 1.3f)
        Assertions.assertEquals(res, testObj.rotation)
    }

    @Test
    fun getSetScale() {
        val testObj = Component(
            ObjMeshLoader.load("Grass_Block.obj"),
            Vector(1.1f, 4.1f, 4.9f),
            Vector(0.5f, -0.75f, 0.65f),
            2.3f)

        testObj.setScale(2.5f)
        Assertions.assertEquals(2.5f, testObj.scale)
    }
}