package net.williserver.tiers.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TiersTabCompleter: TabCompleter {

    /**
     * Tab completion for tiers command.
     *
     * @param sender Entity which sent the command and will experience tab completion.
     * @param command command being completed for; if not "tiers", this tab completer will ignore.
     * @param alias Alternate name for command; unused.
     * @param args Arguments passed to command.
     *
     * @author Willmo3
     */
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): MutableList<String> {
        val completions = mutableListOf<String>()

        // Only listen for tiers command
        if (!command.name.equals("tiers", ignoreCase = true)) {
            return completions
        }

        // Add all possible suggestions for when the user hasn't typed anything.
        if (args.size == 1) {
            completions.add("help")
            completions.add("get")
            completions.add("inc")
            completions.add("dec")
            completions.add("set")
            // Only add completions that correspond with the subcommand they're typing.
            completions.removeAll{ !it.startsWith(args[0].lowercase()) }
        }

        return completions
    }
}