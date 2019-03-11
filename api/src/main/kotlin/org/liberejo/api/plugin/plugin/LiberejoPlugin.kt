package org.liberejo.api.plugin.plugin

import org.kodein.di.KodeinAware

abstract class LiberejoPlugin : KodeinAware {
	abstract fun load()
	abstract fun unload()
}