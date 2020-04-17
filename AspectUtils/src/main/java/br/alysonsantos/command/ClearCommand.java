package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ClearCommand extends AbstractCommand {

    public ClearCommand() {
        super("clear", "Limpa o inventário de um jogador", "/clear <player>");

        this.setPermission(PermissionType.GERENTE.getPermisison());
        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            if (inventoryIsEmpty(player)) {
                return failure(
                        "\n " +
                                "§c Seu inventário já está vazio." +
                                "\n "
                );
            }

            clearInventory(player);
            success(
                    "\n " +
                            " §aSeu inventário foi limpo." +
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

            if (inventoryIsEmpty(playerTarget)) {
                return failure(
                        "\n " +
                                "§c Seu inventário do jogador já está vazio." +
                                "\n "
                );
            }

            clearInventory(playerTarget);
            success(
                    "\n " +
                            " §aO inventário do jogador foi limpo." +
                            "\n "
            );
        }

        return null;
    }

    private boolean inventoryIsEmpty(Player p) {
        PlayerInventory inv = p.getInventory();
        ItemStack[] contents;
        ItemStack[] armorContents;

        for (int length = (contents = inv.getContents()).length, j = 0; j < length; ++j) {
            ItemStack i = contents[j];
            if (i != null && i.getType() != Material.AIR) {
                return false;
            }
        }

        for (int length2 = (armorContents = inv.getArmorContents()).length, k = 0; k < length2; ++k) {
            ItemStack i = armorContents[k];
            if (i != null && i.getType() != Material.AIR) {
                return false;
            }
        }
        return p.getItemOnCursor() == null || p.getItemOnCursor().getType() == Material.AIR;
    }

    private void clearInventory(Player p) {
        PlayerInventory inv = p.getInventory();
        p.setItemOnCursor(null);
        inv.clear();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
    }
}
