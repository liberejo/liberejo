package org.liberejo.api.engine

import com.badlogic.ashley.core.Entity
import ktx.ashley.mapperFor
import org.liberejo.api.engine.physics.BodyComponent
import org.liberejo.api.engine.player.Player
import org.liberejo.api.engine.render.RendererComponent

object Mappers {
	val player = mapperFor<Player>()
	val transform = mapperFor<Transform>()
	val body = mapperFor<BodyComponent>()
	val renderer = mapperFor<RendererComponent>()
}

fun Entity.player() = Mappers.player[this]
fun Entity.transform() = Mappers.transform[this]
fun Entity.body() = Mappers.body[this]
fun Entity.renderer() = Mappers.renderer[this]