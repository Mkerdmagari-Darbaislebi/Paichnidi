import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.Time
import kotlinx.coroutines.runBlocking
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.SharedLibrary.Default

const val WIDTH: Int = 300
const val HEIGHT: Int = 300
const val TITLE: String = "Title"

class Engine {

    private var mainWindow = Window(WIDTH, HEIGHT, TITLE)
    private var running: Boolean = true

    private var lastUPS: Double = 0.0
    private var lastFPSUPSout: Double = 0.0

    private var UPS: Double = 0.0

//    private var EngineThread: Thread = Thread(Runnable { run() })

    private fun run() {
        println("Hello LWJGL " + Version.getVersion() + "!")
        init()
        GL.createCapabilities()
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        loop()
    }

    fun stop() {
        running = false
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(mainWindow.window)
        GLFW.glfwDestroyWindow(mainWindow.window)

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

    fun start() {
        running = true
        run()
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
        mainWindow.createWindow()
        if (mainWindow.window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        GLFW.glfwSetKeyCallback(
            mainWindow.window
        ) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(
                window,
                true
            )
        }
        MemoryStack.stackPush().also { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            GLFW.glfwGetWindowSize(mainWindow.window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

            // Center the window
            GLFW.glfwSetWindowPos(
                mainWindow.window,
                (vidmode!!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(mainWindow.window)
        // Enable v-sync
        GLFW.glfwSwapInterval(1)

        // Make the window visible
        GLFW.glfwShowWindow(mainWindow.window)
    }

    private fun loop() {
        while (running) {
            var passedTime: Double = Time.currentTime() - lastUPS
            var renderLock: Boolean = false
            if (GLFW.glfwWindowShouldClose(mainWindow.window)) stop()
            if (System.nanoTime() - lastFPSUPSout > 1000000000) {
                println("FPS: $UPS")
                UPS = 0.0
                lastFPSUPSout = Time.currentTime()
            }

            while ((passedTime) >= Time.UPDATE_CAP) {
                mainWindow.update()
                renderLock = true
                UPS++
                passedTime -= Time.UPDATE_CAP
            }
            lastUPS = Time.currentTime() - passedTime
            if (renderLock)
                mainWindow.render()
            mainWindow.clean()
        }
    }
}

fun main(): Unit = runBlocking {
    launch(Dispatchers.Default) {
        println(Thread.currentThread().name)
        Engine().start()
    }
}