package org.liberejo.api.mod.plugin

import org.kodein.di.KodeinAware

abstract class LiberejoPlugin : KodeinAware {
	abstract fun onLoad()
	abstract fun onUnload()
}