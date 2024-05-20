package net.williserver.tiers

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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
class JoinListener(private val logger: LogHandler, private val model: TierModel): Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        // Increment the tier if player cap reached.
        val numPlayers = Bukkit.getOnlinePlayers().size
        logger.info("$numPlayers online")

        logger.info("Need ${model.players_for_next_tier()} players to unlock next tier")

        // Check if we need to increment the interval.
        if (numPlayers.toUInt() >= model.players_for_next_tier()) {
            model.increment_tier()
            logger.info("$numPlayers online. Tier ${model.currentTier} unlocked!")
            Bukkit.broadcast(Component.text("[TIERS]: $numPlayers online. Tier ${model.currentTier} unlocked!", NamedTextColor.DARK_RED))

            // Increase radius of border to match.
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "worldborder set ${model.border_width()}")
        }
    }
}