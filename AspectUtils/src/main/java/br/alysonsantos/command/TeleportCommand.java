package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import acore.util.specificutils.PlayerUtil;
import br.alysonsantos.system.teleport.helper.TeleportHelper;
import br.alysonsantos.util.VaultAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends AbstractCommand {
    private static TeleportHelper teleportHelper = new TeleportHelper();

    public TeleportCommand() {
        super("tpa", "", "");

        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {
        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage(new String[]{"", "§e Modo correto: §7 /tpa <jogador>.", ""});
            return null;
        }

        if (strings[0].equalsIgnoreCase("info")) {

            player.sendMessage(new String[]{"", "§a Lista de teleportes ocorrendo no momento:", ""});

            TeleportHelper.cachedTPA.forEach((a, b) -> {
                player.sendMessage(" §7De " + b.getSender() + "§f para §7" + b.getTarget());
            });

            player.sendMessage("");

            return null;

        }

        if (strings.length > 0) {

            if (strings[0].equalsIgnoreCase("aceitar")) {

                if (!teleportHelper.containsTpaRequest(player.getName())) {
                    player.sendMessage("§eVocê não possui uma solicitação de teleporte.");
                    return null;
                }

                if (strings.length == 1) {
                    player.sendMessage(new String[]{"", "§eComando correto: /tpa aceitar <jogador>.", ""});
                    return null;
                }
                if (strings.length == 2) {
                    Player target = Bukkit.getPlayer(strings[1]);
                    teleportHelper.acceptTpa(player, target);
                }
                return null;
            }

            if (strings[0].equalsIgnoreCase("cancelar")) {

                if (!teleportHelper.containsTpaByTarget(player.getName(), strings[1])) {
                    player.sendMessage("§eVocê não possui um pedido de teleporte para esse jogador.");
                    return null;
                }

                if (strings.length == 1) {
                    player.sendMessage(new String[]{"", "§eComando correto: /tpa cancelar <jogador>.", ""});
                    return null;
                }
                if (strings.length == 2) {
                    Player target = Bukkit.getPlayer(strings[1]);
                    teleportHelper.removeTpa(player.getName());

                    player.sendMessage(new String[]{"", "§c Você cancelou o pedido de teleporte para o jogador " + VaultAPI.getPlayerGroupPrefix(strings[1]) + strings[1] + "§c.", ""});
                    PlayerUtil.sendActionBar(target, "§cO jogador " + VaultAPI.getPlayerGroupPrefix(player.getName()) + player.getName() + "§c cancelou o pedido de teleporte.");
                }
                return null;
            }

            if (strings[0].equalsIgnoreCase("negar")) {

                if (!teleportHelper.containsTpaRequest(player.getName())) {
                    player.sendMessage("§eVocê não possui uma solicitação de teleporte.");
                    return null;
                }

                if (strings.length == 1) {
                    player.sendMessage(new String[]{"", "§eComando correto: /tpa negar <jogador>.", ""});
                    return null;
                }
                if (strings.length == 2) {
                    Player target = Bukkit.getPlayer(strings[1]);
                    teleportHelper.removeTpa(target.getName());

                    PlayerUtil.sendActionBar(target, "§c O jogador " + VaultAPI.getPlayerGroupPrefix(target.getName()) + target.getName() + "§c não aceitou o seu pedido de teleporte.");
                    player.sendMessage(new String[]{"", "§c Você negou o pedido de teleporte do jogador " + VaultAPI.getPlayerGroupPrefix(player.getName()) + player.getName() + "§c.", ""});
                }
                return null;
            }

            Player target = Bukkit.getPlayer(strings[0]);

            if (target == null) {
                player.sendMessage("§cEste jogador está offline ou não existe.");
                return null;
            }

            if (teleportHelper.containsTpa(player.getName())) {
                player.sendMessage(new String[]{"", "§c Você já mandou uma solicitação de teleporte para alguém.", "§7 Aguarde o jogador aceitar ou clique §c§lAQUI§7 para cancelar.", ""});
                return null;
            }

            if (strings[0].equals(player.getName())) {
                player.sendMessage(new String[]{"§cAlgo de errado não está certo..."});
                return null;
            }

            teleportHelper.sendTpa(player, target);
        }

        return success();
    }
}