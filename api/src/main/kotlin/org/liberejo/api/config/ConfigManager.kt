package org.liberejo.api.config

import java.nio.file.Path

interface ConfigManager {
	val dataDir: Path
	val configDir: Path

	val configFile: Path

	fun loadConfig(): LiberejoConfig
	fun saveConfig(config: LiberejoConfig)
}