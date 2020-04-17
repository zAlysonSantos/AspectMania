package com.alysonsantos.homes.command

import br.com.devsrsouza.kotlinbukkitapi.architecture.lifecycle.cancellableCoroutineScope
import br.com.devsrsouza.kotlinbukkitapi.dsl.command.CommandDSL
import br.com.devsrsouza.kotlinbukkitapi.dsl.command.arguments.boolean
import br.com.devsrsouza.kotlinbukkitapi.dsl.command.arguments.optional
import br.com.devsrsouza.kotlinbukkitapi.dsl.command.arguments.string
import br.com.devsrsouza.kotlinbukkitapi.dsl.command.command
import br.com.devsrsouza.kotlinbukkitapi.dsl.command.fail
import br.com.devsrsouza.kotlinbukkitapi.extensions.bukkit.onlinePlayer
import br.com.devsrsouza.kotlinbukkitapi.extensions.player.msg
import br.com.devsrsouza.kotlinbukkitapi.extensions.text.msg
import com.alysonsantos.homes.*
import kotlinx.coroutines.launch

internal fun AspectHomes.registerCommands() {

    fun CommandDSL.applyMessages() {
        permissionMessage = MessagesConfig.no_permission
        onlyInGameMessage = MessagesConfig.only_in_game
    }

    command("home") {
        permission = PERMISSION_CMD_HOME
        description = "Teleport to your home"
        applyMessages()

        executorPlayer {
            val homeName = optional { string(0) } ?: Config.default_home_name

            val home = homeManager.findCachedHome(sender, homeName)

            if (home != null) {
                sender.teleport(home.location)
                sender.msg(MessagesConfig.home_teleport.replaceHome(home.name))
            } else {
                sender.msg(MessagesConfig.home_not_found)
            }
        }
    }

    command("sethome") {
        permission = PERMISSION_CMD_SETHOME
        description = "Sets a new home with the given name"
        applyMessages()

        executorPlayer {
            val name = string(0)

            if (homeManager.canSetHome(sender, name)) {
                homeManager.setHome(sender, name)

                sender.msg(MessagesConfig.home_set.replaceHome(name))
            } else {
                sender.msg(MessagesConfig.home_limit_arrived)
            }
        }
    }

    command("delhome", "deletehome", "removehome") {
        permission = PERMISSION_CMD_DELHOME
        description = "Delete a home"
        applyMessages()

        executorPlayer {
            val home = string(0)

            if (homeManager.deleteHome(sender, home)) {
                sender.msg(MessagesConfig.home_deleted.replaceHome(home))
            } else {
                sender.msg(MessagesConfig.home_not_found.replaceHome(home))
            }
        }
    }

    command("homes") {
        permission = PERMISSION_CMD_HOMES
        description = "List all your homes"
        applyMessages()


        executorPlayer {
            val homes = homeManager.getHomes(sender)
                    .takeIf { it.isNotEmpty() }
                    ?: fail(MessagesConfig.none_home)

            val homeString = homes.joinToString(", ") { it.name }

            sender.msg(MessagesConfig.home_list.replaceHomeList(homeString))
        }
    }

    command("makePublic") {
        permission = PERMISSION_CMD_MAKE_PUBLIC
        description = "Turns your home in public home"
        applyMessages()

        executorPlayer {
            val homeName = string(0)
            val enable = optional { boolean(1) } ?: true

            val home = homeManager.findCachedHome(sender, homeName)

            val status = if (enable) "§ePública" else "§cPrivada"

            if (home != null) {
                homeManager.makePublic(home, enable)

                sender.msg(listOf(
                        "",
                        "§a Home '&7${homeName}'&a alterada com sucesso!",
                        "§f Status: ${status}",
                        ""
                ))
            } else {
                sender.msg(listOf(
                        "",
                        "§c Essa home não existe.",
                        ""
                ))
            }
        }
    }

    command("visit") {
        permission = PERMISSION_CMD_VISIT
        description = "Visit a home from a player"
        applyMessages()

        executorPlayer {
            val targetName = string(0)
            val homeName = string(1)

            val home = onlinePlayer(targetName)?.let {
                homeManager.findCachedHome(it, homeName)
            }

            fun onSuccess() = sender.msg(
                    MessagesConfig.home_teleport
                            .replaceHome(homeName)
            )

            fun onFail() = sender.msg(
                    MessagesConfig.home_not_public
                            .replaceTarget(targetName)
            )

            if (home != null) {
                if (home.isPublic) {
                    sender.teleport(home.location)
                    onSuccess()
                } else onFail()
            } else {
                cancellableCoroutineScope.launch {
                    val home = homeManager.findStorageHome(targetName, homeName)

                    if (home?.isPublic == true && sender.isValid && sender.isOnline) {
                        sender.teleport(home.location)
                        onSuccess()
                    } else onFail()
                }
            }
        }
    }
}