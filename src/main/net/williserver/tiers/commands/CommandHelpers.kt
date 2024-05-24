package net.williserver.tiers.commands
import net.williserver.tiers.model.TierConfig
import org.bukkit.Bukkit

/**
 * For simplicity, Tiers interfaces with the server by simply running the relevant commands.
 * These are implemented here.
 *
 * @author Willmo3
 */

/**
 * Set the border width of the server.
 *
 * @param width new width
 */
fun setBorderWidth(width: UInt) = runCommand("worldborder set $width")

/**
 * Join a player in the specified tier.
 *
 * @param tier tier to add player to
 * @param username username of player
 */
fun playerJoinTier(tier: UInt, username: String) = runCommand("lp user $username parent add t$tier")

/**
 * Create a new tier group.
 *
 * @param tier Tier to use.
 * @param config TierConfig to grab needed fields from.
 */
fun createTierGroup(tier: UInt, config: TierConfig) {
    // Name of new group to be created.
    val groupname = "T$tier"

    runCommand("lp creategroup $groupname")
    runCommand("lp track ${config.trackName} append $groupname")

    if (config.usePrefix) {
        // Give prefixes priority $tier.
        // NOTE: Server should be configured to display lower tiers first!
        runCommand("lp group $groupname meta addprefix $tier &7[$groupname]")
    }
}

/**
 * Run the given command text through the Bukkit dispatcher.
 */
private fun runCommand(command: String) {
    Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, command)
}