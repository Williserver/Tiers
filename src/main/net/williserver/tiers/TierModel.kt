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
class TierModel(private val tierInterval: Int, private val tierSize: Int, private var path: String) {
    // Read in values from file if they exist.
    init {
        val data = File(path)
        // For now, build the file if it doesn't exist.
        if (!data.exists()) {
            data.createNewFile()
        }
    }

    // Need method to read in the model.

    // Need method to write out the model.

    // Will need to reset the worldborder on each load -- in case things change.
}