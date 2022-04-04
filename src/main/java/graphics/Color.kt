package graphics

open class Color(
    var red: Float,
    var green: Float,
    var blue: Float,
    var alpha : Float
) {
    fun flatten() = listOf(
        red,
        green,
        blue,
        alpha
    )
}
