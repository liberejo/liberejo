package org.liberejo.game.engine

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.entity
import ktx.box2d.BodyDefinition
import org.liberejo.api.engine.Transform
import org.liberejo.api.engine.physics.BodyComponent
import org.liberejo.api.engine.player.Player
import org.liberejo.api.engine.render.RenderComponent
import java.util.*

fun Engine.spawnPlayer(id: UUID, hasAuthority: Boolean, position: Vector2, world: World): Entity = entity {
	with<RenderComponent> {
		//region = createTexture(com.badlogic.gdx.graphics.Color.BLUE, false, 1, 1)
	}

	val bodyDef = BodyDefinition().apply {
		box {
//			filter.categoryBits = PhysicsSystem.CATEGORY_PLAYER
//			filter.maskBits = PhysicsSystem.CATEGORY_PLAYER.inv()
		}
		type = BodyDef.BodyType.DynamicBody
		fixedRotation = true
		this.position.set(position)
	}

//	entity.add(BodyComponent(bodyDef, world))
	entity.add(Transform())

	val player = Player(id)
//	player.hasAuthority = hasAuthority

	entity.add(player)
}