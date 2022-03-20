import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;


public class Input extends GLFWKeyCallback{

    public static boolean[] keys = new boolean[65535];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }

    //LUKA, DONT FORGET THIS!

    //inside main class:
    //  private GLFWKeyCallback keyCallback; (attribute)

    //inside main init() method:
    //  glfwSetKeyCallback(window, keyCallback = new Input());

    //inside main update() method:
    //  glfwPollEvents();
    //  if(Input.keys[GLFW_KEY_SPACE])
    //      {whatever happens after pressing SPACE}

    //at the end of main run() method:
    //  keyCallback.release();








}
