package org.liberejo.api.mod

import org.liberejo.api.mod.declaration.PackageDeclaration
import java.nio.file.Path

/**
 * Loads and unloads packages at runtime
 */
interface PackageManager {
	fun loadPackage(packageDeclaration: PackageDeclaration): LoadedPackage
	fun unloadPackage(packageDeclaration: PackageDeclaration)

	data class LoadedPackage(val info: PackageInfo, val assetsDir: Path, val mainClass: Class<*>)
}