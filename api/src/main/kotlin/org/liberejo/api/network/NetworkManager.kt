package org.liberejo.api.network

import com.esotericsoftware.kryo.Serializer
import com.esotericsoftware.kryonet.*

interface NetworkManager {
	/**
	 * Is this network instance a client?
	 */
	val isClient: Boolean

	/**
	 * Is this network instance a server?
	 */
	val isServer: Boolean

	/**
	 * The address to connect to as a client.
	 */
	val address: String

	/**
	 * The [com.esotericsoftware.kryonet.Server] for this instance.
	 */
	val server: Server

	/**
	 * The [com.esotericsoftware.kryonet.Client] for this instance.
	 */
	val client: Client

	@Suppress("unused")
	companion object {
		const val TCP_PORT = 34500
		const val UDP_PORT = 34501

		/**
		 * Timeout in milliseconds for connecting
		 */
		const val CONNECT_WAIT = 5000
	}

	/**
	 * Register a packet type for Kryo serialization
	 */
	fun <T : Any> registerPacketType(clazz: Class<T>)

	/**
	 * Register a packet type for Kryo serialization with a customer serializer
	 */
	fun <T : Any> registerPacketType(clazz: Class<T>, serializer: Serializer<T>)

	/**
	 * Begin network activity. Behavior depends on [isClient] and [isServer]
	 */
	fun start()

	/**
	 * Stop network activity.
	 */
	fun stop()
}

// util

inline fun <reified T : Any> EndPoint.onReceive(crossinline body: (conn: Connection, packet: T) -> Unit) {
	addListener(object : Listener() {
		override fun received(conn: Connection, obj: Any) {
			if (obj is T)
				body.invoke(conn, obj)
		}
	})
}

inline fun EndPoint.onConnect(crossinline body: (conn: Connection) -> Unit) {
	addListener(object : Listener() {
		override fun connected(conn: Connection) {
			body.invoke(conn)
		}
	})
}

inline fun EndPoint.onDisconnect(crossinline body: (conn: Connection) -> Unit) {
	addListener(object : Listener() {
		override fun disconnected(conn: Connection) {
			body.invoke(conn)
		}
	})
}