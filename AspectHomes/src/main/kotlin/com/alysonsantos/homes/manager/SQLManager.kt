package com.alysonsantos.homes.manager

import br.com.devsrsouza.kotlinbukkitapi.architecture.lifecycle.LifecycleListener
import br.com.devsrsouza.kotlinbukkitapi.exposed.databaseTypeFrom
import com.alysonsantos.homes.AspectHomes
import com.alysonsantos.homes.Home
import com.alysonsantos.homes.SQLConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.bukkit.Location
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class SQLManager(
        override val plugin: AspectHomes)
    : LifecycleListener<AspectHomes> {

    private lateinit var dataSource: HikariDataSource
    private lateinit var database: Database

    override fun onPluginEnable() {
        dataSource = databaseTypeFrom(plugin.dataFolder, SQLConfig).dataSource()
        database = Database.connect(dataSource)
    }

    override fun onPluginDisable() {
        dataSource.close()
    }

    suspend fun homesFromPlayer(
            player: Player
    ): List<Home> = newSuspendedTransaction(Dispatchers.IO, database) {
        Home.homesFromPlayer(player)
    }

    suspend fun findHome(
            playerName: String,
            home: String
    ) = newSuspendedTransaction(Dispatchers.IO, database) {
        Home.findHome(playerName, home)
    }

    suspend fun createHome(
            player: String,
            name: String,
            location: Location
    ): Home = newSuspendedTransaction(Dispatchers.IO, database) {
        Home.newHome(player, name, location)
    }

   suspend fun updateHome(
           actualHome: Home,
           updateBlock: Home.() -> Unit
    ) = newSuspendedTransaction(Dispatchers.IO, database) {
        updateBlock(actualHome)
    }

   suspend fun deleteHome(
            player: String,
            home: String
    ) = newSuspendedTransaction(Dispatchers.IO, database) {
      Home.deleteHome(player, home)
    }
}