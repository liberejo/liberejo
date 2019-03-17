package org.liberejo.api.network.packet

import org.liberejo.api.util.NoArg
import java.util.*

@NoArg
data class CRevokePlayerAuthorityPacket(val playerId: UUID)