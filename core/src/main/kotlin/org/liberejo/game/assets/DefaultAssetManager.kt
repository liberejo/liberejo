package org.liberejo.game.assets

import com.badlogic.gdx.graphics.Texture
import ktx.assets.getValue
import ktx.assets.load
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.liberejo.api.assets.AssetManager

class DefaultAssetManager(override val kodein: Kodein) : AssetManager, KodeinAware {
	private val assetManager = com.badlogic.gdx.assets.AssetManager()

	val logo by assetManager.load<Texture>("logo.png")
}