package org.liberejo.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.getValue
import ktx.assets.load
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class AssetManager(val kodein: Kodein) {
//	private val assetManager: AssetManager by kodein.instance()
	private val assetManager = AssetManager()

	val logo by assetManager.load<Texture>("logo.png")
}