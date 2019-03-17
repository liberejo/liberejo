package org.liberejo.game.engine.world

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.liberejo.api.engine.world.WorldManager

class DefaultWorldManager(override val kodein: Kodein) : WorldManager, KodeinAware {

}