package net.williserver.tiers.model

import java.io.File
import com.google.gson.Gson
import net.williserver.tiers.LogHandler
import java.io.FileReader
import java.io.FileWriter
import kotlin.math.max

/**
 * The TierModel represents which tier we're in.
 *
 * @param config TierConfig to pull options from.
 * @param path Path to load saved data from, if it exists.
 *
 * @author Willmo3
 */

// Data model: file abstraction
class TierModel(private val logger: LogHandler, private val config: TierConfig, private var path: String) {
    private val defaultTier = 1u
    private val defaultWidth = 1u

    // If file doesn't exist, initialize default tier -- 1u
    var currentTier: UInt =
        if (File(path).exists()) {
            Gson().fromJson(FileReader(path).readText(), UInt::class.java)
        } else {
            defaultTier
        }
        internal set

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
    fun playersForNextTier(): UInt = currentTier * config.tierInterval.toUInt()

    /**
     * Get the border width for this tiermodel.
     */
    fun borderWidth(): UInt = max(currentTier * config.tierSize.toUInt(), defaultWidth)

    /**
     * Increment the tier used in this model if the tier is less than uint.max_value.
     */
    fun incrementTier() =
        when (currentTier) {
            UInt.MAX_VALUE -> logger.err("Tier has reached maximum uint value! Not incrementing to avoid overflow.")
            else -> currentTier += 1u
        }

    /**
     * Decrement the tier used in this model if the tier is greater than one.
     */
    fun decrementTier() =
        when (currentTier) {
            1u -> logger.err("Tier has reached minimum value of 1. Not decrementing.")
            else -> currentTier -= 1u
        }
}