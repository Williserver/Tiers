package net.williserver.tiers.commands

import net.williserver.tiers.LogHandler
import net.williserver.tiers.model.TierModel
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
                else -> false
            }
        } else false

    /**
     * Subfunction for get command.
     */
    private fun get(s: CommandSender, args: Array<String>): Boolean =
        if (args.size != 1) {
            false
        } else {
            s.sendMessage("[TIERS]: Server is on tier ${model.currentTier} with border width ${model.borderWidth()}.")
            true
        }
}