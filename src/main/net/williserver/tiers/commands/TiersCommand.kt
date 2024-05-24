package net.williserver.tiers.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.williserver.tiers.LogHandler
import net.williserver.tiers.model.TierModel
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

/**
 * Base tiers command.
 * Called both to get current tier and modify tier.
 *
 * @author Willmo3
 */
class TiersCommand(private val logger: LogHandler,
                   private val model: TierModel): CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean =
        if (args.isNotEmpty()) {
            when (args[0]) {
                "get" -> get(sender, args)
                "set" -> set(sender, args)
                else -> false
            }
        } else false

    /**
     * Subfunction for get command.
     * Format: /tiers get
     */
    private fun get(s: CommandSender, args: Array<String>): Boolean =
        if (args.size != 1) {
            false
        } else {
            s.sendMessage("[TIERS]: Server is on tier ${model.currentTier} with border width ${model.borderWidth()}.")
            true
        }

    /**
     * Subfunction for set command.
     * Format: /tiers set
     *
     * @param s Entity which sent the command.
     * @param args Args to command.
     */
    private fun set(s: CommandSender, args: Array<String>): Boolean {
        // Malformed command if incorrect arg count.
        if (args.size != 2) {
            return false
        }

        // Returning true so that usage information is not displayed.
        // Still exiting prematurely
        if (!s.hasPermission("tiers.set")) {
            s.sendMessage(
                Component.text("[TIERS]: You do not have permission to set the tier!", NamedTextColor.RED))
            return true
        }

        val tier = args[1].toIntOrNull() ?: return false
        if (tier < 1) {
            s.sendMessage(
                Component.text("[TIERS]: Tier must be greater than zero!", NamedTextColor.RED))
        } else {
            model.currentTier = tier.toUInt()
            changeTier(model)
        }

        return true
    }

}