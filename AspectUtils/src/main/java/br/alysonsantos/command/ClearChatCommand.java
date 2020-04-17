package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import br.alysonsantos.permission.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ClearChatCommand extends AbstractCommand {

    public ClearChatCommand() {
        super("clearchat", "Limpar o chat do servidor", "/clearchat");

        this.setPermission(PermissionType.ADMIN.getPermisison());
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        for (int i = 0; i < 50; i++) {
            Bukkit.broadcastMessage(" ");
        }

        return success(
                "\n " +
                        " §aO chat foi limpado com sucesso!" +
                        "\n "
        );
    }
}
