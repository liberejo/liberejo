package org.liberejo.game.mod

import org.liberejo.api.mod.PackageInfo
import org.liberejo.api.mod.PackageRepository
import org.liberejo.api.mod.declaration.RemotePackageDeclaration

class DefaultPackageRepository : PackageRepository {
	override val installedPackages: List<PackageInfo>
		get() = TODO("not implemented")

	override fun scanLocalPackages() {
		TODO("not implemented")
	}

	override fun installRemotePackage(declaration: RemotePackageDeclaration) {
		TODO("not implemented")
	}

}