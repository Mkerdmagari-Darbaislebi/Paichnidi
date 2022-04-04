package math

import kotlin.math.sqrt

class Quanternion(
    private var x: Float,
    private var y: Float,
    private var z: Float,
    private var w: Float
) : VectorBuilder(arrayOf(x, y, z, w)) {


    override fun normalize() = scaleDown(norm())

    fun scaleDown(scale: Float) {
        if (scale != 1.0f) {
            x /= scale
            y /= scale
            z /= scale
            w /= scale
        }
    }

    fun length() = sqrt(x * x + y * y + z * z + w * w)

    fun norm() = sqrt(dotProduct(this))

    fun dotProduct(other: Quanternion) = x * other.x + y * other.y + z * other.z + w * other.w

    fun conjugate(): Quanternion = Quanternion(x, -y, -z, -w)

    operator fun times(other: Quanternion): Quanternion {
        val otherX = other.x
        val otherY = other.y
        val otherZ = other.z
        val otherW = other.w

        val resultX = x * otherX - y * otherY - z * otherZ - w * otherW
        val resultY = x * otherY + y * otherX + z * otherW - w * otherZ
        val resultZ = x * otherZ - y * otherW + z * otherX + w * otherY
        val resultW = x * otherW + y * otherZ - z * otherY + w * otherX

        return Quanternion(
            resultX, resultY,
            resultZ, resultW
        )
    }

    companion object {

        private val _IDENTITY_QUANTERNION
                by lazy { Quanternion(1.0f, .0f, .0f, .0f) }

        private val _ZERO_QUANTERNION
                by lazy { Quanternion(.0f, .0f, .0f, .0f) }

        private val _I_QUANTERNION
                by lazy { Quanternion(.0f, 1.0f, .0f, .0f) }

        private val _J_QUANTERNION
                by lazy { Quanternion(.0f, .0f, 1.0f, .0f) }

        private val _K_QUANTERNION
                by lazy { Quanternion(.0f, .0f, .0f, 1.0f) }

        @JvmStatic
        val IDENTITY_QUANTERNION: Quanternion
            get() = _IDENTITY_QUANTERNION

        @JvmStatic
        val ZERO_QUANTERNION: Quanternion
            get() = _ZERO_QUANTERNION

        @JvmStatic
        val I_QUANTERNION: Quanternion
            get() = _I_QUANTERNION

        @JvmStatic
        val J_QUANTERNION: Quanternion
            get() = _J_QUANTERNION

        @JvmStatic
        val K_QUANTERNION: Quanternion
            get() = _K_QUANTERNION
    }
}
