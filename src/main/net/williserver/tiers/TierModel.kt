package net.williserver.tiers

import java.io.File
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter

/**
 * The TierModel represents which tier we're in.
 *
 * @param tierInterval number of players per tier.
 * @param tierSize Size of a single tier
 * @param path Path to load saved data from, if it exists.
 *
 * @author Willmo3
 */

// Data model: file abstraction
class TierModel(private val logger: LogHandler, val tierInterval: Int, val tierSize: Int, private var path: String) {
    private val defaultTier = 1u
    private val defaultWidth = 1u

    // If file doesn't exist, initialize default tier -- 1u
    var currentTier: UInt =
        if (File(path).exists()) {
            Gson().fromJson(FileReader(path).readText(), UInt::class.java)
        } else {
            defaultTier
        }
        private set

    // On init, log tier being used.
    init {
        logger.info("Initialized with tier: $currentTier")
    }

    /**
     * Serialize this file to our path.
     */
    fun serialize() {
        // Open file.
        val writer = FileWriter(path)
        Gson().toJson(currentTier, writer)
        writer.close()
    }

    /**
     * Return the number of players needed to reach the next tier.
     */
    fun players_for_next_tier(): UInt = (currentTier + 1u) * tierInterval.toUInt()

    /**
     * Get the border width for this tiermodel.
     */
    fun border_width(): UInt =
        if (currentTier * tierSize.toUInt() > 0u) {
            currentTier * tierSize.toUInt()
        } else {
            defaultWidth
        }

    /**
     * Increment the tier used in this model.
     */
    fun increment_tier() {
        currentTier += 1u
    }
}