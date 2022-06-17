package unit

import core.Engine
import graphics.Color
import graphics.loaders.ObjMeshLoader
import math.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


internal class ComponentTest {

    @BeforeEach
    fun init() {
        Engine.apply {
            setWindowWidth(1200)
            setWindowHeight(900)
            setWindowTitle("Game Engine")
            setBackgroundColor(Color(100, 100, 120))
            init()
        }
    }

    @Test
    fun getMove() {
        val testObj = Component(
            ObjMeshLoader.load("Grass_Block.obj"),
            Vector(1.1f, 4.1f, 4.9f),
            Vector(0.5f, -0.75f, 0.65f),
            2.3f
        )

        testObj.move(1.1f, 1.2f, 1.3f)
        assertEquals(2.2f, testObj.position.x)
        assertEquals(5.3f, testObj.position.y)
        assertEquals(6.2f, testObj.position.z)
    }

    @Test
    fun getRotate() {
        val testObj = Component(
            ObjMeshLoader.load("Grass_Block.obj"),
            Vector(1.1f, 4.1f, 4.9f),
            Vector(0.5f, -0.75f, 0.65f),
            2.3f
        )
        val res = testObj.rotation + Vector(1.1f, 1.2f, 1.3f)
        testObj.rotate(1.1f, 1.2f, 1.3f)
        assertEquals(res.x, testObj.rotation.x)
        assertEquals(res.y, testObj.rotation.y)
        assertEquals(res.z, testObj.rotation.z)
    }

    @Test
    fun getSetScale() {
        val testObj = Component(
            ObjMeshLoader.load("Grass_Block.obj"),
            Vector(1.1f, 4.1f, 4.9f),
            Vector(0.5f, -0.75f, 0.65f),
            2.3f
        )

        testObj.setScale(2.5f)
        Assertions.assertEquals(2.5f, testObj.scale)
    }
}