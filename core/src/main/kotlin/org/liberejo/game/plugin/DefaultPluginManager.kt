package org.liberejo.game.plugin

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.liberejo.api.plugin.PluginInfo
import org.liberejo.api.data.DataManager
import org.liberejo.api.plugin.PluginManager
import org.liberejo.api.plugin.declaration.PluginDeclaration
import org.liberejo.api.plugin.plugin.LiberejoPlugin
import java.net.URLClassLoader
import java.nio.file.Files

class DefaultPluginManager(override val kodein: Kodein) : PluginManager, KodeinAware {
	private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
	private val packageInfoAdapter = moshi.adapter(PluginInfo::class.java)

	init {
//		Policy.setPolicy(SandboxSecurityPolicy())
//		System.setSecurityManager(SecurityManager())
	}

	private val dataManager: DataManager by instance()

	override fun scanLocalPlugins(): List<PluginManager.DetectedPlugin> {
		val packages = mutableListOf<PluginManager.DetectedPlugin>()

		Files.walk(dataManager.packagesDir).forEach { path ->
			val classLoader = URLClassLoader(arrayOf(path.toUri().toURL()))
			val stream = classLoader.getResourceAsStream("plugin.json") ?: return@forEach

			val packageInfo = packageInfoAdapter.fromJson(stream.readBytes().toString(Charsets.UTF_8)) ?: return@forEach
			packages.add(PluginManager.DetectedPlugin(packageInfo, path))
		}

		return packages
	}

	override fun loadPlugin(detectedPlugin: PluginManager.DetectedPlugin): LiberejoPlugin {
		val url = detectedPlugin.path.toUri().toURL()
		val mainClass = detectedPlugin.info.mainClass

		val classLoader = URLClassLoader(arrayOf(url))
		val clazz = classLoader.loadClass(mainClass)

		val plugin = clazz.newInstance() as LiberejoPlugin
		plugin.load()

		return plugin
	}

	override fun unloadPlugin(pluginDeclaration: PluginDeclaration) {
	}
}