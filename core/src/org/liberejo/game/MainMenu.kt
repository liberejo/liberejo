package org.liberejo.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import ktx.vis.menuItem
import ktx.vis.popupMenu
import ktx.vis.window

class MainMenu : Screen {
	private val uiStage = Stage(ScreenViewport())

	private val inputMultiplexer = InputMultiplexer()

	override fun show() {
		// setup input
		Gdx.input.inputProcessor = inputMultiplexer
		inputMultiplexer.addProcessor(uiStage)

		// basic menu
		val window = window("Liberejo") {
			isMovable = false

			table(true) {
				textButton("Play").onClick {
					println("TODO")
				}
				row()

				textButton("Quit").onClick {
					Gdx.app.exit()
				}
				row()
			}
			centerWindow()
		}
		uiStage.addActor(window)
	}

	override fun hide() {
	}

	override fun render(delta: Float) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		uiStage.act(delta)
		uiStage.draw()
	}

	override fun pause() {
	}

	override fun resume() {
	}

	override fun resize(width: Int, height: Int) {
	}

	override fun dispose() {
		uiStage.dispose()
	}
}