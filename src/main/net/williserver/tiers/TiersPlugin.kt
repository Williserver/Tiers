package net.williserver.tiers

import net.williserver.tiers.commands.TiersCommand
import net.williserver.tiers.commands.setBorderWidth
import net.williserver.tiers.listeners.JoinListener
import net.williserver.tiers.model.TierConfig
import net.williserver.tiers.model.TierModel
import org.bukkit.plugin.java.JavaPlugin

/**
 * TiersPlugin. Allows for configurable rescaling of world border as more players join.
 *
 * @author Willmo3
 */
class TiersPlugin : JavaPlugin() {
    private val handler = LogHandler(super.getLogger())
    // Default path for this baby.
    private val path = "$dataFolder/tiers.json"
    // Model initialized after config loaded.
    private lateinit var model: TierModel
    // Name of LuckPerms plugin
    private val luckName = "LuckPerms"

    override fun onEnable() {
        // Load configuration options
        saveDefaultConfig()
        val luckPresent = server.pluginManager.getPlugin(luckName) != null
        val config = TierConfig(handler, config, luckPresent)

        // Generate data model.
        model = TierModel(handler, config, path)
        // Set border width based on starting player count.
        setBorderWidth(model.borderWidth())

        // Add player join listener.
        val joiner = JoinListener(handler, config, model)
        server.pluginManager.registerEvents(joiner, this)
        handler.info("Register tiers join listener.")

        // Register commands
        this.getCommand("tiers")!!.setExecutor(TiersCommand(handler, model))

        // If we get this far, plugin is successfully enabled!
        handler.info("Enabled")
    }

    override fun onDisable() {
        // Serialize model settings.
        model.serialize()
        handler.info("Wrote data")
        handler.info("Disabled")
    }
}