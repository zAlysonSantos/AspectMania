package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import acore.util.specificutils.PlayerUtil;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends AbstractCommand {

    public FlyCommand() {
        super("voar", "", "");

        this.setPermission(PermissionType.VIP.getPermisison());
        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {
        if (strings.length == 0) {
            Player player = (Player) commandSender;

            if (!player.getAllowFlight()) {

                player.setAllowFlight(true);
                player.setFlying(true);
                PlayerUtil.sendActionBar(player, "§aModo voar habilitado com sucesso!");
                return success();
            }

            player.setAllowFlight(false);
            player.setFlying(false);
            PlayerUtil.sendActionBar(player, "§eModo voar desabilitado.");

            return success();
        }
        if (strings.length == 1) {
            Player player = (Player) commandSender;
            Player target = Bukkit.getPlayer(strings[0]);

            if (target == null) {
                player.sendMessage("§cEste jogador está offline ou não existe.");
                return failure();
            }

            if (!player.getAllowFlight()) {

                target.setAllowFlight(true);
                target.setFlying(true);
                PlayerUtil.sendActionBar(target, "§aModo voar habilitado com sucesso!");
                PlayerUtil.sendActionBar(player, "§aModo voar habilitado para o jogador §7" + player.getName() + "§a!");
                return success();
            }

            target.setAllowFlight(false);
            target.setFlying(false);
            PlayerUtil.sendActionBar(target, "§cModo voar desabilitado.");
            PlayerUtil.sendActionBar(player, "§cModo voar desabilitado para o jogador §7" + player.getName() + "§c!");
        }

        return success();
    }
}
