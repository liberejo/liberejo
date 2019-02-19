package org.liberejo.game

import com.badlogic.gdx.Screen
import com.kotcrab.vis.ui.VisUI
import ktx.app.KtxGame

object Liberejo : KtxGame<Screen>() {
	override fun create() {
		// load assets

		VisUI.load()

		addScreen(MainMenu())
		setScreen<MainMenu>()
	}

	override fun dispose() {
		// dispose of assets
	}
}