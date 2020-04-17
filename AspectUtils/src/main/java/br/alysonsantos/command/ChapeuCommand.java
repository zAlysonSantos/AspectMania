package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChapeuCommand extends AbstractCommand {

    public ChapeuCommand() {
        super("chapeu", "Use um chapéu", "/chapeu");

        this.setPermission(PermissionType.VIP.getPermisison());
        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        ItemStack itemStack = player.getItemInHand();

        if (itemStack.getType() == Material.AIR) {
            return failure(
                    "\n " +
                            " §c Você não possui um item válido para usar como chapéu. " +
                            "\n"
            );
        }

        player.getInventory().setHelmet(itemStack);
        player.setItemInHand(new ItemStack(Material.AIR));

        return success(
                "\n " +
                        "§a Yay! Agora você possui um chapéu. " +
                        "\n "
        );
    }
}
