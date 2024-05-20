package net.williserver.tiers

import org.bukkit.plugin.java.JavaPlugin

class TiersPlugin : JavaPlugin() {
    private val handler = LogHandler(super.getLogger())

    override fun onEnable() {
        handler.info("Enabled")
    }

    override fun onDisable() {
        handler.info("Disabled")
    }
}