package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCommand extends AbstractCommand {

    public CraftCommand() {
        super("craft", "Abre uma mesa de trabalho virtual", "/craft");

        this.setPermission(PermissionType.VIP.getPermisison());
        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;
        player.openWorkbench(player.getLocation(), true);

        return success("§aMesa de trabalho aberta.");
    }
}
