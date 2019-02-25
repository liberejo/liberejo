package org.liberejo.api.mod

data class ModInfo(val id: String,
				   val name: String,
				   val description: String,
				   val authors: List<String>,
				   val version: String,
				   val mainClass: String)