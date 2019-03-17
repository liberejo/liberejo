package org.liberejo.api.plugin

import com.squareup.moshi.JsonClass

/**
 * Information about a plugin, to be serialized as JSON in a plugin.json
 */
@JsonClass(generateAdapter = true)
data class PluginInfo(val id: String,
					  val name: String,
					  val version: String,
					  val description: String,
					  val authors: List<String>,
					  val mainClass: String)