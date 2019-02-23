package org.liberejo.game

import com.badlogic.gdx.utils.Disposable
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.liberejo.api.network.NetworkManager
import org.liberejo.game.network.DefaultNetworkManager

class Game(isClient: Boolean, isServer: Boolean, address: String = "localhost") : Disposable {
	private val kodein = Kodein {
		bind<NetworkManager>() with singleton { DefaultNetworkManager(isClient, isServer, address) }
	}

	override fun dispose() {

	}
}