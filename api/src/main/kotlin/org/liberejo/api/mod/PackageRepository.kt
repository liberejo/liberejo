package org.liberejo.api.mod

/**
 * Manages the installation of packages
 */
interface PackageRepository {
	val installedPackages: List<PackageInfo>

	// detect currently installed mods
	fun scanLocalPackages()

	// install mod from repository
	// TODO
//	fun installRemotePackage(declaration: RemotePackageDeclaration)
}