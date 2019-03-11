package org.liberejo.api.plugin

import org.liberejo.api.plugin.declaration.PluginDeclaration
import org.liberejo.api.plugin.plugin.LiberejoPlugin
import java.nio.file.Path

/**
 * Loads and unloads packages at runtime
 */
interface PluginManager {
	fun loadPlugin(detectedPlugin: DetectedPlugin): LiberejoPlugin
	fun unloadPlugin(pluginDeclaration: PluginDeclaration)

	fun scanLocalPlugins(): List<DetectedPlugin>

	data class DetectedPlugin(val info: PluginInfo, val path: Path)
}