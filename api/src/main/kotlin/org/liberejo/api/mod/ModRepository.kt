package org.liberejo.api.mod

interface ModRepository {
	val installedMods: List<ModInfo>

	// detect currently installed mods
	fun detectLocalMods()

	// install mod from repository
	fun installMod(id: String)

	// load all mods into game
	fun loadMod(id: String)

	fun isInstalled(modId: String): Boolean
}