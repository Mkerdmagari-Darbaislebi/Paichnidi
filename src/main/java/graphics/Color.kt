package graphics

data class Color(
    var red: Float,
    var green: Float,
    var blue: Float,
    var alpha: Float = 1f
) {

    constructor(red: Int, green: Int, blue: Int, alpha: Float = 1f) : this(
        red.toFloat() / 255,
        green.toFloat() / 255,
        blue.toFloat() / 255,
        alpha
    )
}
