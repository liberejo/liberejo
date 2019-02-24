package org.liberejo.api.engine.tile.material

interface MaterialManager {
	fun loadMaterial(id: String, material: Material)

	fun byId(id: String): Material?
}