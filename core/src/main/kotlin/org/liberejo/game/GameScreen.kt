package org.liberejo.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import de.javakaffee.kryoserializers.UUIDSerializer
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.box2d.BodyDefinition
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.liberejo.api.engine.player.NetworkPlayerManager
import org.liberejo.api.network.NetworkManager
import org.liberejo.api.network.packet.CDespawnPlayerPacket
import org.liberejo.api.network.packet.CSpawnPlayerPacket
import org.liberejo.game.engine.physics.PhysicsSystem
import org.liberejo.game.engine.player.DefaultNetworkPlayerManager
import org.liberejo.game.engine.render.RenderingSystem
import org.liberejo.game.network.DefaultNetworkManager
import java.util.*
import kotlin.experimental.inv

class GameScreen(isClient: Boolean, isServer: Boolean, address: String = "localhost") : KtxScreen {
	private val kodein = Kodein {
		bind<PooledEngine>() with singleton { engine }
		bind<NetworkManager>() with singleton { networkManager }
		bind<NetworkPlayerManager>() with singleton { networkPlayerManager }
		bind<InputMultiplexer>() with singleton { inputMultiplexer}
		bind<Stage>() with singleton { uiStage }
	}

	// engine
	private val engine = PooledEngine()

	// network
	private val networkManager: NetworkManager = DefaultNetworkManager(isClient, isServer, address)

	// player
	private val networkPlayerManager: NetworkPlayerManager = DefaultNetworkPlayerManager(kodein)

	// rendering
	private val renderingSystem: RenderingSystem = RenderingSystem(SpriteBatch())

	// input
	private val inputMultiplexer = InputMultiplexer()

	// ui
	private val uiStage = Stage(ScreenViewport())

	override fun show() {
		initCamera()
		initInput()
		initPhysics()
		initWorld()
		initNetwork()
		initMods()
	}

	private fun initCamera() {
		Gdx.app.log("Game", "Initializing rendering")

		renderingSystem.sb.projectionMatrix = renderingSystem.cam.combined // TODO necessary?
	}

	private fun initInput() {
		Gdx.app.log("Game", "Initializing input")

		Gdx.input.inputProcessor = inputMultiplexer

		inputMultiplexer.addProcessor(uiStage)
	}

	private fun initPhysics() {
		Gdx.app.log("Game", "Initializing physics")
	}

	private fun initWorld() {
		Gdx.app.log("Game", "Initializing world")

		engine.addSystem(renderingSystem)

		engine.addSystem(PhysicsSystem(kodein))
	}

	private fun initNetwork() {
		Gdx.app.log("Game", "Initializing network")

		networkManager.apply {
			// misc
			register(Vector2::class.java)
			register(UUID::class.java, UUIDSerializer())

			// packets

			register(CSpawnPlayerPacket::class.java)
			register(CDespawnPlayerPacket::class.java)

			start()
		}
	}

	private fun initMods() {

	}

	override fun dispose() {
		networkManager.stop()
	}
}