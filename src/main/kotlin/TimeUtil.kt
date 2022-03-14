class TimeUtil
{
    //Constants for Time util
    val MAX_FPS = 60
    val MAX_UPS = 60

    val fOPTIMAL_TIME = (1000000000 / MAX_FPS).toDouble()
    val uOPTIMAL_TIME = (1000000000 / MAX_UPS).toDouble()

    var uDeltaTime = 0.0
    var fDeltaTime:kotlin.Double = 0.0
    var frames = 0
    var updates:Int = 0
    var timer = System.currentTimeMillis()

    // util function to handle start time conversion
    fun startTime(): Long {
        return System.nanoTime();
    }
    
}


