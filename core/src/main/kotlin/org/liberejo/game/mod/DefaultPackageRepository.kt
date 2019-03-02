package org.liberejo.game.mod

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.liberejo.api.config.DataManager
import org.liberejo.api.mod.PackageInfo
import org.liberejo.api.mod.PackageRepository

class DefaultPackageRepository(override val kodein: Kodein) : PackageRepository, KodeinAware {
	private val dataManager: DataManager by instance()

	override val installedPackages: List<PackageInfo> = emptyList()

	override fun scanLocalPackages() {
		// TODO scan
	}
}