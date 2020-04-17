package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.AspectUtil;
import br.alysonsantos.system.warps.helper.SpawnHelper;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends AbstractCommand {
    private SpawnHelper spawnHelper = AspectUtil.getInstance().getSpawnHelper();

    public SpawnCommand() {
        super("spawn", "", "");

        this.setPlayersOnly(true);
    }


    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (strings.length > 0 && strings[0].equalsIgnoreCase("setarspawn")) {
            spawnHelper.saveLocation(player.getLocation());

            return success("§a Spawn §7#" + spawnHelper.getLocationList().size() + "§a setado com sucesso!");
        }

        if (strings.length > 0 && strings[0].equalsIgnoreCase("deletarspawn")) {
            if (!player.hasPermission("rankup.admin")) {
                return failure(
                        "\n Você não possui permissão. \n "
                );
            }

            spawnHelper.getLocationList().clear();
            spawnHelper.getDocument().set("locs", null);
            spawnHelper.getDocument().save();

            return success("Localização do spawn deletada com sucesso!");
        }

        spawnHelper.teleportToSpawn(player);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 60, 60);

        return success(
                "",
                "§a Teleportado para o spawn!",
                ""
        );
    }
}
