package org.liberejo.game.mod

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.liberejo.api.config.DataManager
import org.liberejo.api.mod.PackageManager
import org.liberejo.api.mod.declaration.PackageDeclaration

class DefaultPackageManager(override val kodein: Kodein) : PackageManager, KodeinAware {
	private val dataManager: DataManager by instance()

	override fun loadPackage(packageDeclaration: PackageDeclaration): PackageManager.LoadedPackage {
		TODO("not implemented")
	}

	override fun unloadPackage(packageDeclaration: PackageDeclaration) {
		TODO("not implemented")
	}
}