package net.williserver.tiers

import net.williserver.tiers.commands.TiersCommand
import net.williserver.tiers.commands.TiersTabCompleter
import net.williserver.tiers.commands.setBorderWidth
import net.williserver.tiers.listeners.ThresholdJoinListener
import net.williserver.tiers.model.*
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * TiersPlugin. Allows for configurable rescaling of world border as more players join.
 *
 * @author Willmo3
 */
class TiersPlugin : JavaPlugin() {
    private val handler = LogHandler(super.getLogger())
    // Default path for this baby.
    private val path = "$dataFolder${File.separator}tiers.json"
    // Model initialized after config loaded.
    private lateinit var model: TiersModel
    // Name of LuckPerms plugin
    private val luckName = "LuckPerms"

    override fun onEnable() {
        // Load configuration options
        saveDefaultConfig()
        val luckPresent = server.pluginManager.getPlugin(luckName) != null
        val config = TiersConfigLoader(handler, config, luckPresent).config

        // Generate data model.
        model = TiersModel(handler, config, deserialize(path))
        // Set border width based on starting player count.
        setBorderWidth(model.borderWidth())

        // Add player join listener.
        val joiner = ThresholdJoinListener(handler, config, model)
        server.pluginManager.registerEvents(joiner, this)
        handler.info("Register tiers join listener.")

        // Register commands
        this.getCommand("tiers")!!.setExecutor(TiersCommand(handler, model))
        this.getCommand("tiers")!!.tabCompleter = TiersTabCompleter()

        // If we get this far, plugin is successfully enabled!
        handler.info("Enabled")
    }

    override fun onDisable() {
        // Serialize model settings.
        serialize(model, path)
        handler.info("Wrote data")
        handler.info("Disabled")
    }
}