package org.liberejo.game.config

import net.harawata.appdirs.AppDirsFactory
import org.liberejo.api.config.ConfigManager
import org.liberejo.api.config.LiberejoConfig
import org.liberejo.game.Liberejo
import java.nio.file.Path
import java.nio.file.Paths

class DefaultConfigManager : ConfigManager {
	private val appDirs = AppDirsFactory.getInstance()

	override val dataDir: Path = Paths.get(appDirs.getUserDataDir("liberejo", null, null))
	override val configDir: Path = Paths.get(appDirs.getUserConfigDir("liberejo", null, null))

	override fun loadConfig(): LiberejoConfig {
		TODO("not implemented")
	}

	override fun saveConfig(config: LiberejoConfig) {
		TODO("not implemented")
	}
}