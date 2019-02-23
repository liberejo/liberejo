package org.liberejo.game.network

import com.badlogic.gdx.Gdx
import com.esotericsoftware.kryo.Serializer
import com.esotericsoftware.kryonet.*
import org.liberejo.api.network.NetworkManager
import org.liberejo.api.network.onConnect
import org.liberejo.api.network.onDisconnect

class DefaultNetworkManager(override val isClient: Boolean, override val isServer: Boolean, override val address: String) : NetworkManager {
	companion object {
		const val TCP_PORT = 34500
		const val UDP_PORT = 34501
		const val CONNECT_WAIT = 5000
	}

	override val server: Server = Server()
	override val client: Client = Client()

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

	override fun <T : Any> register(clazz: Class<T>) {
		client.kryo.register(clazz)
		server.kryo.register(clazz)
	}

	override fun <T : Any> register(clazz: Class<T>, serializer: Serializer<T>) {
		client.kryo.register(clazz, serializer)
		server.kryo.register(clazz, serializer)
	}

	override fun start() {
		if(isServer)
			server.bind(TCP_PORT, UDP_PORT) // TODO handle bind error
		if(isClient)
			client.connect(CONNECT_WAIT, address, TCP_PORT, UDP_PORT)
	}

	override fun stop() {
		if(isServer)
			server.stop()
		if(isClient)
			client.stop()
	}
}