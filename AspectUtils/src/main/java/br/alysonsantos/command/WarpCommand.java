package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.system.warps.helper.WarpHelper;
import br.alysonsantos.system.warps.model.Warp;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class WarpCommand extends AbstractCommand {
    private static final WarpHelper warpHelper = new WarpHelper();

    public WarpCommand() {
        super("warp", "", "");
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (strings.length > 0 && strings[0].equalsIgnoreCase("listar")) {

            player.sendMessage(
                    "\n " +
                            " §a§lWARPS DISPONÍVEIS:" +
                            "\n "
            );

            warpHelper.cachedWarps.values().forEach(warp -> {
                player.sendMessage(" §7 - §f" + warp.getName());
            });

            player.sendMessage("");
        }

        if (strings.length == 0) {
            player.chat("/warp listar");
        }

        if (strings.length > 0 && strings.length > 0 && !commands.contains(strings[0])) {

            String warpName = strings[0];

            if (!warpHelper.containsWarp(warpName)) {
                return failure(
                        "\n " +
                                "§c Ops! Essa warp não existe." +
                                "\n "
                );
            }

            warpHelper.teleportToWarp(player, warpName);

            player.sendMessage(
                    "\n " +
                            "§a Teleportado para a warp §7'" + warpName + "'§a!" +
                            "\n"
            );
        }

        if (strings.length > 0 && player.hasPermission(PermissionType.GERENTE.getPermisison())) {

            if (strings[0].equalsIgnoreCase("deletar")) {

                if (strings.length == 1) {
                    return failure(
                            "\n " +
                                    "§c Ops! O comando está incorreto, digite os argumentos necessários." +
                                    "\n"
                    );
                }

                String warpName = strings[0];

                if (!warpHelper.containsWarp(warpName)) {
                    return failure(
                            "\n " +
                                    "§c Ops! Essa warp não existe." +
                                    "\n"
                    );
                }

                warpHelper.deleteWarp(warpName);
                return success(
                        "\n " +
                                "§a§l SUCESSO! §aWarp §7'" + warpName + "'§a foi deletada." +
                                "\n ");
            }

            if (strings[0].equalsIgnoreCase("criar")) {

                if (strings.length == 1) {
                    return failure(
                            "\n " +
                                    "§c Ops! O comando está incorreto, digite os argumentos necessários." +
                                    "\n"
                    );
                }

                String warpName = strings[1];

                if (commands.contains(warpName)) {
                    return failure(
                            "\n " +
                                    "§c Ops! Já existe uma warp com este nome." +
                                    "\n"
                    );
                }

                if (warpHelper.containsWarp(warpName)) {
                    return failure(
                            "\n " +
                                    "§c Ops! Já existe uma warp com este nome." +
                                    "\n"
                    );
                }

                Warp warp = new Warp(warpName, player.getLocation());
                warpHelper.saveWarp(warp);

                return success(
                        "",
                        "§a Warp §7" + warpName + "§a criada com sucesso!",
                        "");
            }
        }

        return success();
    }

    private List<String> commands = Arrays.asList("listar", "deletar", "criar");
}
