package org.liberejo.api.plugin

import org.kodein.di.KodeinAware

abstract class LiberejoPlugin : KodeinAware {
	abstract fun load()
	abstract fun unload()
}