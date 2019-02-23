package org.liberejo.game.network

import com.badlogic.gdx.Gdx
import com.esotericsoftware.kryo.Serializer
import com.esotericsoftware.kryonet.*
import kotlin.reflect.KClass

class NetworkManager(val isClient: Boolean, val isServer: Boolean, val address: String) {
	companion object {
		const val TCP_PORT = 34500
		const val UDP_PORT = 34501
		const val CONNECT_WAIT = 5000
	}

	val server: Server = Server()

	val client: Client = Client()

	init {
		server.apply {
			onConnect {
				Gdx.app.log("Server", "Client ${it.id} connected")
			}
			onDisconnect {
				Gdx.app.log("Server", "Client ${it.id} disconnected")
			}
		}

		client.apply {
			onConnect {
				Gdx.app.log("Client", "Connected to ${it.remoteAddressTCP} (TCP) and ${it.remoteAddressUDP} (UDP)")
			}
			onDisconnect {
				Gdx.app.log("Client", "Disconnected from server")
			}
		}

		if(isServer)
			server.start()

		if(isClient)
			client.start()
	}

	fun <T : Any> register(clazz: KClass<T>) {
		client.kryo.register(clazz.java)
		server.kryo.register(clazz.java)
	}

	fun <T : Any> register(clazz: Class<T>, serializer: Serializer<T>) {
		client.kryo.register(clazz, serializer)
		server.kryo.register(clazz, serializer)
	}

	fun start() {
		if(isServer)
			server.bind(TCP_PORT, UDP_PORT) // TODO handle bind error
		if(isClient)
			client.connect(CONNECT_WAIT, address, TCP_PORT, UDP_PORT)
	}

	fun stop() {
		if(isServer)
			server.stop()
		if(isClient)
			client.stop()
	}
}

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