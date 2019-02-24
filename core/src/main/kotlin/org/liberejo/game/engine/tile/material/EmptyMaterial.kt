package org.liberejo.game.engine.tile.material

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.liberejo.api.engine.tile.material.Material

class EmptyMaterial : Material() {
	override fun render(spriteBatch: SpriteBatch) {}
}