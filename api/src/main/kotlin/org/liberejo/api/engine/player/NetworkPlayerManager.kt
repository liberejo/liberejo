package org.liberejo.api.engine.player

import com.badlogic.ashley.core.Entity
import com.esotericsoftware.kryonet.Connection
import java.util.*

interface NetworkPlayerManager {
//	fun spawnPlayerForConnection(id: UUID, conn: Connection)
	fun despawnPlayer(id: UUID)

	/**
	 * Retrieves an entity with the [org.liberejo.api.engine.player.Player] component by a player ID
	 */
	fun playerById(id: UUID): Entity

	/**
	 * Retrieves an entity with the [org.liberejo.api.engine.player.Player] component by a connection
	 */
	fun playerByConnection(connection: Connection): Entity

	/**
	 * Retrieves a [Connection] from a player
	 *
	 * @see[playerByConnection]
	 */
	fun connectionByPlayer(player: Player): Connection

	/**
	 * Does [conn] have authority over player with id [playerId]?
	 */
	fun hasAuthority(conn: Connection, playerId: UUID): Boolean

	/**
	 * Does [conn] have authority over [player]?
	 */
	fun hasAuthority(conn: Connection, player: Player): Boolean
}