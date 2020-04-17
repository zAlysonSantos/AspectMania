package br.alysonsantos.system.teleport.helper;

import acore.util.json.JAction;
import acore.util.specificutils.PlayerUtil;
import acore.util.specificutils.TextUtil;
import br.alysonsantos.system.teleport.models.Teleport;
import br.alysonsantos.util.VaultAPI;
import com.google.common.collect.Maps;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TeleportHelper {
    public static Map<String, Teleport> cachedTPA = Maps.newHashMap();

    public boolean containsTpa(String name) {
        return cachedTPA.containsKey(name);
    }

    public boolean containsTpaRequest(String name) {
        for (Map.Entry<String, Teleport> r : cachedTPA.entrySet()) {
            if (r.getValue().getTarget().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTpaByTarget(String player, String target) {
        for (Map.Entry<String, Teleport> r : cachedTPA.entrySet()) {
            if (r.getKey().equals(player) && r.getValue().getTarget().equals(target)) return true;
        }
        return false;
    }

    public void sendTpa(Player sender, Player target) {
        cachedTPA.put(sender.getName(), new Teleport(sender.getName(), target.getName(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60)));

        String sendMess = "\n " + "§a§l TELEPORTE: " + "\n " + " §aPedido de teleporte enviado para " + VaultAPI.getPlayerGroupPrefix(target.getName()) + target.getName() + "§a." + "\n " + "§a O jogador possui 1 minuto para aceitar. \n \n " + "§7 Clique §C§L" + "%info%" + "§7 para cancelar o pedido." + "\n ";
        String[] split = sendMess.split("%info%");

        JAction jActionOne = new JAction()
                .text(TextUtil.parse(split[0])).end()
                .text("§c§lAQUI")
                .setEvent("§cClique para cancelar a solicitação de teleporte.", HoverEvent.Action.SHOW_TEXT)
                .setParseEvent("/tpa cancelar " + target.getName(), ClickEvent.Action.RUN_COMMAND).end()
                .text(TextUtil.parse(split[1]), true).endJson();

        jActionOne.send(sender);

        String sendMessTwo = "\n " + "§a§l TELEPORTE: " + "\n " + " §aPedido de teleporte recebido de " + VaultAPI.getPlayerGroupPrefix(sender.getName()) + sender.getName() + "§a." + "\n " + "§a Você possui 1 minuto para aceitar. \n \n " + "§7 Clique para §a§l" + "%aceitar%" + "§7 ou §c§l" + "%aceitar%" + "§7 o pedido." + "\n ";
        String[] splitTWO = sendMessTwo.split("%aceitar%");

        JAction jActionTwo = new JAction()
                .text(TextUtil.parse(splitTWO[0])).end()
                .text("§a§lACEITAR")
                .setEvent("§eClique para aceitar o pedido de teleporte.", HoverEvent.Action.SHOW_TEXT)
                .setParseEvent("/tpa aceitar " + sender.getName(), ClickEvent.Action.RUN_COMMAND).end()

                .text(TextUtil.parse(splitTWO[1])).end()
                .text("§c§lNEGAR")
                .setEvent("§eClique para negar o pedido de teleporte.", HoverEvent.Action.SHOW_TEXT)
                .setParseEvent("/tpa negar " + sender.getName(), ClickEvent.Action.RUN_COMMAND).end()

                .text(TextUtil.parse(splitTWO[2]), true).endJson();

        jActionTwo.send(target);

        //sender.sendMessage(new String[]{"", "§e Pedido de teleporte enviado para " + VaultAPI.getPlayerGroupPrefix(target.getName()) + target.getName() + "§e.", "§7 Clique §C§LAQUI§7 para cancelar o pedido.", "", "§aO jogador possui 1 minuto para aceitar."});
        //target.sendMessage(new String[]{"", "§e Pedido de teleporte recebido de " + VaultAPI.getPlayerGroupPrefix(sender.getName()) + sender.getName() + "§e.", "§7 Clique para §a§lACEITAR§7 ou §c§lNEGAR§7 o pedido.", "", "§aVocê possui 1 minuto para aceitar."});
    }

    public void acceptTpa(Player player, Player target) {
        if (!containsTpaByTarget(target.getName(), player.getName())) {
            player.sendMessage("§eVocê não possui uma solicitação de teleporte desse jogador.");
            return;
        }

        PlayerUtil.sendActionBar(target, "§aTeleportando para " + VaultAPI.getPlayerGroupPrefix(target.getName()) + target.getName() + "§e.");

        player.sendMessage("\n §aVocê aceitou a solicitação de teleporte do jogador " + VaultAPI.getPlayerGroupPrefix(target.getName()) + target.getName() + "§a.\n ");
        target.sendMessage(new String[]{"", "§a O jogador aceitou a sua solicitação de teleporte!", ""});
        target.teleport(player.getLocation());

        cachedTPA.remove(target.getName());
    }

    public void removeTpa(String player) {
        cachedTPA.remove(player);
    }
}