package org.liberejo.api.plugin

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PluginInfo(val id: String,
					  val name: String,
					  val version: String,
					  val description: String,
					  val authors: List<String>,
					  val mainClass: String)