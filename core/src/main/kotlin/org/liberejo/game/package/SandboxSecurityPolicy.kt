package org.liberejo.game.`package`

import sun.plugin.security.PluginClassLoader
import java.security.*

class SandboxSecurityPolicy : Policy() {
	override fun getPermissions(domain: ProtectionDomain): PermissionCollection {
		return if (isPlugin(domain)) {
			pluginPermissions()
		} else {
			applicationPermissions()
		}
	}

	private fun isPlugin(domain: ProtectionDomain): Boolean {
		return domain.classLoader is PluginClassLoader
	}

	private fun pluginPermissions(): PermissionCollection {
		return Permissions()
	}

	private fun applicationPermissions(): PermissionCollection {
		val permissions = Permissions()
		permissions.add(AllPermission())
		return permissions
	}
}