package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import acore.util.specificutils.PlayerUtil;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand extends AbstractCommand {


    public InvseeCommand() {
        super("invsee", "", "");

        this.setPlayersOnly(true);
        this.setPermission(PermissionType.GERENTE.getPermisison());
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            return failure(
                    "\n " +
                            "§c Ops! O comando está incorreto, digite os argumentos necessários." +
                            "\n"
            );
        }

            Player playerTarget = Bukkit.getPlayer(strings[0]);

            if (playerTarget == null) {
                return failure(
                        "\n " +
                                " §c Ops! Este jogador está offline ou não existe." +
                                "\n "
                );
            }

            player.openInventory(playerTarget.getInventory());
            PlayerUtil.sendActionBar(player, "§aInventário aberto com sucesso!");

            return success();
        }
}
