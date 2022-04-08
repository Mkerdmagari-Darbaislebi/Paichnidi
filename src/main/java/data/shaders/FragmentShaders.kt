package data.shaders

object FragmentShaders {
    val SIMPLE_FRAGMENT_SHADER =
        fun(r: Float, g: Float, b: Float, a: Float) =
            "#version 330 core\n" +
                    "out vec4 FragColor;\n" +
                    "\n" +
                    "void main()\n" +
                    "{\n" +
                    "    FragColor = vec4($r, $g, $b, $a);\n" +
                    "}\n"

}