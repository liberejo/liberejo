package org.liberejo.api.plugin

import org.kodein.di.KodeinAware

abstract class LiberejoPlugin : KodeinAware {
	/**
	 * Called when this plugin is being loaded in a game instance. This may be called several times in the application lifetime.
	 */
	abstract fun load()

	/**
	 * Called when the game instance is being unloaded.
	 */
	abstract fun unload()
}