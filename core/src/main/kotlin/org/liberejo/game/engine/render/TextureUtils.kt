package org.liberejo.game.engine.render

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

fun createRect(color: Color, w: Int, h: Int): TextureRegion {
	val pixmap = Pixmap(w, h, Pixmap.Format.RGBA8888)
	pixmap.setColor(color)
	pixmap.fill()
	val tex = TextureRegion(Texture(pixmap))
	pixmap.dispose()
	return tex
}