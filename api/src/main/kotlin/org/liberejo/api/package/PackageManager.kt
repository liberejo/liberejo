package org.liberejo.api.`package`

import org.liberejo.api.`package`.declaration.PackageDeclaration
import java.nio.file.Path

/**
 * Loads and unloads packages at runtime
 */
interface PackageManager {
	fun loadPackage(loadedPackage: LoadedPackage)
	fun unloadPackage(packageDeclaration: PackageDeclaration)

	val installedPackages: List<LoadedPackage>

	fun scanLocalPackages()

	data class LoadedPackage(val info: PackageInfo, val path: Path)
}