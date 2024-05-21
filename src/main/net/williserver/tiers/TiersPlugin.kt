package net.williserver.tiers

import org.apache.commons.lang3.ObjectUtils.Null
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

    // ***** CONFIG FIELDS ***** //
    private val tierIntervalOption = "tierInterval"
    private val tierSizeOption = "tierSize"
    private val trackNameOption= "trackName"
    private val useRanksOption = "tierRanks"

    // Config defaults
    private val defaultTierInterval = 10
    private val defaultTierSize = 500
    private val defaultTrackName = "tiers"

    override fun onEnable() {
        // ***** LOAD CONFIG ***** //

        // Save config with defaults if not present.
        saveDefaultConfig()

        // Load config file.
        val loadedInterval = config.getInt(tierIntervalOption)
        val tierInterval = if (loadedInterval >= 1) {
            loadedInterval
        } else {
            handler.err("Tier interval $loadedInterval too small, must be at least one.")
            defaultTierInterval
        }

        val loadedSize = config.getInt(tierSizeOption)
        val tierSize = if (loadedSize >= 1) {
            loadedSize
        } else {
            handler.err("loaded tier size $loadedSize too small, must be at least one.")
            defaultTierSize
        }

        // TrackName not optional!
        val loadedTrackName = config.getString(trackNameOption)
        val trackName = if (loadedTrackName != null) {
            loadedTrackName
        } else {
            handler.err("Track name not specified!")
            defaultTrackName
        }

        // This will default to false.
        val useRanks = config.getBoolean(useRanksOption)

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