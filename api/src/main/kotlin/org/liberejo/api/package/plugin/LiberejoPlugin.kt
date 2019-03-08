package org.liberejo.api.`package`.plugin

import org.kodein.di.KodeinAware

abstract class LiberejoPlugin : KodeinAware {
	abstract fun onLoad()
	abstract fun onUnload()
}