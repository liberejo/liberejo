package org.liberejo.api.engine.tile.material

import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class Material {
	abstract fun render(spriteBatch: SpriteBatch)
}