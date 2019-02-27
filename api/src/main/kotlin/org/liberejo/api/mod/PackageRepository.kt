package org.liberejo.api.mod

import org.liberejo.api.mod.declaration.RemotePackageDeclaration

interface PackageRepository {
	val installedPackages: List<PackageInfo>

	// detect currently installed mods
	fun scanLocalPackages()

	// install mod from repository
	fun installRemotePackage(declaration: RemotePackageDeclaration)
}