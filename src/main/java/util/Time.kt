package util

object Time {

    val MAX_FPS = 60
    val MAX_UPS = 60

    val UPDATE_CAP by lazy { (1_000_000_000 / MAX_FPS).toDouble() }
    val RENDER_CAP by lazy { (1_000_000_000 / MAX_UPS).toDouble() }

    var uDeltaTime = 0.0
    var fDeltaTime = 0.0
    var frames = 0
    var updates: Int = 0
    var timer = System.currentTimeMillis()

    fun currentTime(): Double {
        return System.nanoTime().toDouble()
    }
}


