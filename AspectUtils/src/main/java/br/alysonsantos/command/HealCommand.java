package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand extends AbstractCommand {

    public HealCommand() {
        super("heal", "", "");

        this.setPermission(PermissionType.ADMIN.getPermisison());
        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            if (player.getHealth() == 20) {
                return failure(
                        "\n " +
                                "§c Sua vida já está no máximo." +
                                "\n "
                );
            }

            player.setHealth(20);
            return success(
                    "\n " +
                            "§a Sua vida foi restaurada!" +
                            "\n "
            );
        }

        if (strings.length == 1) {
            Player playerTarget = Bukkit.getPlayer(strings[0]);

            if (playerTarget == null) {
                return failure(
                        "\n " +
                                " §c Ops! Este jogador está offline ou não existe." +
                                "\n "
                );
            }

            if (player.getHealth() == 20) {
                return failure(
                        "\n " +
                                "§c A vida deste jogador já está no máximo." +
                                "\n "
                );
            }

            playerTarget.setHealth(20);
            return success(
                    "\n " +
                            "§a A vida do jogador foi restaurada!" +
                            "\n "
            );
        }

        return null;
    }
}
