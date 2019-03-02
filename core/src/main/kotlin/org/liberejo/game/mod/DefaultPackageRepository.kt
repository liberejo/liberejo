package org.liberejo.game.mod

import org.liberejo.api.mod.PackageInfo
import org.liberejo.api.mod.PackageRepository

class DefaultPackageRepository : PackageRepository {
	override val installedPackages: List<PackageInfo> = emptyList()

	override fun scanLocalPackages() {
		// TODO scan
	}
}