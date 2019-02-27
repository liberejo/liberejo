package org.liberejo.api.engine.world

import org.liberejo.api.engine.tile.Tile

// size 32x32 tiles
abstract class Chunk(val xCoord: Long, val yCoord: Long) {
	abstract val tiles: Array<Array<Tile>>/* = Array(CHUNK_LENGTH) {
		Array<Tile>(CHUNK_LENGTH) { EmptyTile() }
	}*/

	companion object {
		const val CHUNK_LENGTH: Int = 32
	}
}