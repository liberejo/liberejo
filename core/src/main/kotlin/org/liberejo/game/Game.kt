package org.liberejo.game

import com.badlogic.gdx.utils.Disposable
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.liberejo.game.network.NetworkManager

class Game(isClient: Boolean, isServer: Boolean, address: String = "localhost") : Disposable {
	// TODO SRP?

	private val kodein = Kodein {
		bind<NetworkManager>() with singleton { networkManager }
	}

	private val networkManager = NetworkManager(isClient, isServer, address)

	override fun dispose() {

	}
}