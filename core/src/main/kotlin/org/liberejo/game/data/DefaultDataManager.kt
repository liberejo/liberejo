package org.liberejo.game.data

import com.squareup.moshi.Moshi
import net.harawata.appdirs.AppDirsFactory
import org.liberejo.api.data.DataManager
import org.liberejo.api.data.LiberejoConfig
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class DefaultDataManager : DataManager {
	private val moshi: Moshi = Moshi.Builder().build()
	private val configAdapter = moshi.adapter(LiberejoConfig::class.java)

	override val dataDir: Path
	override val configDir: Path

	override val packagesDir: Path

	override val configFile: Path

	init {
		// use AppDirs to locate per-platform directories
		val appDirs = AppDirsFactory.getInstance()

		dataDir = Paths.get(appDirs.getUserDataDir("liberejo", null, null))
		configDir = Paths.get(appDirs.getUserConfigDir("liberejo", null, null))

		packagesDir = dataDir.resolve("packages")

		Files.createDirectories(dataDir)
		Files.createDirectories(configDir)
		Files.createDirectories(packagesDir)

		configFile = configDir.resolve("config.json")
	}

	override fun loadConfig(): LiberejoConfig {
		val raw = Files.readAllBytes(configFile).toString()
		return configAdapter.fromJson(raw) ?: LiberejoConfig()
	}

	override fun saveConfig(config: LiberejoConfig) {
		val json = configAdapter.toJson(config)
		Files.write(configFile, json.toByteArray())
	}
}