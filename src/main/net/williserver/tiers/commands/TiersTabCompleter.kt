package net.williserver.tiers.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TiersTabCompleter: TabCompleter {

    /**
     * Tab completion for tiers command.
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
            completions.removeAll{ !it.startsWith(args[0].lowercase()) }
        }

        return completions
    }
}