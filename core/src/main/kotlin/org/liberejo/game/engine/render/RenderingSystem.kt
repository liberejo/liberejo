package org.liberejo.game.engine.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.ashley.allOf
import org.liberejo.api.engine.render.RendererComponent
import org.liberejo.api.engine.renderer
import org.liberejo.api.engine.transform

class RenderingSystem(val sb: SpriteBatch) : SortedIteratingSystem(allOf(RendererComponent::class).get(), ZComparator()) {
	val PIXELS_PER_METER = 64.0f
	val METERS_PER_PIXEL = 1.0f / PIXELS_PER_METER

	val cam: OrthographicCamera

	private var entities = mutableListOf<Entity>()

	init {
		cam = OrthographicCamera(Gdx.graphics.width * METERS_PER_PIXEL, Gdx.graphics.height * METERS_PER_PIXEL)
		cam.position.set(0f, 0f, 0f)
		cam.update()
	}

	override fun update(deltaTime: Float) {
		super.update(deltaTime)

//		entities.sortWith(comparator)

		cam.update()
		sb.projectionMatrix = cam.combined
		sb.enableBlending()
		sb.begin()

		for (entity in entities) {
			val renderer = entity.renderer()
			val transform = entity.transform()

			if (!transform.isVisible)
				continue

			val width = renderer.region.regionWidth.toFloat()
			val height = renderer.region.regionHeight.toFloat()

			val originX = width / 2f
			val originY = height / 2f

			sb.draw(
					renderer.region,
					transform.position.x - originX, transform.position.y - originY,
					0f, 0f,
					width, height,
					1f, 1f,
					transform.rotation
			)
		}

		sb.end()
		entities.clear()
	}

	fun resize(width: Int, height: Int) {
		cam.viewportWidth = width * METERS_PER_PIXEL
		cam.viewportHeight = height * METERS_PER_PIXEL
		cam.update()
	}

	public override fun processEntity(entity: Entity, deltaTime: Float) {
		entities.add(entity)
	}

	// TODO dispose
}