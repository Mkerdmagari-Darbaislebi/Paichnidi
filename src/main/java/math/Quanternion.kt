package math

class Quanternion(
    private var x: Double,
    private var y: Double,
    private var z: Double,
    private var w: Double
) {


    fun normalize() = scaleDown(norm())

    fun scaleDown(scale: Double): Quanternion {
        if (scale != 1.0) {
            x /= scale
            y /= scale
            z /= scale
            w /= scale
        }
        return this
    }

    fun length() = Math.sqrt(x * x + y * y + z * z + w * w)

    fun norm() = Math.sqrt(dotProduct(this))

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
                by lazy { Quanternion(1.0, .0, .0, .0) }

        private val _ZERO_QUANTERNION
                by lazy { Quanternion(.0, .0, .0, .0) }

        private val _I_QUANTERNION
                by lazy { Quanternion(.0, 1.0, .0, .0) }

        private val _J_QUANTERNION
                by lazy { Quanternion(.0, .0, 1.0, .0) }

        private val _K_QUANTERNION
                by lazy { Quanternion(.0, .0, .0, 1.0) }

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
