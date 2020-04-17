package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.AspectKits;
import br.alysonsantos.kit.Kit;
import br.alysonsantos.kit.KitHelper;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandKitCriar extends AbstractCommand {

    private final KitHelper kitHelper = AspectKits.getInstance().getKitHelper();

    public CommandKitCriar() {
        super("kitcriar", "", "");

        this.isPlayersOnly();
        this.setPermission("rankup.gerente");
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("\n " + "§a O comando correto é /criarkit <nome> <cooldown>." + "\n");
            return null;
        }

        if (strings.length == 1) {
            commandSender.sendMessage("\n" + "§a O comando correto é /criarkit <nome> <cooldown>." + "\n");
            return null;
        }

        if (kitHelper.containsKit(strings[0])) {
            commandSender.sendMessage("\n" + "§c§l OPS!§c Já existe um kit com este nome." + "\n");
            return null;
        }

        Player player = (Player) commandSender;

        try {

            long time = Long.parseLong(strings[1]);

            List<ItemStack> itemStacks = new ArrayList<>();

            for (ItemStack itemStack : player.getInventory().getContents()) {

                if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                    itemStacks.add(itemStack);
                }

            }

            Kit kit = new Kit(strings[0], time, itemStacks);
            kitHelper.createKit(kit);

            player.sendMessage(new String[]{"", "§a Kit criado com sucesso!", ""});
        } catch (NumberFormatException e) {
            player.sendMessage(new String[]{
                    "",
                    "§c§l Ops!§c Este número não é válido.",
                    ""
            });
        }
        return null;
    }

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
            sb.append(day).append(" ").append(day == 1L ? "dia" : "dias").append(" ");
        }
        if (hours > 0L) {
            sb.append(hours).append(" ").append(hours == 1L ? "hora" : "horas").append(" ");
        }
        if (minutes > 0L) {
            sb.append(minutes).append(" ").append(minutes == 1L ? "minuto" : "minutos").append(" ");
        }
        if (seconds > 0L) {
            sb.append(seconds).append(" ").append(seconds == 1L ? "segundo" : "segundos");
        }
        String diff = sb.toString();
        return diff.isEmpty() ? "agora" : diff;
    }
}
