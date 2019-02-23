package org.liberejo.api.engine.player

import com.badlogic.ashley.core.Component
import java.util.*

class Player(val id: UUID) : Component {
	var hasAuthority = false
}