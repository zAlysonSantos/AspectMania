package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LixeiraCommand extends AbstractCommand {

    public LixeiraCommand() {
        super("lixeira", "Abre uma lixeira", "/lixeira");

        this.setPermission(PermissionType.VIP.getPermisison());
        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "§8Lixeira:");
        Player player = (Player) commandSender;

        player.openInventory(inventory);

        return success(
                "\n " +
                        "§a Lixeira aberta com sucesso!" +
                        "\n "
        );
    }
}
