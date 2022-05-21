package math

interface LinAlgObj {

    operator fun plusAssign(scalar: Float)

    operator fun minusAssign(scalar: Float)

    operator fun plus(scalar: Float): LinAlgObj

    operator fun minus(scalar: Float): LinAlgObj

    operator fun inc(): LinAlgObj

    operator fun dec(): LinAlgObj

}
