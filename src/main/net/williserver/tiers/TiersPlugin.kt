package net.williserver.tiers

import org.bukkit.plugin.java.JavaPlugin

class TiersPlugin : JavaPlugin() {
    private val handler = LogHandler(super.getLogger())
    // Default path for this baby.
    private val path = "$dataFolder/tiers.json"
    // Model initialized after config loaded.
    private lateinit var model: TierModel;

    override fun onEnable() {
        // Save config with defaults if not present.
        saveDefaultConfig()

        // Load config file.
        val tierInterval = config.getInt("tierInterval")
        val tierSize = config.getInt("tierSize")

        // Generate data model.
        model = TierModel(handler, tierInterval, tierSize, path);

        // If we get this far, plugin is successfully enabled!
        handler.info("Enabled")
    }

    override fun onDisable() {
        // Serialize model settings.
        model.serialize()
        handler.info("Wrote data")
        handler.info("Disabled")
    }
}