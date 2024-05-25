package net.williserver.tiers.model

import net.williserver.tiers.LogHandler
import org.bukkit.configuration.file.FileConfiguration

/**
 * Responsible for parsing config options and restoring defaults, if need be.
 * Interfaces into a read-only data class.
 *
 * @param handler Logger
 * @param config Configuration file to parse options from.
 * @param ranksPluginPresent Whether there is a backend plugin for our ranks to target.
 * @author Willmo3
 */
class TiersConfigLoader(private val handler: LogHandler,
                       private val fileConfig: FileConfiguration,
                       private val ranksPluginPresent: Boolean) {

    // ***** CONFIG FIELDS ***** //
    private val tierIntervalOption = "tierInterval"
    private val tierSizeOption = "tierSize"
    private val usePrefixOption = "usePrefix"
    private val useRanksOption = "tierRanks"
    private val trackNameOption= "trackName"

    // Config defaults
    private val defaultTierInterval = 10
    private val defaultTierSize = 500
    private val defaultTrackName = "tiers"

    // Loaded fields contained in final tiersConfig.
    // This may be accessed externally.
    val config: TiersConfig

    init {
        // Load config file.
        val loadedInterval = fileConfig.getInt(tierIntervalOption)
        val tierInterval = if (loadedInterval >= 1) {
            loadedInterval
        } else {
            handler.err("Tier interval $loadedInterval too small, must be at least one.")
            defaultTierInterval
        }

        val loadedSize = fileConfig.getInt(tierSizeOption)
        val tierSize = if (loadedSize >= 1) {
            loadedSize
        } else {
            handler.err("loaded tier size $loadedSize too small, must be at least one.")
            defaultTierSize
        }

        // TrackName not optional!
        val loadedTrackName = fileConfig.getString(trackNameOption)
        val trackName = if (loadedTrackName != null) {
            loadedTrackName
        } else {
            handler.err("Track name not specified!")
            defaultTrackName
        }

        // Loading booleans defaults to false
        // NOTE: only use ranks if we've got a backend to target!
        val useRanks = fileConfig.getBoolean(useRanksOption) && ranksPluginPresent
        val usePrefix = fileConfig.getBoolean(usePrefixOption)

        config = TiersConfig(tierInterval, tierSize, useRanks, usePrefix, trackName)
    }
}

/**
 * TiersConfig data should be interfaced outside of File IO.
 * Therefore, TiersConfig class is passed as opposed to TiersConfigLoader.
 *
 * @param tierInterval Number of players to move to next tier.
 * @param tierSize Interval to increase width by
 * @param useRanks Whether to integrate with LuckPerms ranks.
 * @param usePrefix Whether to apply a prefix
 * @param trackName Name of track to use.
 */
data class TiersConfig(val tierInterval: Int,
                       val tierSize: Int,
                       val useRanks: Boolean,
                       val usePrefix: Boolean,
                       val trackName: String)