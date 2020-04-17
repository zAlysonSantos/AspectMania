package com.alysonsantos.homes

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import com.alysonsantos.homes.command.registerCommands
import com.alysonsantos.homes.manager.HomeManager
import com.alysonsantos.homes.manager.SQLManager

class AspectHomes : KotlinPlugin() {

    val sqlManager = lifecycle(100) { SQLManager(this) }
    val homeManager = lifecycle(90) { HomeManager(this) }

    val config = config("config.yml", Config)

    override fun onPluginEnable() {
        registerCommands()
        registerEvents()
    }
}