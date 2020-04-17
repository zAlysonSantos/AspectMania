package com.alysonsantos.homes.manager

import br.com.devsrsouza.kotlinbukkitapi.architecture.lifecycle.LifecycleListener
import br.com.devsrsouza.kotlinbukkitapi.collections.onlinePlayerMapOf
import br.com.devsrsouza.kotlinbukkitapi.extensions.skedule.BukkitDispatchers
import com.alysonsantos.homes.*
import kotlinx.coroutines.*
import org.bukkit.entity.Player

class HomeManager(
        override val plugin: AspectHomes)
    : LifecycleListener<AspectHomes> {

    private val homesCache = onlinePlayerMapOf<MutableList<Home>>()

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + BukkitDispatchers.SYNC)

    private val sqlManager get() = plugin.sqlManager

    override fun onPluginEnable() {
    }

    override fun onPluginDisable() {
        runBlocking {
            job.join()
        }
    }

    fun loadPlayer(player: Player) {
        coroutineScope.launch {
            val playerHomes = sqlManager.homesFromPlayer(player)

            if (player.isOnline)
                homesCache.put(player, playerHomes.toMutableList())
        }
    }

    fun findCachedHome(
            player: Player,
            home: String
    ): Home? = homesCache[player]?.find { it.name.equals(home, ignoreCase = true) }

    suspend fun findStorageHome(
            playerName: String,
            home: String
    ): Home? = withContext(job) {
        sqlManager.findHome(playerName, home)
    }

    fun getHomes(player: Player): List<Home> {
        return homesCache[player] ?: emptyList()
    }

    fun setHome(player: Player, name: String) {
        val playerName = player.name
        val playerLocation = player.location.clone()
        val actualHome = findCachedHome(player, name)

        coroutineScope.launch {
            println(Thread.currentThread().name)

            if (actualHome != null) {
                sqlManager.updateHome(actualHome) {
                    this@updateHome.location = location
                }
            } else {
                sqlManager.createHome(playerName, name, playerLocation)
            }
        }
    }

    fun canSetHome(player: Player, name: String): Boolean {
        return findCachedHome(player, name) != null || playerInLimit(player)
    }

    private fun playerInLimit(player: Player): Boolean {
        if (player.hasPermission(PERMISSION_LIMIT_UNLIMITED))
            return true

        val max = Config.limit.map {
            if (player.hasPermission(PERMISSION_LIMIT_BASE + ".${it.key}"))
                it.value
            else
                null
        }.filterNotNull().max() ?: 0

        val currentHomes = homesCache[player]?.size ?: 0

        return currentHomes < max
    }

    fun deleteHome(player: Player, home: String): Boolean {
        val playerName = player.name;

        if (homesCache[player]?.removeIf { it.name.equals(home, ignoreCase = true) } == true) {
            coroutineScope.launch {
                sqlManager.deleteHome(playerName, home)
            }
            return true
        } else {
            return false
        }
    }

    fun makePublic(home: Home, enable: Boolean) {
        coroutineScope.launch {
            sqlManager.updateHome(home) {
                this@updateHome.isPublic = enable
            }
        }
    }
}
