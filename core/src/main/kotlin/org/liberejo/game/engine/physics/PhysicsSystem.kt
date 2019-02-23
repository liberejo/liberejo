package org.liberejo.game.engine.physics

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.allOf
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.liberejo.api.engine.Transform
import org.liberejo.api.engine.body
import org.liberejo.api.engine.physics.BodyComponent
import org.liberejo.api.engine.transform

class PhysicsSystem(kodein: Kodein) : IteratingSystem(allOf(BodyComponent::class, Transform::class).get()) {
	private val world: World by kodein.instance()

	private val bodiesQueue = mutableListOf<Entity>()

	private var accumulator = 0f

	override fun update(deltaTime: Float) {
		super.update(deltaTime)

		val frameTime = Math.min(deltaTime, 0.25f)
		accumulator += frameTime

		if (accumulator >= MAX_STEP_TIME) {
			world.step(MAX_STEP_TIME, 6, 2)
			accumulator -= MAX_STEP_TIME

			for (entity in bodiesQueue) {
				// get components
				val transform = entity.transform()
				val body = entity.body().body

				// get position from body
				val position = body.position

				// update our transform to match body position
				transform.position.x = position.x
				transform.position.y = position.y
				transform.rotation = body.angle * MathUtils.radiansToDegrees
			}
		}

		bodiesQueue.clear()
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		bodiesQueue.add(entity)
	}

	companion object {
		const val MAX_STEP_TIME = 1 / 45f

		val CATEGORY_PLAYER: Short = 0b0000000000000001
		val CATEGORY_ENVIRONMENT: Short = 0b0000000000000010
	}
}