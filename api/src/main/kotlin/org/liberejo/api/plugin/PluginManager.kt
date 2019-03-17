package org.liberejo.api.plugin

import java.nio.file.Path

/**
 * Loads and unloads packages at runtime
 */
interface PluginManager {
	/**
	 * Load a detected plugin into the game
	 */
	fun loadPlugin(detectedPlugin: DetectedPlugin): LiberejoPlugin
	fun unloadPlugin(pluginDeclaration: PluginDeclaration)

	/**
	 * Scan the system for locally installed plugins
	 */
	fun scanLocalPlugins(): List<DetectedPlugin>

	/**
	 * Represents a plugin detected in [scanLocalPlugins]
	 */
	data class DetectedPlugin(val info: PluginInfo, val path: Path)
}