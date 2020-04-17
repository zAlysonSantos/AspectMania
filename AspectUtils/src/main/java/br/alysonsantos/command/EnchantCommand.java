package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantCommand extends AbstractCommand {
    public EnchantCommand() {
        super("enchantment", "", "");

        this.setPermission(PermissionType.GERENTE.getPermisison());
        this.setPlayersOnly(true);

        this.registerAliases("encantar");
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        if (strings.length != 2) {
            commandSender.sendMessage("\n §cComando incorreto, digite os argumentos necessários.\n ");
            return failure();
        }

        String en = strings[0].toUpperCase();
        Enchantment ench = getEnchantment(en);
        if (ench == null) {
            commandSender.sendMessage("\n §cEste encantamento não existe. Lista de encantamentos: \n" + getEnchants());
            return failure();
        }

        int level;
        try {
            level = Integer.parseInt(strings[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage("§cEste número não é válido.");
            return failure();
        }

        Player p = (Player) commandSender;
        ItemStack hand = p.getItemInHand();
        if (hand == null || hand.getType() == Material.AIR) {
            commandSender.sendMessage("\n §cEste item não é válido.\n ");
            return failure();
        }

        hand.addUnsafeEnchantment(ench, level);

        commandSender.sendMessage(
                "\n " +
                        "§a Item encantado com sucesso! " +
                        "§f  Encantamento: §e" + ench.getName() + "§f, nível:§e " + String.valueOf(level) + "\n "
        );

        return success();
    }

    private Enchantment getEnchantment(String enchant) {
        try {
            return Enchantment.getByName(enchant);
        } catch (Throwable e) {
            return null;
        }
    }

    private String getEnchants() {
        List<String> enchants = new ArrayList<>();
        for (Enchantment e : Enchantment.values()) {
            if (e != null) enchants.add(e.getName());
        }
        return enchants.toString().replace(",", "§7");
    }
}
