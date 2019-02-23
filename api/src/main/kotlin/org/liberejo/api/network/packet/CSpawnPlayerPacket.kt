package org.liberejo.api.network.packet

import com.badlogic.gdx.math.Vector2
import org.liberejo.api.util.NoArg
import java.util.*

@NoArg
data class CSpawnPlayerPacket(val playerId: UUID, val position: Vector2, val hasAuthority: Boolean = false)