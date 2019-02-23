package org.liberejo.game.engine.render

import com.badlogic.ashley.core.Entity
import org.liberejo.api.engine.transform
import java.util.*

class ZComparator : Comparator<Entity> {
	override fun compare(entityA: Entity, entityB: Entity): Int {
		val az = entityA.transform().z
		val bz = entityB.transform().z

		return az.compareTo(bz)
	}
}