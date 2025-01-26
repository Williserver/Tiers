package net.williserver.tiers.model

import net.williserver.tiers.LogHandler
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Edge case tests for TiersModel.
 *
 * @author Willmo3
 */
class TiersModelTest {
    // Starter values
    private val config = TiersConfig(
        onlineOnlyIncrement = false,
        1,
        1,
        useRanks = false,
        usePrefix = false,
        "nunya"
    )
    private val logHandler = LogHandler(null)

    // Overflow test.
    @Test
    fun incrementTier() {
        val model = TiersModel(logHandler, config, TiersData(UInt.MAX_VALUE - 1u))

        model.incrementTier()
        assertEquals(UInt.MAX_VALUE, model.currentTier)

        model.incrementTier()
        assertEquals(UInt.MAX_VALUE, model.currentTier)
    }

    // Underflow test
    @Test
    fun decrementTier() {
        val model = TiersModel(logHandler, config, TiersData(2u))

        model.decrementTier()
        assertEquals(1u, model.currentTier)

        model.decrementTier()
        assertEquals(1u, model.currentTier)
    }

    // Ensure model defaults to tier 1.
    @Test
    fun startingTierTooSmall() {
        val model = TiersModel(logHandler, config, TiersData(0u))
        assertEquals(1u, model.currentTier)
    }

    // Even if there's overflow!
    @Test
    fun startingTierOverflow() {
        val model = TiersModel(logHandler, config, TiersData(UInt.MAX_VALUE + 1u))
        assertEquals(1u, model.currentTier)
    }
}