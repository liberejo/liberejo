package org.liberejo.launcher

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.liberejo.game.Liberejo

object DesktopLauncher {
	@JvmStatic fun main(args: Array<String>) {
		startDesktopApp()
	}

	fun startDesktopApp() {
		val config = LwjglApplicationConfiguration()
		config.apply {
			width = 1280
			height = 720
		}
		LwjglApplication(Liberejo, config)
	}
}
