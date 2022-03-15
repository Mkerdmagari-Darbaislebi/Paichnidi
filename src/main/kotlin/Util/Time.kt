package Util

class Time
{

    val MAX_FPS = 60
    val MAX_UPS = 60

    val UPDATE_CAP = (1000000000 / MAX_FPS).toDouble()
    val RENDER_CAP = (1000000000 / MAX_UPS).toDouble()

    var uDeltaTime = 0.0
    var fDeltaTime:kotlin.Double = 0.0
    var frames = 0
    var updates:Int = 0
    var timer = System.currentTimeMillis()


    fun currentTime(): Double {
        return System.nanoTime().toDouble();
    }

}


