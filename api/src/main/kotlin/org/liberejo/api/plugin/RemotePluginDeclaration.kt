package org.liberejo.api.plugin

// used for plugin manifests sent by a server
data class RemotePluginDeclaration(val id: String, val version: String, val url: String)