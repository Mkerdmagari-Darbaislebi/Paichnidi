package graphics

data class Color8Bit(
    var red8Bit: Int,
    var green8Bit: Int,
    var blue8Bit: Int
) : Color(
    red8Bit.toFloat() / 255,
    green8Bit.toFloat() / 255,
    blue8Bit.toFloat() / 255
)