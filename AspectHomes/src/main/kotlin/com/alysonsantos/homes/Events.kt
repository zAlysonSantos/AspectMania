package com.alysonsantos.homes

import br.com.devsrsouza.kotlinbukkitapi.extensions.event.event
import br.com.devsrsouza.kotlinbukkitapi.extensions.event.events
import org.bukkit.event.player.PlayerJoinEvent

fun AspectHomes.registerEvents() = events {
    event<PlayerJoinEvent> {
        homeManager.loadPlayer(player)
    }
}