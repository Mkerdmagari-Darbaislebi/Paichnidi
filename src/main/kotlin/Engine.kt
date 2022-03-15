import Util.Time
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

class Engine {

    private var window:Long = 0;
    private var running:Boolean = true;
    private var time : Time = Time()

    private var lastUPS : Double = 0.0
    private var lastFPSUPSout : Double = 0.0

    private var UPS : Double = 0.0

    private var EngineThread : Thread = Thread(Runnable { run() })

    private fun run() {
        println("Hello LWJGL " + Version.getVersion() + "!")
        init()
        GL.createCapabilities()
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        loop()
    }

    fun stop(){
        running = false
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

     fun start(){
         running = true
         EngineThread.run()
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
        while (running){
            var passedTime:Double = time.currentTime() - lastUPS
            var renderLock : Boolean = false
            if (GLFW.glfwWindowShouldClose(window)) stop()
            if (System.nanoTime() - lastFPSUPSout > 1000000000) {
                println("FPS: " + UPS)
                UPS = 0.0
                lastFPSUPSout = time.currentTime()
            }

            while ((passedTime) >= time.UPDATE_CAP){
                update()
                renderLock = true
                UPS++
                passedTime -= time.UPDATE_CAP
            }
            lastUPS = time.currentTime() - passedTime
            if (renderLock) render()
            clean()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Engine().start()
        }
    }

}