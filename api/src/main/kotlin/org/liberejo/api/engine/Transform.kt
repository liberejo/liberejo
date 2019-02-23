package org.liberejo.api.engine

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import ktx.math.vec2

class Transform(val position: Vector2 = vec2(),
				var z: Int = 0,
				var rotation: Float = 0.0f,
				var isVisible: Boolean = true) : Component