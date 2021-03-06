package org.liberejo.api.data

import java.nio.file.Path

interface DataManager {
	val dataDir: Path
	val configDir: Path

	val packagesDir: Path

	val configFile: Path

	fun loadConfig(): LiberejoConfig
	fun saveConfig(config: LiberejoConfig)
}