package br.alysonsantos.util;

import acore.util.specificutils.PlayerUtil;
import br.alysonsantos.command.CommandVanish;
import br.alysonsantos.system.teleport.helper.TeleportHelper;
import br.alysonsantos.system.teleport.models.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class UtilTasker extends BukkitRunnable {

    @Override
    public void run() {
        CommandVanish.getVanishList().forEach(a -> PlayerUtil.sendActionBar(a, "§aVocê está no modo vanish. Há §7§l" + CommandVanish.getVanishList().size() + "§a jogador(es) neste modo."));

        for (Teleport teleport : TeleportHelper.cachedTPA.values()) {

            if (teleport.getCooldown() <= System.currentTimeMillis()) {

                Player player = Bukkit.getPlayer(teleport.getSender());
                Player target = Bukkit.getPlayer(teleport.getTarget());

                player.sendMessage(new String[]{"", "§c O jogador " + VaultAPI.getPlayerGroupPrefix(teleport.getTarget()) + teleport.getTarget() + "§c não aceitou o seu pedido de teleporte.", ""});
                target.sendMessage(new String[]{"", "§c Você não aceitou o pedido de teleporte do jogador " + VaultAPI.getPlayerGroupPrefix(teleport.getSender()) + teleport.getSender() + "§c.", ""});

                TeleportHelper.cachedTPA.remove(teleport.getSender());
            }
        }
    }

    private static String formatTime(long time) {
        if (time == 0L) {
            return "never";
        }
        long day = TimeUnit.MILLISECONDS.toDays(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time) - day * 24L;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.MILLISECONDS.toHours(time) * 60L;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MILLISECONDS.toMinutes(time) * 60L;
        StringBuilder sb = new StringBuilder();
        if (day > 0L) {
            sb.append(day).append(" ").append(day == 1L ? "dia" : "dias").append(" ");
        }
        if (hours > 0L) {
            sb.append(hours).append(" ").append(hours == 1L ? "hora" : "horas").append(" ");
        }
        if (minutes > 0L) {
            sb.append(minutes).append(" ").append(minutes == 1L ? "minuto" : "minutos").append(" ");
        }
        if (seconds > 0L) {
            sb.append(seconds).append(" ").append(seconds == 1L ? "segundo" : "segundos");
        }
        String diff = sb.toString();
        return diff.isEmpty() ? "agora" : diff;
    }
}