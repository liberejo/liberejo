package org.liberejo.game

import com.badlogic.gdx.Screen
import com.kotcrab.vis.ui.VisUI
import ktx.app.KtxGame
import org.liberejo.game.screen.MainMenu

object Liberejo : KtxGame<Screen>() {
	override fun create() {
		// load assets

		VisUI.load()

		// TODO check: is this a headless server?

		addScreen(MainMenu())
		setScreen<MainMenu>()
	}

	override fun dispose() {
		// dispose of assets
	}

	fun loadGame(game: Game) {

	}
}