package org.liberejo.api.network.packet

import org.liberejo.api.plugin.RemotePluginDeclaration
import org.liberejo.api.util.NoArg

@NoArg
data class CPluginManifestPacket(val remotePlugins: List<RemotePluginDeclaration>)