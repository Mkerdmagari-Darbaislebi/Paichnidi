package graphics

open class Color(
    var red: Float,
    var green: Float,
    var blue: Float,
    var alpha: Float
) {

    constructor(red: Int, green: Int, blue: Int, alpha: Float) : this(
        red.toFloat() / 255,
        green.toFloat() / 255,
        blue.toFloat() / 255,
        alpha
    )

    fun flatten() = listOf(
        red,
        green,
        blue,
        alpha
    )
}
