package net.williserver.tiers.model

import net.williserver.tiers.LogHandler
import java.io.File
import java.io.FileWriter
import kotlin.math.max
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.FileReader

/**
 * TiersData collects all persistent data for tiers operation.
 *
 * @param currentTier Tier current server instance is running.
     */
@Serializable
data class TiersData(val currentTier: UInt)

/**
 * The TierModel represents which tier we're in.
 *
 * @param logger Logging utility.
 * @param config TierConfig to pull options from.
 * @param data Starting data store.
 *
 * @author Willmo3
 */
// Data model: file abstraction
class TiersModel(private val logger: LogHandler, private val config: TiersConfig, data: TiersData) {
    private val defaultTier = 1u
    private val defaultWidth = 1u

    // If invalid tier supplied initialize, with default tier.
    var currentTier: UInt = defaultTier

    init {
        if (data.currentTier > defaultTier) {
            currentTier = data.currentTier
        }
        logger.info("Initialized with tier: $currentTier")
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

// ***** FILE IO FNS ***** //

/**
 * Load the current tier from a path.
 *
 * @param path file system path to read from.
 * @return The tier to start a tier with.
 */
fun deserialize(path: String): TiersData {
    if (!File(path).exists()) {
        return TiersData(1u)
    }
    val reader = FileReader(path)
    val jsonString = reader.readText()
    reader.close()
    return Json.decodeFromString<TiersData>(jsonString)
}

/**
 * Write the current tier in json format to a file.
 *
 * @param model Model to serialize
 * @param path Path to read from.
 */
fun serialize(model: TiersModel, path: String) {
    val writer = FileWriter(path)
    writer.write(Json.encodeToString(TiersData(model.currentTier)))
    writer.close()
}