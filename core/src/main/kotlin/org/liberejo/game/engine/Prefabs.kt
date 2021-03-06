package org.liberejo.game.engine

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.entity
import ktx.box2d.body
import org.liberejo.api.engine.Transform
import org.liberejo.api.engine.physics.BodyComponent
import org.liberejo.api.engine.player.Player
import org.liberejo.api.engine.render.RendererComponent
import org.liberejo.game.engine.physics.PhysicsSystem
import org.liberejo.game.engine.render.createRect
import java.util.*
import kotlin.experimental.inv

fun Engine.spawnPlayer(id: UUID, hasAuthority: Boolean, position: Vector2, world: World): Entity = entity {
	with<RendererComponent> {
		region = createRect(Color.BLUE, 1, 1)
	}

	val body = world.body(BodyDef.BodyType.DynamicBody) {
		box {
			filter.categoryBits = PhysicsSystem.CATEGORY_PLAYER
			filter.maskBits = PhysicsSystem.CATEGORY_PLAYER.inv()
		}
		type = BodyDef.BodyType.DynamicBody
		fixedRotation = true
		this.position.set(position)
	}

	entity.add(BodyComponent(body))
	entity.add(Transform())

	val player = Player(id)
	player.hasAuthority = hasAuthority

	entity.add(player)
}