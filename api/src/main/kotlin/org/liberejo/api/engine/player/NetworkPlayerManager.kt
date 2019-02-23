package org.liberejo.api.engine.player

import com.badlogic.ashley.core.Entity
import com.esotericsoftware.kryonet.Connection
import java.util.*

interface NetworkPlayerManager {
//	fun spawnPlayerForConnection(id: UUID, conn: Connection)
	fun despawnPlayer(id: UUID)

	fun playerById(id: UUID): Entity

	fun playerByConnection(connection: Connection): Entity
	fun connectionByPlayer(player: Player): Connection

	fun hasAuthority(conn: Connection, playerId: UUID): Boolean
	fun hasAuthority(conn: Connection, player: Player): Boolean
}