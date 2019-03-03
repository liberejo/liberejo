package org.liberejo.game

import com.badlogic.gdx.Screen
import com.kotcrab.vis.ui.VisUI
import ktx.app.KtxGame
import org.liberejo.game.screen.MainMenu

object Liberejo : KtxGame<Screen>() {
	private var gameScreen: GameScreen? = null
	override fun create() {
		// load assets

		VisUI.load()

		// TODO check: is this a headless server? or maybe that occurs during launcher

		addScreen(MainMenu())
		setScreen<MainMenu>()
	}

	override fun dispose() {
		if (gameScreen != null)
			gameScreen!!.dispose()
		// dispose of assets
	}

	fun hostGame() {
		removeScreen<MainMenu>()
		addScreen(GameScreen(isClient = true, isServer = true, address = "localhost"))
		setScreen<GameScreen>()
	}

	fun joinGame(address: String) {
		removeScreen<MainMenu>()
		addScreen(GameScreen(isClient = true, isServer = false, address = address))
	}

	fun loadMainMenu() {
		addScreen(MainMenu())
	}
}