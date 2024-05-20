package net.williserver.tiers

import java.io.File

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
class TierModel(private val logger: LogHandler, private val tierInterval: Int, private val tierSize: Int, private var path: String) {
    val currentTier: UInt

    // Read in values from file if they exist.
    init {
        val data = File(path)

        // If file doesn't exist, initialize default tier -- 1u
        if (!data.exists()) {
            currentTier = 1u
        // If file does exist, load in via GSON
        } else {
            currentTier = 1u
        }
    }
}