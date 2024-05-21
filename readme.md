# Tiers
## Introduction
Tiers is a Minecraft plugin for dynamically expanding the bounds of your world as new players join.

A fundamental feature of Minecraft is resource management. Restrictive world borders are often used for this feature, but they often lead a stale, overcrowded as the population increases. What if world borders grew with the server? And what if they did it in a fun way? Enter Tiers! 

Tiers rewards players for joining by unlocking new worldborder "tiers" when admin-specified player thresholds are met. For instance, the first time that twenty players get on a server concurrently, a new tier might be unlocked, permanently increasing the world border! Early members are rewarded with a rank to commemorate their longevity, and everyone gets to experience that first-day rush of discovery again -- on the very same server!

## Configuration
### tierInterval
This option controls how many players need to join concurrently, per tier, to unlock the next tier. Whenever currentTier * tierInterval players join, a new server tier is unlocked and the world border expands!

Importantly, TierInterval measures *concurrent connections*, not total unique logins.

#### Default: 10
By default, Tiers are unlocked in intervals of 10 players. This can be changed in config.yml.

### tierSize
How many blocks the width should be increased by every time a new tier is unlocked. Currently, this only supports linear scaling, meaning the total area of the server increases quadratically.

#### Default: 1000

### tierRanks
One of the key aspects of this plugin is giving OG players a pat on the back. If you have LuckPerms installed, you can choose to create "tier" ranks. 

Whenever a player joins the server, they gain a rank for the current server tier. Using LuckPerm's "lowest_on_track_tiers" prefix settings, you can show the first tier that the member joined!

This option chooses whether to attempt to use tierRanks. Note that this will only work if LuckPerms is installed on your server.

#### Default: True

### trackName
What track should we use to refer to our ranks in LuckPerms? Change this if you want to use the name "tiers"

#### Default: "tiers"

## Building

Currently, I'm not distributing jars. You'll need to build from source.

### Dependencies
1. Maven

Simply go to the project root, run `mvn install`, and copy the shaded jar (the one not titled original!) from the target directory.

I have had issues getting non-shaded jars to work, often because the Kotlin stdlib doesn't seem to be properly available on Paper plugins. This could very well be a me issue -- let me know!

## Design
When possible, I followed a  complexity minimizing design for this plugin, due to its small size. Modules are generally tightly coupled.

The main classes are:
1. TierModel: This contains the data model, serializing the number of players using Gson.
2. TierConfig: This parses the plugin config file, leaving the options available as read only properties.
3. JoinListener: This fires relevant events whenever players join, such as increasing the world border if a new tier has been unlocked and adding players to their tier ranks.
