package math

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class VectorTest {
    @Test
    fun test_plusAssign() {
        val test_vector1 = Vector(1.1f, 4.1f, 4.9f)
        val test_vector2 = Vector(2.3f, 3.1f, 4.1f)
        test_vector1.plusAssign(test_vector2)
        val expected_x = 1.1f + 2.3f
        val expected_y = 4.1f + 3.1f
        val expected_z = 4.9f + 4.1f
        Assertions.assertEquals(expected_x, test_vector1.x)
        Assertions.assertEquals(expected_y, test_vector1.y)
        Assertions.assertEquals(expected_z, test_vector1.z)
    }

    @Test
    fun test_plus() {
        val test_vector = Vector(1.1f, 4.1f, 4.9f)
        val new_vector = test_vector.plus(4.5f)
        val expected_x = 1.1f + 4.5f
        val expected_y = 4.1f + 4.5f
        val expected_z = 4.9f + 4.5f
        Assertions.assertEquals(expected_x, new_vector.x)
        Assertions.assertEquals(expected_y, new_vector.y)
        Assertions.assertEquals(expected_z, new_vector.z)
    }

    @Test
    fun test_minusAssign() {
        val test_vector1 = Vector(1.1f, 4.1f, 4.9f)
        val test_vector2 = Vector(2.3f, 3.1f, 4.1f)
        test_vector1.minusAssign(test_vector2)
        val expected_x = 1.1f - 2.3f
        val expected_y = 4.1f - 3.1f
        val expected_z = 4.9f - 4.1f
        Assertions.assertEquals(expected_x, test_vector1.x)
        Assertions.assertEquals(expected_y, test_vector1.y)
        Assertions.assertEquals(expected_z, test_vector1.z)
    }

    @Test
    fun test_minus() {
        val test_vector = Vector(1.1f, 4.1f, 4.9f)
        val new_vector = test_vector.minus(4.5f)
        val expected_x = 1.1f - 4.5f
        val expected_y = 4.1f - 4.5f
        val expected_z = 4.9f - 4.5f
        Assertions.assertEquals(expected_x, new_vector.x)
        Assertions.assertEquals(expected_y, new_vector.y)
        Assertions.assertEquals(expected_z, new_vector.z)
    }

    @Test
    fun test_inc() {
        val test_vector = Vector(1.1f, 4.1f, 4.9f)
        val new_vector = test_vector.inc()
        val expected_x = 1.1f + 1f
        val expected_y = 4.1f + 1f
        val expected_z = 4.9f + 1f
        Assertions.assertEquals(expected_x, new_vector.x)
        Assertions.assertEquals(expected_y, new_vector.y)
        Assertions.assertEquals(expected_z, new_vector.z)
    }

    @Test
    fun test_dec() {
        val test_vector = Vector(1.1f, 4.1f, 4.9f)
        val new_vector = test_vector.dec()
        val expected_x = 1.1f - 1f
        val expected_y = 4.1f - 1f
        val expected_z = 4.9f - 1f
        Assertions.assertEquals(expected_x, new_vector.x)
        Assertions.assertEquals(expected_y, new_vector.y)
        Assertions.assertEquals(expected_z, new_vector.z)
    }
}