package org.liberejo.api.network

import com.esotericsoftware.kryo.Serializer
import com.esotericsoftware.kryonet.*

interface NetworkManager {
	val isClient: Boolean
	val isServer: Boolean
	val address: String

	val server: Server
	val client: Client

	companion object {
		const val TCP_PORT = 34500
		const val UDP_PORT = 34501
		const val CONNECT_WAIT = 5000
	}

	fun <T : Any> register(clazz: Class<T>)

	fun <T : Any> register(clazz: Class<T>, serializer: Serializer<T>)

	fun start()

	fun stop()
}

// Util

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