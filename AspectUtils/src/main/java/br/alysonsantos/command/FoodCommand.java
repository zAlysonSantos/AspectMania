package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FoodCommand extends AbstractCommand {

    public FoodCommand() {
        super("food", "", "");

        this.setPermission(PermissionType.VIP.getPermisison());
        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (player.getFoodLevel() == 20) {
            failure(
                    "\n " +
                            "§c Você não está com fome..." +
                            "\n "
            );
        }

        player.setFoodLevel(20);

        return success(
                "\n " +
                        "§æ Sua fome foi saciada!" +
                        "\n "
        );
    }
}
