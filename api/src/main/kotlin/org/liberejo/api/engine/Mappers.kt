package org.liberejo.api.engine

import com.badlogic.ashley.core.Entity
import ktx.ashley.mapperFor
import org.liberejo.api.engine.physics.BodyComponent
import org.liberejo.api.engine.player.Player

object Mappers {
	val player = mapperFor<Player>()
	val transform = mapperFor<Transform>()
	val body = mapperFor<BodyComponent>()
}

fun Entity.player() = Mappers.player[this]
fun Entity.transform() = Mappers.transform[this]
fun Entity.body() = Mappers.body[this]