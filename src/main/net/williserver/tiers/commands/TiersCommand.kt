package net.williserver.tiers.commands

import net.williserver.tiers.LogHandler
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * Base tiers command.
 * Called both to get current tier and modify tier.
 *
 * @author Willmo3
 */
class TiersCommand(val logger: LogHandler): CommandExecutor {

    override fun onCommand(p0: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        logger.info(args.toString())
        return false
    }
}