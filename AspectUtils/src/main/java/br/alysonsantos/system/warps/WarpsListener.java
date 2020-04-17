package br.alysonsantos.system.warps;

import br.alysonsantos.AspectUtil;
import br.alysonsantos.system.warps.helper.SpawnHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Random;

public class WarpsListener implements Listener {
    private SpawnHelper spawnHelper = AspectUtil.getInstance().getSpawnHelper();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void playerRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(spawnHelper.getLocationList().get(new Random().nextInt(15)));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);

        Player player = e.getPlayer();
        spawnHelper.teleportToSpawn(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        spawnHelper.teleportToSpawn(player);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equals("Aspect")) {
            if (player.hasPermission("*")) return;
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

//        if(e.getBlock().getType() == Material.SKULL){
//            ActionBarAPI.sendActionBar(p, "§cVocê não pode fazer isso.");
//            e.setCancelled(true);
//
//            return;
//        }

        if (p.getWorld().getName().equals("Aspect")) {
            if (p.hasPermission("*")) return;
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (p.getWorld().getName().equals("Aspect")) {
                e.setCancelled(true);
            }
        }
    }
}
