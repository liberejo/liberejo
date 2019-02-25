package org.liberejo.game.mod

import org.liberejo.api.mod.ModInfo
import org.liberejo.api.mod.ModRepository

class DefaultModRepository : ModRepository {
	override fun detectLocalMods() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun installMod(id: String) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun loadMod(id: String) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun isInstalled(modId: String): Boolean {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override val installedMods: List<ModInfo>
		get() = emptyList()
}