package net.williserver.tiers

import org.bukkit.Bukkit

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
 * @param player username of player
 */
fun playerJoinTier(tier: UInt, player: String) = runCommand("lp user $player parent add t$tier")

/**
 * Create a new tier group.
 *
 * @param tier Tier to use.
 * @param trackName Name of track for tiers.
 */
fun createTierGroup(tier: UInt, trackName: String) {
    // Name of new group to be created.
    val groupname = "t$tier"

    runCommand("lp creategroup $groupname")
    runCommand("lp track $trackName append $groupname")
    // Give prefixes priority $tier.
    // NOTE: Server should be configured to display lower tiers first!
    runCommand("lp group $groupname meta addprefix $tier &7[$groupname]")
}

/**
 * Run the given command text through the Bukkit dispatcher.
 */
private fun runCommand(command: String) {
    Bukkit.getServer().dispatchCommand(
        Bukkit.getServer().consoleSender, command)
}