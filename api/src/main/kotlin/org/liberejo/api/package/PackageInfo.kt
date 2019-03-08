package org.liberejo.api.`package`

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PackageInfo(val id: String,
					   val name: String,
					   val version: String,
					   val description: String,
					   val authors: List<String>,
					   val mainClass: String)