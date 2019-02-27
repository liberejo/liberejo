package org.liberejo.api.mod

data class PackageInfo(val id: String,
					   val name: String,
					   val version: String,
					   val description: String,
					   val authors: List<String>,
					   val mainClass: String)