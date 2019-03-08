package org.liberejo.game.`package`

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.liberejo.api.`package`.PackageInfo
import org.liberejo.api.data.DataManager
import org.liberejo.api.`package`.PackageManager
import org.liberejo.api.`package`.declaration.PackageDeclaration
import org.liberejo.api.`package`.plugin.LiberejoPlugin
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.security.Policy

class DefaultPackageManager(override val kodein: Kodein) : PackageManager, KodeinAware {
	override var installedPackages: List<PackageManager.LoadedPackage> = emptyList()
		private set

	private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
	private val packageInfoAdapter = moshi.adapter(PackageInfo::class.java)

	init {
//		Policy.setPolicy(SandboxSecurityPolicy())
//		System.setSecurityManager(SecurityManager())
	}

	private val dataManager: DataManager by instance()

	override fun scanLocalPackages() {
		val packages = mutableListOf<PackageManager.LoadedPackage>()

		Files.walk(dataManager.packagesDir).forEach { path ->
			val classLoader = URLClassLoader(arrayOf(path.toUri().toURL()))
			val stream = classLoader.getResourceAsStream("liberejo-package.json") ?: return@forEach

			val packageInfo = packageInfoAdapter.fromJson(stream.readBytes().toString(Charsets.UTF_8)) ?: return@forEach
			packages.add(PackageManager.LoadedPackage(packageInfo, path))
		}

		installedPackages = packages
	}

	override fun loadPackage(loadedPackage: PackageManager.LoadedPackage) {
	}

	override fun unloadPackage(packageDeclaration: PackageDeclaration) {
	}

	private fun loadPlugin(path: Path, className: String): LiberejoPlugin {
		val url = path.toUri().toURL()
		val classLoader = URLClassLoader(arrayOf(url))
		val clazz = classLoader.loadClass(className)
		return clazz.newInstance() as LiberejoPlugin
	}

}