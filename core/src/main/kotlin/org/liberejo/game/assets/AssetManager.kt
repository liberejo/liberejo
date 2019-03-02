package org.liberejo.game.assets

import com.badlogic.gdx.graphics.Texture
import ktx.assets.getValue
import ktx.assets.load
import org.kodein.di.Kodein

class AssetManager(val kodein: Kodein) {
	private val assetManager = com.badlogic.gdx.assets.AssetManager()

	val logo by assetManager.load<Texture>("logo.png")
}