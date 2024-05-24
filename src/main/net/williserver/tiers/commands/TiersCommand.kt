package net.williserver.tiers.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.williserver.tiers.LogHandler
import net.williserver.tiers.model.TierModel
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

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
                "help" -> help(sender, args)
                "get" -> get(sender, args)
                "set" -> set(sender, args)
                "inc" -> inc(sender, args)
                "dec" -> dec(sender, args)
                else -> false
            }
        } else false

    /**
     * Subfunction for get command.
     * Format: /tiers get
     *
     * @param s Entity which sent the command.
     * @param args Args to command -- should be one arg.
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
     * @param args Args to command -- should be two args.
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

    /**
     * Give help information to sender.
     *
     * @param s Command Sender.
     * @param args Args for command -- should be only one arg.
     */
    private fun help(s: CommandSender, args: Array<String>): Boolean {
        if (args.size != 1) {
            return false
        }

        val help = StringBuilder()
        help.append("Tiers commands:\n")
        help.append("-- /tiers help: pull up this help menu\n")
        help.append("-- /tiers get: get the current tier\n")
        help.append("-- /tiers set (value): set the tier to given value.\n")
        help.append("-- /tiers inc: increment the current tier.\n")
        help.append("-- /tiers dec: decrement the current tier.\n")

        s.sendMessage(help.toString())
        return true
    }

    /**
     * Increment the model value.
     * Will not work if overflows.
     *
     * @param s Command sender.
     * @param args Args for command -- should be only one arg.
     */
    private fun inc(s: CommandSender, args: Array<String>): Boolean {
        if (args.size != 1) {
            return false
        }

        // Returning true so that usage information is not displayed.
        // Still exiting prematurely
        if (!s.hasPermission("tiers.inc")) {
            s.sendMessage(
                Component.text("[TIERS]: You do not have permission to increment the tier!",
                    NamedTextColor.RED))
            return true
        }

        if (model.currentTier == UInt.MAX_VALUE) {
            s.sendMessage(Component.text("[TIERS]: Model has reached max width! Not incrementing.",
                NamedTextColor.RED))
            return true
        }

        model.incrementTier()
        changeTier(model)
        return true
    }

    /**
     * Decrement the model value.
     * Will not work if sets below 1.
     *
     * @param s Command sender.
     * @param args Args for command -- should be only one arg.
     */
    private fun dec(s: CommandSender, args: Array<String>): Boolean {
        if (args.size != 1) {
            return false
        }

        // Returning true so that usage information is not displayed.
        // Still exiting prematurely
        if (!s.hasPermission("tiers.dec")) {
            s.sendMessage(
                Component.text("[TIERS]: You do not have permission to decrement the tier!",
                    NamedTextColor.RED))
            return true
        }

        if (model.currentTier == 1u) {
            s.sendMessage(
                Component.text("[TIERS]: Model has reached minimum width! Not decrementing.",
                    NamedTextColor.RED))
            return true
        }

        model.decrementTier()
        changeTier(model)
        return true
    }
}