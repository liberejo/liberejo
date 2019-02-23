package org.liberejo.api.engine.render

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

class RendererComponent : Component {
	lateinit var region: TextureRegion
}