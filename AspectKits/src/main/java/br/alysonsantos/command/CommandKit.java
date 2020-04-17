package br.alysonsantos.command;

/*
 Author: Alyson Santos
*/

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import acore.util.specificutils.PlayerUtil;
import br.alysonsantos.AspectKits;
import br.alysonsantos.kit.Kit;
import br.alysonsantos.kit.KitHelper;
import br.alysonsantos.user.KitUser;
import br.alysonsantos.user.UserHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandKit extends AbstractCommand {
    private KitHelper kitHelper = AspectKits.getInstance().getKitHelper();
    private UserHelper userHelper = AspectKits.getInstance().getUserHelper();

    public CommandKit() {
        super("kit", "", "");

        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {
        if (strings.length > 0 && strings[0].equalsIgnoreCase("listar")) {

            commandSender.sendMessage(new String[]{"", "§e§l LISTA DE KITS DISPONÍVEIS:", ""});

            kitHelper.getCachedKits().keySet().forEach(kit -> {
                commandSender.sendMessage("§f - " + kit);
            });

            commandSender.sendMessage("");
            return null;
        }

        if (strings.length == 1) {
            String kitName = strings[0];

            if (!kitHelper.containsKit(kitName)) {
                return failure(
                        "\n " +
                                "§c§l Ops!§c Não existe um kit com este nome." +
                                "\n"
                );

            }

            Kit kit = kitHelper.getCachedKits().get(kitName);
            KitUser user = userHelper.getCachedUsers().get(commandSender.getName());

            if (user == null) {
                return failure(
                        "\n " +
                                "§c§l OPS!§c Ocorreu um erro ao pegar suas informações. Por favor, relogue." +
                                "\n "
                );
            }

            if (userHelper.inCooldown(user, kit)) {
                return failure(
                        "\n "
                                + "§e§l OPS!§e Você não pode fazer isso agora." +
                                "\n "
                                + " §7Aguarde§f " + formatTime(userHelper.getCooldown(user, kit) - System.currentTimeMillis()) + "§7 para recolher esse kit novamente." +
                                "\n ");
            }

            kit.getItemStackList().forEach(item -> {
                Player player = (Player) commandSender;
                player.getInventory().addItem(item);
            });

            user.getKitsInCooldown().put(kitName, System.currentTimeMillis() + kit.getCooldown());
            userHelper.saveDataUser(commandSender.getName());
            return success("", "§a§l KITS!§a O kit foi entregue em seu inventário.", "  " + kit.getName() + ".", "");
        }

        if (strings.length > 0 && strings[0].equalsIgnoreCase("dar")) {
            if (commandSender.hasPermission("rankup.gerente")) {
                String kitName = strings[1];

                if (!kitHelper.containsKit(kitName)) {
                    failure("\n " + "§c§l OPS!§c Não existe um kit com este nome." + "\n");
                }

                Kit kit = kitHelper.getCachedKits().get(kitName);

                Player target = Bukkit.getPlayer(strings[2]);

                if (target == null) {
                    return failure("§cEste jogador está offline ou não existe.");
                }

                kit.getItemStackList().forEach(item -> {
                    target.getInventory().addItem(item);
                });

                return success("", "§e§l KITS!§e O kit foi entregue no inventário do jogador " + target.getName() + "§e.", "");
            }
        }

        return null;
    }

    /*
     OTHERS ->>
     */

    private String formatTime(long time) {
        if (time == 0L) {
            return "never";
        }
        long day = TimeUnit.MILLISECONDS.toDays(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time) - day * 24L;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.MILLISECONDS.toHours(time) * 60L;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MILLISECONDS.toMinutes(time) * 60L;
        StringBuilder sb = new StringBuilder();
        if (day > 0L) {
            sb.append(day).append(" ").append(day == 1L ? "dia" : "dias,").append(" ");
        }
        if (hours > 0L) {
            sb.append(hours).append(" ").append(hours == 1L ? "hora" : "horas,").append(" ");
        }
        if (minutes > 0L) {
            sb.append(minutes).append(" ").append(minutes == 1L ? "minuto" : "minutos e").append(" ");
        }
        if (seconds > 0L) {
            sb.append(seconds).append(" ").append(seconds == 1L ? "segundo" : "segundos");
        }
        String diff = sb.toString();
        return diff.isEmpty() ? "agora" : diff;
    }

    private int getFreeSpaceInInventory(Player p) {
        int free = 0;
        ItemStack[] itens = p.getInventory().getContents();
        ItemStack[] arrayOfItemStack1;
        int j = (arrayOfItemStack1 = itens).length;
        for (int i = 0; i < j; i++) {
            ItemStack item = arrayOfItemStack1[i];
            if ((item == null) || (item.getType() == Material.AIR)) {
                free++;
            }
        }
        return free;
    }

    public boolean hasSpaceInInventory(Player player, Kit kit) {
        return getFreeSpaceInInventory(player) > getItemsAmountAmount(kit);
    }

    private int getItemsAmountAmount(Kit kit) {
        int i = 0;
        for (ItemStack itemStack : kit.getItemStackList()) {
            if (itemStack != null) {
                i++;
                i = i + itemStack.getAmount();
            }
        }
        return i;
    }

}