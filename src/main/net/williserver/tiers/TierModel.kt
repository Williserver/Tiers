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
class TierModel(private val logger: LogHandler, private val tierInterval: Int, private val tierSize: Int, private var path: String) {
    // If file doesn't exist, initialize default tier -- 1u
    val currentTier: UInt =
        if (File(path).exists()) {
            Gson().fromJson(FileReader(path).readText(), UInt::class.java)
        } else {
            1u
        }

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
}