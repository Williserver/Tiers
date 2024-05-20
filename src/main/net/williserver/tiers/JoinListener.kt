package net.williserver.tiers

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * JoinListener.
 * When a player joins, see if we've exceeded the minimum to progress to the next tier.
 *
 * @author Willmo3
 */
class JoinListener(val logger: LogHandler, val model: TierModel): Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val numPlayers = Bukkit.getOnlinePlayers().size
        logger.info("$numPlayers online")

        val nextTier = (model.currentTier + 1u) * model.tierInterval.toUInt()
        logger.info("Need $nextTier players to unlock next tier")

        // Check if we need to increment the interval.
        if (numPlayers.toUInt() >= nextTier) {
            logger.info("$numPlayers joined -- moving to next tier!")
            model.increment_tier()
        }
    }
}