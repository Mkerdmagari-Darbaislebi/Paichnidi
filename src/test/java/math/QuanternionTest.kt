package math

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class QuanternionTest {
    @Test
    fun test_length() {
        val test_quanternion = Quanternion(1.4f, 4.8f, 5.6f, 3.9f)
        val expected_length: Float = sqrt(1.4f * 1.4f + 4.8f * 4.8f + 5.6f * 5.6f + 3.9f * 3.9f)
        Assertions.assertEquals(expected_length, test_quanternion.length())
    }

    @Test
    fun test_dotProduct() {
        val test_quanternion1 = Quanternion(1.4f, 4.8f, 5.6f, 3.9f)
        val test_quanternion2 = Quanternion(4.1f, 8.4f, 6.5f, 9.3f)
        val expected_result = 1.4f * 4.1f + 4.8f * 8.4f + 5.6f * 6.5f + 3.9f * 9.3f
        Assertions.assertEquals(expected_result, test_quanternion1.dotProduct(test_quanternion2))
    }

    @Test
    fun test_norm() {
        val test_quanternion = Quanternion(1.4f, 4.8f, 5.6f, 3.9f)
        val dot_product = test_quanternion.dotProduct(test_quanternion)
        val expected_norm: Float = sqrt(dot_product)
        Assertions.assertEquals(expected_norm, test_quanternion.norm())
    }

    @Test
    fun test_times() {
        val test_quanternion1 = Quanternion(1.4f, 4.8f, 5.6f, 3.9f)
        val test_quanternion2 = Quanternion(4.1f, 8.4f, 6.5f, 9.3f)
        val obtained_quanternion = test_quanternion1.times(test_quanternion2)
        val expected_x = 1.4f * 4.1f - 4.8f * 8.4f - 5.6f * 6.5f - 3.9f * 9.3f
        val expected_y = 1.4f * 4.1f + 4.8f * 8.4f + 5.6f * 6.5f - 3.9f * 9.3f
        val expected_z = 1.4f * 4.1f - 4.8f * 8.4f + 5.6f * 6.5f + 3.9f * 9.3f
        val expected_w = 1.4f * 4.1f + 4.8f * 8.4f - 5.6f * 6.5f + 3.9f * 9.3f
        Assertions.assertEquals(expected_x, obtained_quanternion.getX)
        Assertions.assertEquals(expected_y, obtained_quanternion.getY)
        Assertions.assertEquals(expected_z, obtained_quanternion.getZ)
        Assertions.assertEquals(expected_w, obtained_quanternion.getW)
    }

    @Test
    fun test_conjugate() {
        val test_quanternion = Quanternion(1.4f, 4.8f, 5.6f, 3.9f)
        val obtained_quanternion = test_quanternion.conjugate()
        Assertions.assertEquals(test_quanternion.getX, obtained_quanternion.getX)
        Assertions.assertEquals(-test_quanternion.getY, obtained_quanternion.getY)
        Assertions.assertEquals(-test_quanternion.getZ, obtained_quanternion.getZ)
        Assertions.assertEquals(-test_quanternion.getW, obtained_quanternion.getW)
    }
}