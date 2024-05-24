package net.williserver.tiers.listeners

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.williserver.tiers.*
import net.williserver.tiers.commands.createTierGroup
import net.williserver.tiers.commands.playerJoinTier
import net.williserver.tiers.commands.setBorderWidth
import net.williserver.tiers.model.TierConfig
import net.williserver.tiers.model.TierModel
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
                   private val config: TierConfig,
                   private val model: TierModel
): Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        // Join player to rank if possible
        if (config.useRanks) {
            // attempt to create a tier group whenever a player joins.
            createTierGroup(model.currentTier, config)
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

            Bukkit.broadcast(Component.text("[TIERS]: $numPlayers online. Worldborder increased to ${model.borderWidth()}!", NamedTextColor.DARK_RED))
            Bukkit.broadcast(Component.text("[TIERS]: ${model.playersForNextTier()} players needed to reach tier ${model.currentTier + 1u}", NamedTextColor.DARK_RED))

            // Increase radius of border to match.
            setBorderWidth(model.borderWidth())
        }
    }
}