package br.alysonsantos.command;


import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class AlertaCommand extends AbstractCommand {

    public AlertaCommand() {
        super("alerta", "Envia um alerta para todo o servidor", "/alerta <mensagem>");

        this.createHelpCommand();
        this.setPermission(PermissionType.ADMIN.getPermisison());
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Bukkit.broadcastMessage("\n " +
                "§a§l RANKUP FARM:" +
                "\n " + getText(strings).replace("&", "§") +
                "\n ");

        return success("§aAlerta enviado com sucesso.");
    }

    private String getText(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg);
            sb.append(" ");
        }
        return sb.toString();
    }

}

