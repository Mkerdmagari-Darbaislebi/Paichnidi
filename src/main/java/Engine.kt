import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.lwjgl.Version
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import util.Time

const val WIDTH: Int = 300
const val HEIGHT: Int = 300
const val TITLE: String = "Title"

class Engine {

    private var mainWindow = Window(WIDTH, HEIGHT, TITLE)
    private var running: Boolean = true

    private var lastUPS: Double = 0.0
    private var lastFPSUPSout: Double = 0.0

    private var UPS: Double = 0.0

//    private var EngineThread: Thread = Thread { run() }

    private fun run() {
        println("Hello LWJGL ${Version.getVersion()}!")
        init()
        GL.createCapabilities()
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        loop()
    }

    fun start() {
        running = true
        run()
    }

    private fun init() {
        // Setup an error callback
        Window.setupErrorCallback(System.err)

        // Configure GLFW
        mainWindow.configure()

        // Create the window
        mainWindow.createWindow()

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
            mainWindow.getWindowSize(pWidth, pHeight)

            // Center the window
            mainWindow.centerWindow(pWidth, pHeight)
        }

        // Make the OpenGL context current
        mainWindow.makeContextCurrent()

        // Enable v-sync
        mainWindow.enableVSync()

        // Make the window visible
        mainWindow.showWindow()
    }

    private fun loop() {
        while (running) {
            var passedTime: Double = Time.currentTime() - lastUPS
            var renderLock: Boolean = false
            if (mainWindow.windowShouldClose())
                mainWindow.stop()

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
