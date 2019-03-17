package org.liberejo.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import de.javakaffee.kryoserializers.UUIDSerializer
import ktx.app.KtxScreen
import ktx.math.vec2
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.liberejo.api.assets.AssetManager
import org.liberejo.api.data.DataManager
import org.liberejo.api.engine.player.NetworkPlayerManager
import org.liberejo.api.plugin.PluginManager
import org.liberejo.api.network.NetworkManager
import org.liberejo.api.network.packet.*
import org.liberejo.game.assets.DefaultAssetManager
import org.liberejo.game.data.DefaultDataManager
import org.liberejo.game.engine.physics.PhysicsSystem
import org.liberejo.game.engine.player.DefaultNetworkPlayerManager
import org.liberejo.game.engine.render.RenderingSystem
import org.liberejo.game.plugin.DefaultPluginManager
import org.liberejo.game.network.DefaultNetworkManager
import java.util.*

class GameScreen(isClient: Boolean, isServer: Boolean, address: String = "localhost") : KtxScreen {
	// all remotePlugins also have access to this
	private val kodein = Kodein {
		bind<PooledEngine>() with singleton { engine }
		bind<NetworkManager>() with singleton { networkManager }
		bind<NetworkPlayerManager>() with singleton { networkPlayerManager }
		bind<InputMultiplexer>() with singleton { inputMultiplexer }
		bind<Stage>() with singleton { uiStage }
		bind<AssetManager>() with singleton { assetManager }
		bind<PluginManager>() with singleton { pluginManager }
		bind<DataManager>() with singleton { dataManager }
		bind<World>() with singleton { World(vec2(0f, 0f), true) } // TODO world manager
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

	// packages
	private val pluginManager: PluginManager = DefaultPluginManager(kodein)

	private val dataManager: DataManager = DefaultDataManager()

	// assets
	private val assetManager: AssetManager = DefaultAssetManager(kodein)

	override fun show() {
		initCamera()
		initInput()
		initPhysics()
		initWorld()
		initNetwork()
		initPlugins()
	}

	private fun initCamera() {
		Gdx.app.log("Game", "Initializing rendering")

		renderingSystem.spriteBatch.projectionMatrix = renderingSystem.cam.combined // TODO necessary?
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
			registerType(Vector2::class.java)
			registerType(UUID::class.java, UUIDSerializer())

			// TODO what's the best way of registering packets?

			start()
		}
	}

	private fun initPlugins() {
		Gdx.app.log("Game", "Loading remotePlugins")

		val plugins = pluginManager.scanLocalPlugins()

		// TODO contextually load remotePlugins
		plugins.forEach {
			pluginManager.loadPlugin(it)
		}
	}

	override fun render(delta: Float) {
		// clear
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		// update engine
		engine.update(delta)

		// render ui
		uiStage.act(delta)
		uiStage.draw()
	}

	override fun resize(width: Int, height: Int) {
		uiStage.viewport.update(width, height, true)
		renderingSystem.resize(width, height)
	}

	override fun dispose() {
		uiStage.dispose()
		networkManager.stop()
		engine.clearPools()
	}
}