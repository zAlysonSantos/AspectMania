package br.alysonsantos.listener;

import br.alysonsantos.AspectUtil;
import br.alysonsantos.system.warps.helper.SpawnHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class OthersListeners implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(null);

        if (!player.hasPermission("rankup.admin")) return;
        player.setAllowFlight(true);
        player.setFlying(true);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onSignChang(SignChangeEvent e) {
        for (int i = 0; i < e.getLines().length; i++) {
            e.setLine(i, e.getLine(i).replace("&", "§"));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onChangeBlockJump(EntityChangeBlockEvent e) {
        if (e.getBlock().getType() == Material.SOIL) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void wheaterChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) e.setCancelled(true);
    }

    @EventHandler
    public void foodChange(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
        e.setCancelled(true);
    }

    @EventHandler
    public void onSpawnMob(CreatureSpawnEvent e) {
        CreatureSpawnEvent.SpawnReason reason = e.getSpawnReason();
        if (reason == CreatureSpawnEvent.SpawnReason.SPAWNER) return;

        if (
                reason == CreatureSpawnEvent.SpawnReason.NATURAL
                        || reason == CreatureSpawnEvent.SpawnReason.CHUNK_GEN
                        || reason == CreatureSpawnEvent.SpawnReason.JOCKEY
        )

            e.setCancelled(true);
    }

    private SpawnHelper spawnHelper = AspectUtil.getInstance().getSpawnHelper();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        if ((e.getCause() == EntityDamageEvent.DamageCause.VOID) && ((e.getEntity() instanceof Player))) {
            Player p = (Player) e.getEntity();
            if (p.getLocation().getBlockY() < 0) {
                e.setCancelled(true);
                p.setFallDistance(1.0F);
                spawnHelper.teleportToSpawn(p);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void preLogin(AsyncPlayerPreLoginEvent e) {
        Player p = Bukkit.getPlayerExact(e.getName());
        if (p != null) {
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void preLogin(PlayerLoginEvent e) {
        Player p = Bukkit.getPlayerExact(e.getPlayer().getName());
        if (p != null) {
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKick(PlayerKickEvent e) {
        if (e.getReason().contains("You logged in from another location")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void entityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            if (player.getWorld().getName().equals("Aspect")) {
                e.setCancelled(true);
            }

            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                if (player.getLocation().getBlockY() < 0) {
                    e.setCancelled(true);
                    player.setFallDistance(1);
                    spawnHelper.teleportToSpawn(player);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onChangeBlock(EntityChangeBlockEvent e) {
        if (e.getBlock().getType() == Material.SOIL) {
            e.setCancelled(true);
        }
    }

//    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
//    public void onSendMessage(ChatMessageEvent e) {
//        Player p = e.getSender();
//
//        if (p.getName().equals(Magnata.get().magnataAtual) && e.getTags().contains("magnata")) {
//            e.setTagValue("magnata", "�a[$] ");
//        }
//
//        if (p.hasPermission("store.chat")) {
//            e.setFormat(" \n" + e.getFormat() + "\n ");
//        }
//
//        e.setTagValue("position", "�f[" + Magnata.get().positon.get(p.getName()) + "�] ");
//        e.setMessage("�f" + e.getMessage());
//    }
}
