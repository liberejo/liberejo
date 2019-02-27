package org.liberejo.api.mod

import org.liberejo.api.mod.declaration.PackageDeclaration
import java.nio.file.Path

abstract class PackageManager {
	abstract fun loadPackage(packageDeclaration: PackageDeclaration): LoadedPackage

	data class LoadedPackage(val info: PackageInfo, val assetDir: Path, val mainClass: Class<*>)
}