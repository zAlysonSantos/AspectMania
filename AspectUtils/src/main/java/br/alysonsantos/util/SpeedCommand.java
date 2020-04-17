package br.alysonsantos.util;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand extends AbstractCommand {

    public SpeedCommand() {
        super("speed", "", "");

        this.setPermission(PermissionType.ADMINISTRADOR.getPermisison());
        this.registerAliases("velocidade");

        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof Player)) {

            if (strings.length != 2) {
                return failure("§aComando correto: /velocidade <nível>.");
            }

            Player p = Bukkit.getPlayer(strings[1]);
            if (p == null) {
                commandSender.sendMessage("§cEste jogador não existe ou está offline.");
                return failure();
            }

            if (strings[0].equalsIgnoreCase("clear") || strings[0].equalsIgnoreCase("reset")) {
                p.setFlySpeed(0.1f);
                p.setWalkSpeed(0.2f);
                commandSender.sendMessage(new String[]{"", "§a§l VELOCIDADE! Nível alterado para §npadrão§a.", ""});
                return success();
            }

            float speed;
            try {
                speed = Float.parseFloat(strings[0]);
            } catch (NumberFormatException e) {
                commandSender.sendMessage("§cDigite apenas números. Exemplo '0.5'!");
                return failure();
            }

            if (speed > 1.0f || speed < -1.0f) {
                commandSender.sendMessage("§cO valor é inválido.");
                return failure();
            }

            p.setFlySpeed(speed);
            p.setWalkSpeed(speed);
            commandSender.sendMessage(new String[]{"", "§a§l VELOCIDADE! Nível de velocidade do jogador " + p.getName() + " §aalterado para §n" + speed + "§a.", ""});
            return success();
        }

        if (strings.length < 1 || strings.length > 2) {
            return failure("§aComando correto: /velocidade <nível>.");
        }

        if (strings.length == 1) {

            Player p = (Player) commandSender;
            if (strings[0].equalsIgnoreCase("clear") || strings[0].equalsIgnoreCase("reset")) {
                p.setFlySpeed(0.1f);
                p.setWalkSpeed(0.2f);
                commandSender.sendMessage(new String[]{"", "§a§l VELOCIDADE! Nível alterado para §npadrão§a.", ""});
                return success();
            }

            float speed;
            try {
                speed = Float.parseFloat(strings[0]);
            } catch (NumberFormatException e) {
                commandSender.sendMessage("§cDigite apenas números. Exemplo '0.5'!");
                return failure();
            }

            if (speed > 1.0f || speed < -1.0f) {
                commandSender.sendMessage("§cO valor é inválido.");
                return failure();
            }

            p.setFlySpeed(speed);
            p.setWalkSpeed(speed);
            return success(
                    "",
                    "§e§l Yep! §eVelocidade alterada o nível §n" + speed + "§e.",
                    ""
            );
        }

        if (strings.length == 2) {

            Player p = Bukkit.getPlayer(strings[1]);
            if (p == null) {
                commandSender.sendMessage("§cO jogador está offline ou não existe.");
                return failure();
            }

            if (strings[0].equalsIgnoreCase("clear") || strings[0].equalsIgnoreCase("reset")) {
                p.setFlySpeed(0.1f);
                p.setWalkSpeed(0.2f);
                commandSender.sendMessage(new String[]{"", "§a§l VELOCIDADE! Nível alterado para §npadrão§a.", ""});
                return success();
            }

            float speed;
            try {
                speed = Float.parseFloat(strings[0]);
            } catch (NumberFormatException e) {
                commandSender.sendMessage("§cNúmero inválido.");
                return failure();
            }

            if (speed > 1.0f || speed < -1.0f) {
                commandSender.sendMessage("§cNível inválido.");
                return failure();
            }

            p.setFlySpeed(speed);
            p.setWalkSpeed(speed);
            return success(
                    "",
                    "§e Yah! Nível de velocidade do jogador " + p.getName() + " §ealterado para §n" + speed + "§a.",
                    ""
            );
        }
        return success();
    }
}