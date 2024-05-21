package net.williserver.tiers

import org.bukkit.configuration.file.FileConfiguration

/**
 * Responsible for parsing config options and restoring defaults, if need be.
 *
 * @param handler Logger
 * @param config Configuration file to parse options from.
 * @author Willmo3
 */
data class TierConfig(private val handler: LogHandler, private val config: FileConfiguration) {
    // ***** CONFIG FIELDS ***** //
    private val tierIntervalOption = "tierInterval"
    private val tierSizeOption = "tierSize"
    private val trackNameOption= "trackName"
    private val useRanksOption = "tierRanks"

    // Config defaults
    private val defaultTierInterval = 10
    private val defaultTierSize = 500
    private val defaultTrackName = "tiers"

    // Loaded fields
    val tierInterval: Int
    val tierSize: Int
    val trackName: String
    val useRanks: Boolean

    init {
        // Load config file.
        val loadedInterval = config.getInt(tierIntervalOption)
        tierInterval = if (loadedInterval >= 1) {
            loadedInterval
        } else {
            handler.err("Tier interval $loadedInterval too small, must be at least one.")
            defaultTierInterval
        }

        val loadedSize = config.getInt(tierSizeOption)
        tierSize = if (loadedSize >= 1) {
            loadedSize
        } else {
            handler.err("loaded tier size $loadedSize too small, must be at least one.")
            defaultTierSize
        }

        // TrackName not optional!
        val loadedTrackName = config.getString(trackNameOption)
        trackName = if (loadedTrackName != null) {
            loadedTrackName
        } else {
            handler.err("Track name not specified!")
            defaultTrackName
        }

        // This will default to false.
        useRanks = config.getBoolean(useRanksOption)
    }
}