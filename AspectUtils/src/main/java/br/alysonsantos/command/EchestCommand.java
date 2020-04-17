package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import acore.util.specificutils.PlayerUtil;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EchestCommand extends AbstractCommand {


    public EchestCommand() {
        super("echest", "Abre um echest virtual", "/echest");

        this.setPermission(PermissionType.VIP.getPermisison());
        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.openInventory(player.getEnderChest());
            PlayerUtil.sendActionBar(player, "§eBaú do fim aberto com sucesso!");

            return success();
        }

        if (strings.length == 1) {
            if (!player.hasPermission("rankup.gerente")) {
                return failure(
                        "\n " +
                                "§c Vocẽ não possui permissão." +
                                "\n "
                );
            }

            Player target = Bukkit.getPlayer(strings[0]);

            if (target == null) {
                return failure(
                        "\n " +
                                " §c Ops! Este jogador está offline ou não existe." +
                                "\n "
                );
            }

            player.openInventory(target.getEnderChest());
            PlayerUtil.sendActionBar(player, "§eBaú do fim aberto com sucesso!");
        }

        return success();
    }
}
