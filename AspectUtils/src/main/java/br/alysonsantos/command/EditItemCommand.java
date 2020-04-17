package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EditItemCommand extends AbstractCommand {
    public EditItemCommand() {
        super("editaritem", "", "");

        this.setPermission(PermissionType.GERENTE.getPermisison());
        this.registerAliases("edititem");

        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {
        if (strings.length < 1) {
            commandSender.sendMessage("\n §cComando incorreto, digite os argumentos necessários.\n §7 - addlore, removelore, glow, renomear, ");
            return success();
        }

        Player player = (Player)commandSender;
        ItemStack item = player.getItemInHand();

        if (item == null || item.getType() == Material.AIR) {
            commandSender.sendMessage("\n §cEste item não é válido! \n ");
            return success();
        }

        ItemMeta meta = item.getItemMeta();

        if (strings[0].equalsIgnoreCase("renomear")) {
            String nome = "";
            for (int i = 1; i < strings.length; i++) {nome += strings[i] + " ";}
            meta.setDisplayName(nome.replace('&', '§').trim());
            item.setItemMeta(meta);
            commandSender.sendMessage("\n Item editado com sucesso!\n ");
            return success();
        }

        // Verificando se o player quer adiconar glow no item
        if (strings[0].equalsIgnoreCase("glow")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            commandSender.sendMessage("\n Item editado com sucesso!\n ");
            return success();
        }

        if (strings[0].equalsIgnoreCase("removelore")) {
            meta.setLore(null);
            item.setItemMeta(meta);
            commandSender.sendMessage("\n Item editado com sucesso!\n ");
            return success();
        }

        if (strings[0].equalsIgnoreCase("addlore")) {
            List<String> lore = new ArrayList<>();
            if (meta.hasLore()) lore.addAll(meta.getLore());
            String novaLinha = "";
            for (int i = 1; i < strings.length; i++) {novaLinha  += strings[i] + " ";}
            lore.add(novaLinha.replace('&', '§'));
            meta.setLore(lore);
            item.setItemMeta(meta);
            commandSender.sendMessage("\n Item editado com sucesso!\n ");
            return success();
        }

        commandSender.sendMessage("\n §aItem alterado com sucesso! \n ");
        return success();
    }
}
