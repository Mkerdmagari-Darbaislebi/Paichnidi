package util

import math.Matrix4f

// Input
typealias KeyboardInputListener = (Long, Int, Int, Int, Int) -> Unit
typealias CursorAndScrollWheelInputListener = (Long, Double, Double) -> Unit
typealias MouseInputListener = (Long, Int, Int, Int) -> Unit

// Transformation
typealias TransformationFunction = (Float, Float, Float) -> Matrix4f