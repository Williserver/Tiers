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
class JoinListener(private val logger: LogHandler,
                   private val luckPresent: Boolean,
                   private val model: TierModel): Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        // Join player to rank if possible
        if (luckPresent) {
            // attempt to create a tier group whenever a player joins.
            createTierGroup(model.currentTier)
            playerJoinTier(model.currentTier, e.player.name)
        }

        // Increment the tier if player cap reached.
        val numPlayers = Bukkit.getOnlinePlayers().size
        logger.info("$numPlayers online")
        logger.info("Need ${model.playersForNextTier()} players to unlock next tier")

        // Check if we need to increment the interval.
        if (numPlayers.toUInt() >= model.playersForNextTier()) {
            model.incrementTier()
            logger.info("$numPlayers online. Tier ${model.currentTier} unlocked!")
            Bukkit.broadcast(Component.text("[TIERS]: $numPlayers online. Tier ${model.currentTier} unlocked!", NamedTextColor.DARK_RED))

            // Increase radius of border to match.
            setBorderWidth(model.borderWidth())
        }
    }
}