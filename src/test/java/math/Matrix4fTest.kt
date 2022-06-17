package math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Matrix4fTest {

    @Test
    fun test_areInBounds() {
        val testObj = Matrix4f()
        assertEquals(false, testObj.areInBounds(1, 5))
        assertEquals(false, testObj.areInBounds(6, 5))
        assertEquals(false, testObj.areInBounds(7, 2))
        assertEquals(true, testObj.areInBounds(1, 3))
        assertEquals(true, testObj.areInBounds(4, 4))
        assertEquals(true, testObj.areInBounds(0, 0))
    }

    @Test
    fun test_setArray() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], testObj.array[i][j])
    }

    @Test
    fun test_setValue() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)

        nf[3][1] = 7.0F;
        testObj.setValue(7.0F,3,1)
        assertEquals(nf, testObj.array)

        nf[2][2] = 3.2F;
        testObj.setValue(3.2F,2,2)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], testObj.array[i][j])
    }

    @Test
    fun test_getValue() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        assertEquals(6.0f,testObj.getValue(1,3))
        assertEquals(2.0f,testObj.getValue(0,2))
    }

    @Test
    fun test_setRow() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        val r = floatArrayOf(3.0f, 1.5f, 2.1f, 2.3f)
        nf[1] = r
        testObj.setRow(1, r)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], testObj.array[i][j])
        val k = floatArrayOf(2.0f, 2.5f, 1.1f, 3.3f)
        nf[3] = k
        testObj.setRow(3, k)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], testObj.array[i][j])
    }

    @Test
    fun test_setColumn() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        val c = floatArrayOf(3.0f, 1.5f, 2.1f, 2.3f)
        for (i in 0..3) nf[i][1] = c[i]
        testObj.setColumn(1, c)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], testObj.array[i][j])
        val k = floatArrayOf(1.0f, 0.5f, 1.7f, 2.2f)
        for (i in 0..3) nf[i][1] = k[i]
        testObj.setColumn(1, k)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], testObj.array[i][j])
    }

    @Test
    fun test_plusAssign() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        for (i in 0..3) for (j in 0..3) nf[i][j] += 5f
        testObj.plusAssign(5f)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], testObj.array[i][j])
    }

    @Test
    fun test_minusAssign() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        for (i in 0..3) for (j in 0..3) nf[i][j] -= 5f
        testObj.minusAssign(5f)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], testObj.array[i][j])
    }

    @Test
    fun test_plus() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        for (i in 0..3) for (j in 0..3) nf[i][j] += 5f
        val returnObj = testObj.plus(5f)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], returnObj.getValue(i,j))
    }

    @Test
    fun test_minus() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        for (i in 0..3) for (j in 0..3) nf[i][j] -= 5f
        val returnObj = testObj.minus(5f)
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], returnObj.getValue(i,j))
    }

    @Test
    fun test_inc() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        for (i in 0..3) for (j in 0..3) nf[i][j] += 1f
        val returnObj = testObj.inc()
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], returnObj.getValue(i,j))
    }

    @Test
    fun test_dec() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) nf[i][j] = (i * j + j).toFloat()
        testObj.setArray(nf)
        for (i in 0..3) for (j in 0..3) nf[i][j] -= 1f
        val returnObj = testObj.dec()
        for (i in 0..3) for (j in 0..3)
            assertEquals(nf[i][j], returnObj.getValue(i,j))
    }

    @Test
    fun test_dot() {
        val testObj = Matrix4f()
        val nf = Array(4) { FloatArray(4) }
        for (i in 0 until 4) for (j in 0 until 4) nf[i][j] = (i+j).toFloat()
        testObj.setArray(nf)
        val k = Array(4) { FloatArray(4) }
        for (i in 0..3) for (j in 0..3) k[i][j] = 1f
        val returnObj = testObj.dot(Matrix4f(k))
        assertEquals(48.0f, returnObj)
    }
}