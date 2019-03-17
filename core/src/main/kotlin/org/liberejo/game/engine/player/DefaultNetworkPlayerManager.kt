package org.liberejo.game.engine.player

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.esotericsoftware.kryonet.Connection
import com.google.common.collect.HashBiMap
import ktx.ashley.allOf
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.liberejo.api.engine.Transform
import org.liberejo.api.engine.player
import org.liberejo.api.engine.player.NetworkPlayerManager
import org.liberejo.api.engine.player.Player
import org.liberejo.api.engine.transform
import org.liberejo.api.network.NetworkManager
import org.liberejo.api.network.onConnect
import org.liberejo.api.network.onDisconnect
import org.liberejo.api.network.onReceive
import org.liberejo.api.network.packet.CDespawnPlayerPacket
import org.liberejo.api.network.packet.CSpawnPlayerPacket
import org.liberejo.game.engine.spawnPlayer
import java.util.*

class DefaultNetworkPlayerManager(override val kodein: Kodein) : NetworkPlayerManager, KodeinAware {
	private val networkManager: NetworkManager by instance()
	private val engine: PooledEngine by instance()
	private val world: World by instance()

	private val connections = HashBiMap.create<UUID, Int>()

	private val entities = engine.getEntitiesFor(allOf(Player::class, Transform::class).get())

	init {
		networkManager.apply {
			client.onDisconnect {
				// TODO
				//Liberejo.returnToMainMenu()
			}

			server.onConnect { conn ->
				Gdx.app.postRunnable {
					// spawn the player
					val id = UUID.randomUUID()
					engine.spawnPlayer(id, conn.id == networkManager.client.id, Vector2.Zero, world).player()

					// assign this player to the connection
					connections[id] = conn.id

					// tell all others about this player
					server.sendToAllExceptTCP(
							conn.id,
							CSpawnPlayerPacket(id, Vector2.Zero)
					)

					// inform the new client of all current players (including themself)
					entities.forEach {
						val player = it.player()
						val transform = it.transform()

						val hasAuthority = player.id == id

						conn.sendTCP(
								CSpawnPlayerPacket(
										player.id,
										transform.position,
										hasAuthority
								)
						)
					}
				}

			}
			server.onDisconnect { conn ->
				// retrieve the player
				val player = playerByConnection(conn).player()

				// unassign this player from the connection
				connections.inverse().remove(conn.id)

				// notify all other players
				server.sendToAllExceptTCP(conn.id, CDespawnPlayerPacket(player.id))
			}

			client.onReceive<CSpawnPlayerPacket> { _, packet ->
				// if we're the host, we already know
				if (isServer) return@onReceive

				// otherwise, spawn the player
				Gdx.app.postRunnable {
					engine.spawnPlayer(packet.playerId, packet.hasAuthority, packet.position, world)
				}
			}

			client.onReceive<CDespawnPlayerPacket> { _, packet ->
				Gdx.app.postRunnable {
					despawnPlayer(packet.playerId)
				}
			}
		}
	}

	override fun despawnPlayer(id: UUID) {
		engine.removeEntity(playerById(id))
	}

	// TODO null safety and whatnot

	// only on server

	private val notServerException = "Cannot access connections when not the server"

	override fun playerById(id: UUID): Entity {
		return entities.first {
			it.player().id == id
		}
	}

	override fun playerByConnection(connection: Connection): Entity {
		if (!networkManager.isServer)
			throw IllegalStateException(notServerException)

		return connections.inverse()[connection.id]?.let { playerById(it) }!!
	}

	override fun connectionByPlayer(player: Player): Connection {
		if (!networkManager.isServer)
			throw IllegalStateException(notServerException)

		val connId = connections[player.id]
		return networkManager.server.connections.firstOrNull { it.id == connId }!!
	}

	override fun hasAuthority(conn: Connection, playerId: UUID): Boolean {
		if (!networkManager.isServer)
			throw IllegalStateException(notServerException)

		return connections[playerId] == conn.id
	}

	override fun hasAuthority(conn: Connection, player: Player): Boolean = hasAuthority(conn, player.id)
}