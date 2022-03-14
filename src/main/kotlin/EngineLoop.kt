import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

class EngineLoop {

    private var window:Long = 0;
    private var runing:Boolean = true;
    private var timeUtil : TimeUtil = TimeUtil()

    private var lastFPS : Long = 0
    private var lastUPS : Long = 0
    private var lastFPSUPSout : Long = 0

    private var FPS : Long = 0
    private var UPS : Long = 0

    private fun run() {
        println("Hello LWJGL " + Version.getVersion() + "!")
        init()
        GL.createCapabilities()
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        loop()


    }

    fun stop(){
        runing = false
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

     fun start(){
        runing = true
         run()
    }

    private fun render(){
        GLFW.glfwSwapBuffers(window)
    }

    private fun update(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    private fun clean(){
        GLFW.glfwPollEvents()
    }

    private fun init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        check(GLFW.glfwInit()) { "Unable to initialize GLFW" }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints() // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE) // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE) // the window will be resizable

        // Create the window
        window = GLFW.glfwCreateWindow(300, 300, "Hello World!", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        GLFW.glfwSetKeyCallback(
            window
        ) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(
                window,
                true
            )
        }
        MemoryStack.stackPush().also { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            GLFW.glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

            // Center the window
            GLFW.glfwSetWindowPos(
                window,
                (vidmode!!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window)
        // Enable v-sync
        GLFW.glfwSwapInterval(1)

        // Make the window visible
        GLFW.glfwShowWindow(window)
    }

    private fun loop(){
        while (runing){
            if (GLFW.glfwWindowShouldClose(window)) stop()
            if (System.nanoTime() - lastFPSUPSout > 1000000000) {
                println("FPS: " + FPS.toDouble())
                println("UPS: " + UPS.toDouble())
                FPS = 0
                UPS = 0
                lastFPSUPSout = timeUtil.currentTime()
            }
            if ((timeUtil.currentTime() - lastUPS) > timeUtil.fOPTIMAL_TIME){
                lastUPS = timeUtil.currentTime()
                update()
                UPS++
            }

            if ((timeUtil.currentTime() - lastFPS) > timeUtil.uOPTIMAL_TIME){
                lastFPS = timeUtil.currentTime()
                render()
                FPS++
            }
            clean()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            EngineLoop().start()
        }
    }

}