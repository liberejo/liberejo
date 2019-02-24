package org.liberejo.game.engine.tile.material

import org.liberejo.api.engine.tile.material.Material
import org.liberejo.api.engine.tile.material.MaterialManager

class DefaultMaterialManager : MaterialManager {
	private val materials: MutableMap<String, Material> = mutableMapOf()

	override fun loadMaterial(id: String, material: Material) {
		materials[id] = material
	}

	override fun byId(id: String): Material? = materials[id]
}