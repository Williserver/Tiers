package net.williserver.tiers

import org.bukkit.plugin.java.JavaPlugin

/**
 * TiersPlugin. Allows for configurable rescaling of world border as more players join.
 *
 * @author Willmo3
 */
class TiersPlugin : JavaPlugin() {
    private val handler = LogHandler(super.getLogger())
    // Default path for this baby.
    private val path = "$dataFolder/tiers.json"
    // Model initialized after config loaded.
    private lateinit var model: TierModel
    // Name of LuckPerms plugin
    private val luckName = "LuckPerms"

    override fun onEnable() {
        // ***** LOAD CONFIG ***** //

        // Save config with defaults if not present.
        saveDefaultConfig()
        // Load config file.
        val tierInterval = config.getInt("tierInterval")
        val tierSize = config.getInt("tierSize")
        // TrackName not optional!
        val trackName = config.getString("trackName")!!
        val useRanks = config.getBoolean("useRanks")

        // ***** PREPARE MODEL ***** //

        // Generate data model.
        model = TierModel(handler, tierInterval, tierSize, path)
        // Set border width based on starting player count.
        setBorderWidth(model.borderWidth())

        // ***** LUCKPERMS INTEGRATION ***** //
        val useLuck = useRanks && server.pluginManager.getPlugin(luckName) != null

        // ***** PREPARE LISTENER ***** //

        // Add player join listener.
        val joiner = JoinListener(handler, useLuck, model, trackName)
        server.pluginManager.registerEvents(joiner, this)
        handler.info("Register tiers join listener.")

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