package org.liberejo.game.plugin

import com.badlogic.gdx.Gdx
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.liberejo.api.data.DataManager
import org.liberejo.api.network.NetworkManager
import org.liberejo.api.network.onReceive
import org.liberejo.api.network.packet.CPluginManifestPacket
import org.liberejo.api.plugin.PluginInfo
import org.liberejo.api.plugin.PluginManager
import org.liberejo.api.plugin.RemotePluginDeclaration
import org.liberejo.api.plugin.LiberejoPlugin
import java.net.URLClassLoader
import java.nio.file.Files

class DefaultPluginManager(override val kodein: Kodein) : PluginManager, KodeinAware {
	private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
	private val packageInfoAdapter = moshi.adapter(PluginInfo::class.java)

	private val networkManager: NetworkManager by instance()

	companion object {
		const val pluginInfoFilename = "plugin.json"
	}

	init {
		// TODO security
//		Policy.setPolicy(SandboxSecurityPolicy())
//		System.setSecurityManager(SecurityManager())

		// handle plugin manifest packet
		networkManager.registerType(CPluginManifestPacket::class.java)

		networkManager.client.onReceive<CPluginManifestPacket> { conn, packet ->
			packet.remotePlugins.forEach {
				// download, install, and load this plugin
			}
		}
	}

	private val dataManager: DataManager by instance()

	override fun scanLocalPlugins(): List<PluginManager.DetectedPlugin> {
		val packages = mutableListOf<PluginManager.DetectedPlugin>()

		// load packages installed on system (also loads dev packages from resources folders)
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

		val plugin = clazz.getConstructor(Kodein::class.java).newInstance(kodein) as LiberejoPlugin

		Gdx.app.log("Plugins", "Loading plugin \"${detectedPlugin.info.name}\"")

		plugin.load()

		return plugin
	}

	override fun unloadPlugin(remotePluginDeclaration: RemotePluginDeclaration) {
	}
}