package org.liberejo.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.liberejo.game.Liberejo

object DesktopLauncher {
	@JvmStatic
	fun main(arg: Array<String>) {
		val config = LwjglApplicationConfiguration()
		config.apply {
			width = 1280
			height = 720
			title = "liberejo"
		}
		LwjglApplication(Liberejo(), config)
	}
}
