package net.williserver.tiers

/**
 * The TierModel represents which tier we're in.
 *
 * @param tierInterval number of players per tier.
 * @param tierSize Size of a single tier
 * @param currentTier tier we're in. This changes.
 *
 * @author Willmo3
 */

// Data model: file abstraction
class TierModel(private val tierInterval: UInt, private val tierSize: UInt, private var path: String) {
    // Need method to read in the model.

    // Need method to write out the model.

    // Will need to reset the worldborder on each load -- in case things change.
}