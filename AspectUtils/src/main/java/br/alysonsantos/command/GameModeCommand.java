package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import acore.util.specificutils.PlayerUtil;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand extends AbstractCommand {

    public GameModeCommand() {
        super("gamemode", "Altera o modo de jogo", "/gamemode <modo de jogo>");

        this.setPermission(PermissionType.ADMINISTRADOR.getPermisison());
        this.registerAliases("gm");
        this.setPlayersOnly(true);
    }


    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            return failure(
                    "\n " +
                            "§c Ops! O comando está incorreto, digite os argumentos necessários." +
                            "\n"
            );
        }

        if (strings[0].equalsIgnoreCase("1")) {
            if (strings.length == 1) {

                if (player.getGameMode() == GameMode.CREATIVE) {
                    return failure(
                            "\n " +
                                    "§c Você já está com o modo de jogo no criativo!" +
                                    "\n "
                    );
                }

                player.setGameMode(GameMode.CREATIVE);
                PlayerUtil.sendActionBar(player, "§aAgora você está no modo criativo.");

                return success();
            }

            if (strings.length == 2) {
                Player playerTarget = Bukkit.getPlayer(strings[1]);

                if (playerTarget == null) {
                    return failure(
                            "\n " +
                                    " §c Ops! Este jogador está offline ou não existe." +
                                    "\n "
                    );
                }

                if (playerTarget.getGameMode() == GameMode.CREATIVE) {
                    return failure(
                            "\n " +
                                    "§c Este jogador já está com o modo de jogo no criativo!" +
                                    "\n "
                    );
                }

                playerTarget.setGameMode(GameMode.CREATIVE);
                PlayerUtil.sendActionBar(playerTarget, "§aModo de jogo alterado para criativo!");
                PlayerUtil.sendActionBar(player, "§cModo de jogo do jogador �7" + player.getName() + "§a alterado para criativo!");

                return success();
            }
        }
        if (strings[0].equalsIgnoreCase("0")) {
            if (strings.length == 1) {
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    return failure(
                            "\n " +
                                    "§c Você já está no modo de jogo sobrevicência!" +
                                    "\n "
                    );
                }

                player.setGameMode(GameMode.SURVIVAL);
                PlayerUtil.sendActionBar(player, "§aAgora você está no sobrevivência!");

                return success();
            }

            if (strings.length == 2) {
                Player playerTarget = Bukkit.getPlayer(strings[1]);

                if (playerTarget == null) {
                    return failure(
                            "\n " +
                                    " §c Ops! Este jogador está offline ou não existe." +
                                    "\n "
                    );
                }

                if (playerTarget.getGameMode() == GameMode.ADVENTURE) {
                    return failure(
                            "\n " +
                                    "§cEste jogador já está com o modo de jogo em sobrevivência!" +
                                    "\n "
                    );
                }

                playerTarget.setGameMode(GameMode.ADVENTURE);
                PlayerUtil.sendActionBar(playerTarget, "§aModo de jogo alterado para sobrevivência!");
                PlayerUtil.sendActionBar(player, "§aModo de jogo do jogador §7" + player.getName() + "§a alterado para sobrevivência!");
                return success();
            }
        }

        return success();
    }
}
