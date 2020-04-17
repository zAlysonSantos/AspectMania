package br.alysonsantos.command;

import br.alysonsantos.util.VaultAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class CommandVanish implements CommandExecutor {
    @Getter
    private static List<Player> vanishList = new ArrayList<>();

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {

        if (!s.hasPermission("group.moderador")
                || !s.hasPermission("group.admin")
                || !s.hasPermission("group.gerente")
                || !s.hasPermission("group.coordenador")) {
            s.sendMessage(new String[]{"", "§c Você não possui permissão.", ""});
            return true;
        }

        if (args.length == 0) {
            Player player = (Player) s;

            if (inVanish(player)) {
                player.sendMessage(new String[]{"", " §c§lVANISH! §cModo vanish desabilitado com sucesso!", ""});
                setVanish(player, false);
                return true;
            }

            setVanish(player, true);
            player.sendMessage(new String[]{"", " §a§lVANISH! §aModo vanish §nhabilitado§a com sucesso!", ""});
            return true;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("listar")) {

                s.sendMessage(new String[]{"", "§a Jogadores no modo vanish: ", ""});

                getVanishList().forEach(a -> {
                    s.sendMessage("  §f- " + VaultAPI.getPlayerGroupPrefix(a.getName()) + a.getName() + "§7.");
                });

                s.sendMessage(" ");

                return true;
            }

            Player player = (Player) s;
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                s.sendMessage("§cEste jogador está offline ou não existe.");
                return true;
            }

            if (inVanish(target)) {
                player.sendMessage(new String[]{"", " §c§lVANISH! §cModo vanish desabilitado para o jogador §n" + target.getName() + "§c com sucesso!", ""});
                setVanish(target, false);
                return true;
            }

            setVanish(target, true);
            player.sendMessage(new String[]{"", " §a§lVANISH! §aModo vanish §nhabilitado§a para o jogador " + target.getName() + "§a com sucesso!", ""});
            return true;
        }

        return false;
    }

    private boolean inVanish(Player name) {
        return vanishList.contains(name);
    }

    private void setVanish(Player player, boolean ativar) {
        if (ativar) {
            vanishList.add(player);

            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 50), true);
            Bukkit.getOnlinePlayers().forEach(a -> {
                        if (!a.hasPermission("store.vanish.bypass")) {
                            a.hidePlayer(player);
                        }
                    }
            );
        } else {
            vanishList.remove(player);

            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            Bukkit.getOnlinePlayers().forEach(a -> a.showPlayer(player));
        }
    }
}
